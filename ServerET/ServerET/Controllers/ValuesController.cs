using Newtonsoft.Json;
using ServerET.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Data.Entity;


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

        // хэшировать id 

        [HttpPost]
        [Route("api/values/login")]
        public Dictionary<string, string> LogIn([FromBody]string json) // по номеру карты 
        {

            var myJsonObj = JsonConvert.DeserializeObject<Dictionary<string, dynamic>>(json);
            var id = myJsonObj["id"];
            var lastname = myJsonObj["lastname"];

            Student student = db.Students.Find(id);
            if (student == null || !student.LastName.Equals(lastname))
            {
                return new Dictionary<string, string> { { "message", "no such student" } };
            }
            if (student.IsLogIn == 1)
            {
                return new Dictionary<string, string> { { "message", "you are already logged in" } };
            }
            student.IsLogIn = 1;
            db.SaveChanges();

            var groupId = student.GroupId;
            var table = from t in db.Timetables
                        where t.GroupId == groupId && t.LectureNum == 3 && t.DayOfWeek == 3
                        select t.Subject;

            var result = new Dictionary<string, string> {
                {"id", student.Id.ToString() },
                {"LastName", student.LastName },
                {"FirstName", student.FirstName },
                {"GroupId", student.GroupId.ToString() }
            };

            if (table.FirstOrDefault() == null)
            {
                result["SubjectId"] = null;
            }
            else
            {
                Subject subj = table.FirstOrDefault();
                result["SubjectId"] = subj.Id.ToString();
                result["SubjectShortName"] = subj.ShortName;
            }
            return result;
        }

        // если авторизован (вынести часть кода в метод) 

        [Route("api/values/getsubject/{idGroup}")]
        public Subject GetSubject(int idGroup)
        {
            int[] time;
            var table = from t in db.Timetables
                        where t.GroupId == idGroup && t.LectureNum == 2 && t.DayOfWeek == 2
                        select t.Subject;
            Subject subj = table.FirstOrDefault();
            return subj;
        }

        [Route("api/values/getTopics/{idSubj}")]
        public IEnumerable<Topic> GetTopics(int idSubj)
        {
            var table = from t in db.Topics
                        where t.SubjectId == idSubj
                        select t;
            return table;
        }

        [Route("api/values/getQus/{idTopic}")]
        public IEnumerable<Question> GetQus(int idTopic)
        {
            var table = from t in db.Questions
                        where t.TopicId == idTopic
                        select t;
            return table;
        }

        [Route("api/values/getAnswers/{idQu}")]
        public IEnumerable<Answer> GetAnswers(int idQu)
        {
            var table = from t in db.Answers
                        where t.QuestionId == idQu
                        select t;
            return table;
        }

        [HttpPost] // запись результата 
        public void CreateResult([FromBody]Result result)
        {
            db.Results.Add(result);
            db.SaveChanges();
        }

        [Route("api/values/getResults/{idSt}/{idRes}")]
        public IEnumerable<Result> GetResults(string idSt, int idRes)
        {
            var table = from t in db.Results
                        where t.StudentId == idSt && t.Id > idRes
                        select t;

            return table;
        }

        [HttpGet]
        [Route("api/values/logout/{id}")]
        public Dictionary<string, string> LogOut(string id)
        {
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
            DateTime date = new DateTime();
            var day = date.DayOfWeek;
            if ((int)day == 0)
            {
                return null;
            }
            res["DayOfWeek"] = (int)day;

            var hourNow = date.Hour;
            var minNow = date.Minute;

            if (hourNow < 8 || (hourNow > 15 && minNow > 24))
            {
                return null;
            }
            int lectNum = 0;
            foreach (var lecture in Lecture.list)
            {
                if ((lecture.hSt <= hourNow && lecture.mSt <= minNow) && (hourNow <= lecture.hEnd && minNow < lecture.mEnd))
                {
                    lectNum = lecture.number;
                    break;
                }
            }
            res["LectureNum"] = lectNum;

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
