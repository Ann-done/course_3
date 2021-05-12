using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ServerET.Models
{
    public class Task
    {
        public string question { get; set; }
        public List<int> right_ids { get; set; }
        public Dictionary<int,string> answers { get; set; }
    }
}