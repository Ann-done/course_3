using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data.Entity;

namespace ServerET.Models
{
    public class TestContext: DbContext
    {
        public DbSet<Group> Groups { get; set; }
        public DbSet<Student> Students { get; set; }
        public DbSet<Subject> Subjects { get; set; }
        public DbSet<Topic> Topics { get; set; }
        public DbSet<Question> Questions { get; set; }
        public DbSet<Answer> Answers { get; set; }
        public DbSet<Timetable> Timetables { get; set; }
        public DbSet<Result> Results { get; set; }
    }

    //public class TestDbInitializer : DropCreateDatabaseAlways<TestContext>
    //{
    //    protected override void Seed(TestContext db)
    //    {
    //        // groups 
    //        Group group1 = new Group { GroupNum = 1 };
    //        db.Groups.Add(group1);
    //        //db.Groups.Add(new Group { GroupNum = 1});
    //        db.Groups.Add(new Group { GroupNum = 2 });

    //        //sub gr
    //        Subgroup subgroup1 = new Subgroup { SubgroupNum = 1 };
    //        db.Subgroups.Add(subgroup1);
    //        // db.Subgroups.Add(new Subgroup { SubgroupNum = 1 });

    //        //
    //        Student student = new Student 
    //        {
    //            Card = "asldkqwihhuasff",
    //            LastName = "Kalganova",
    //            FirstName = "Anna",
    //            Otchestvo = "Andreevna",
    //            IsLogIn = 0,
    //            Group = group1,
    //            Subgroup = subgroup1
    //        };
    //        db.Students.Add(student);

    //        //
    //        Subject subject = new Subject { ShortName = "BD", FullName = "Database" };
    //        db.Subjects.Add(subject);

    //        //
    //        Topic topic = new Topic { TopicName = "First lab", Subject = subject };
    //        db.Topics.Add(topic);

    //        //

    //        base.Seed(db);
    //    }
    //}
}