using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ServerET.Models
{
    public class Subject
    {
        public int Id { get; set; }
        public string ShortName { get; set; }
        public string FullName { get; set; }

        public List<Topic> Topics { get; set; } = new List<Topic>();
        public List<Timetable> Timetables { get; set; } = new List<Timetable>();
    }
}