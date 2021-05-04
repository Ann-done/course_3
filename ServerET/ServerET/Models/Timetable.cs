using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ServerET.Models
{
    public class Timetable
    {
        public int Id { get; set; }
        public int DayOfWeek { get; set; } // 0 - Вс, 1 - Пн .. 6 - Сб
        public int LectureNum { get; set; }  // 1 .. 4 

        public int SubjectId { get; set; }
        public Subject Subject { get; set; }

        public int GroupId { get; set; }
        public Group Group { get; set; }

    }
}