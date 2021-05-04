using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ServerET.Models
{
    public class Group
    {
        public int Id { get; set; }
        public int GroupNum { get; set; }

        public List<Student> Students { get; set; } = new List<Student>();
        public List<Timetable> Timetables { get; set; } = new List<Timetable>();
    }
}