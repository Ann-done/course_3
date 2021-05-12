using Newtonsoft.Json;
using ServerET.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Data.Entity;
using System.Web;
using Microsoft.Ajax.Utilities;
using System.Data.Entity.Core.Metadata.Edm;
using System.Web.UI.WebControls;

namespace ServerET.Controllers
{
    public class ValuesController : ApiController
    {

        TestContext db = new TestContext();


        // GET api/values
        public IEnumerable<Student> GetStudents() // получить расписание
        {
            return db.Students;
        }


        [HttpPost]
        [Route("api/values/login")]
        public Dictionary<string, string> LogIn([FromBody]string json) // по номеру карты 
        {
           
            var myJsonObj = JsonConvert.DeserializeObject<Dictionary<string, dynamic>>(json);
            var id = myJsonObj["Id"];
            var lastname = myJsonObj["LastName"];
            var name = myJsonObj["FirstName"];

            Student student = db.Students.Find(id);
            if (student == null || (!student.LastName.Equals(lastname) && !student.LastName.Equals(name)))
            {
                return new Dictionary<string, string> { { "message", "no such student" } };
            }
            if (student.IsLogIn == 1)
            {
                return new Dictionary<string, string> { { "message", "you are already logged in" } };
            }
            student.IsLogIn = 1;
            db.SaveChanges();

            var result = new Dictionary<string, string> {
                {"Id", student.Id.ToString() },
                {"LastName", student.LastName },
                {"FirstName", student.FirstName },
                {"GroupId", student.GroupId.ToString() }
            };

            Subject subject = this.FindSubject(student.GroupId);
            if (subject == null)
            {
                result["SubjectId"] = null;
                return result;
            }

            result["SubjectId"] = subject.Id.ToString();
            result["SubjShortName"] = subject.ShortName;
            result["SubjFullName"] = subject.FullName;
            //var i = 1;
            //foreach (var item in this.GetTopics(subject.Id))
            //{
            //    result[$"Topic{i}"] = item.TopicName;
            //    i++;
            //}

            return result;
        }


        [HttpPost]
        [Route("api/values/getsubject")]
        public Dictionary<string, string> GetSubject([FromBody] string json) //через тело 
        {
            if (json == null)
            {
                return new Dictionary<string, string> { { "message", "null body" } };
            }
            var myJsonObj = JsonConvert.DeserializeObject<Dictionary<string, dynamic>>(json);
            var idGroup = myJsonObj["Id"];

            Subject subject = this.FindSubject((int)idGroup);
            Dictionary<string, string> result = new Dictionary<string, string>();

            if (subject == null)
            {
                result = null;
                return result;
            }
            else
            {
                result["Id"] = subject.Id.ToString();
                result["ShortName"] = subject.ShortName;
                result["FullName"] = subject.FullName;
                
                
                //var topics = this.GetTopics(subject.Id);
                //foreach (var topic in topics)
                //{
                //    result[$"Topic_{topic.Id}"] = topic.TopicName;
                //}
            }

            return result;
        }

        private Subject FindSubject(int idGroup)
        {
            Dictionary<string, int> day_lectNum = this.CheckTime();
            if ((day_lectNum == null) || (day_lectNum["LectureNum"] == 0))
            {
                return null;
            }
            var lectNum = day_lectNum["LectureNum"];
            var day = day_lectNum["DayOfWeek"];

            var table = from t in db.Timetables
                        where t.GroupId == idGroup && t.LectureNum == lectNum && t.DayOfWeek == day
                        select t.Subject;
            Subject subject = table.FirstOrDefault();
            return subject;
        }

       
        [Route("api/values/gettopics/{idSubj}")]
        public IEnumerable<Topic> GetTopics(int idSubj)
        {
            var table = from t in db.Topics
                        where t.SubjectId == idSubj
                        select t;
            return table;
        }

        [HttpPost]
        [Route("api/values/getQus")]
        public List<Task> GetQus([FromBody] string json)
        {
            var myJsonObj = JsonConvert.DeserializeObject<Dictionary<string, dynamic>>(json);
            int idTopic = (int)myJsonObj["Id"];
            var table = from t in db.Questions
                        where t.TopicId == idTopic
                        select t;

            List<Task> result = new List<Task>(); 
           
            foreach (var item in table)
            {

                var answers = this.GetAnswers(item.Id);
                var ids = new List<int>();
                var answer_list = new Dictionary<int, string>();
                foreach(var answer in answers)
                {
                    if (answer.IsRight == 1)
                    {
                        ids.Add(answer.Id);
                    }
                    answer_list[answer.Id] = answer.Text;
                }
                result.Add(new Task {question=item.QuName, right_ids=ids, answers=answer_list});

            }

            return result;
        }

        
        private  List<Answer> GetAnswers(int idQu)
        {
          
            var table = from t in db.Answers
                        where t.QuestionId == idQu
                        select t;
            var res =  table.ToList();
            return res;
        }

        //[HttpPost]
        //[Route("api/values/getAnswers")]
        //public IEnumerable<Answer> GetAnswers([FromBody]string json)
        //{
        //    var myJsonObj = JsonConvert.DeserializeObject<Dictionary<string, dynamic>>(json);
        //    int idQu = myJsonObj["id"];

        //    var table = from t in db.Answers
        //                where t.QuestionId == idQu
        //                select t;
        //    return table;
        //}

        [HttpPost] // запись результата 
        public void CreateResult([FromBody]Result result)
        {
            db.Results.Add(result);
            db.SaveChanges();
        }

        [HttpPost]
        [Route("api/values/getResults")]
        public IEnumerable<Result> GetResults([FromBody]string json)
        {
            var myJsonObj = JsonConvert.DeserializeObject<Dictionary<string, dynamic>>(json);
            string idSt = myJsonObj["idSt"];
            int idRes = myJsonObj["idRes"];  // проверять какие данные надо передавать 

            var table = from t in db.Results
                        where t.StudentId == idSt && t.Id > idRes
                        select t;

            return table;
        }

        [HttpPost]
        [Route("api/values/logout")]
        public Dictionary<string, string> LogOut([FromBody] string json)
        {
            var myJsonObj = JsonConvert.DeserializeObject<Dictionary<string, dynamic>>(json);
            string id = myJsonObj["id"];
            Student student = db.Students.Find(id);
            if (student == null)
            {
                return new Dictionary<string, string> { { "message", "Error" } };
            }
            student.IsLogIn = 0;
            db.SaveChanges();
            return new Dictionary<string, string> { { "message", "You logged out" } };
        }



        [HttpPut] //изменение в расписании
        public void EditTimetable(int id, [FromBody]Timetable timetable)
        {
            if (id == timetable.Id)
            {
                db.Entry(timetable).State = EntityState.Modified;

                db.SaveChanges();
            }
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        protected Dictionary<string, int> CheckTime()
        {
            // вернуть LectureNum & DayOfWeek
            Dictionary<string, int> res = new Dictionary<string, int>();

            DateTime date = DateTime.Now;
            var day = date.DayOfWeek;
            if ((int)day == 0)
            {
                return null; // пар нет - выходной день 
            }
            res["DayOfWeek"] = (int)day;

            var hourNow = date.Hour;
            var minNow = date.Minute;

            if (hourNow < 8 || (hourNow > 15 && minNow > 24))
            {
                return null;  // пар нет - не начались \закончились сегодня 
            }
            int lectNum = 0;
            foreach (var lecture in Lecture.list)
            {
                if (((lecture.hSt < hourNow) ||(lecture.hSt == hourNow && lecture.mSt <= minNow)) && ((hourNow == lecture.hEnd && minNow < lecture.mEnd)||(hourNow < lecture.hEnd)))
                {
                    lectNum = lecture.number;
                    break;
                }
            }
            res["LectureNum"] = lectNum;
            // если res = 0 значит доступного предмета нет 

            return res;
        }

    }

    class Lecture
    {
        public int number { get; set; }
        public int hSt { get; set; }
        public int mSt { get; set; }
        public int hEnd { get; set; }
        public int mEnd { get; set; }

        public static List<Lecture> list = new List<Lecture> {
            new Lecture { number = 1, hSt = 8, mSt = 0, hEnd = 9, mEnd = 35 },
            new Lecture { number = 2, hSt = 9, mSt = 50, hEnd = 11, mEnd = 25 },
            new Lecture { number = 3, hSt = 11, mSt = 40, hEnd = 13, mEnd = 15 },
            new Lecture { number = 4, hSt = 13, mSt = 50, hEnd = 15, mEnd = 25 }
        };
    }
}
