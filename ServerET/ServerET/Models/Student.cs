using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ServerET.Models
{
    public class Student
    {
        public string Id { get; set; }  
        public string LastName { get; set; }
        public string FirstName { get; set; }
        public string Otchestvo { get; set; }
        public int IsLogIn { get; set; } //1 - в системе , 0 - вышел из системы 

        public int GroupId { get; set; }
        public Group Group { get; set; }

        public List<Result> Results { get; set; } = new List<Result>();
    }
}