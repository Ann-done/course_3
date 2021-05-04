using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ServerET.Models
{
    public class Result
    {
        public int Id { get; set; }
        public int QuNum { get; set; }
        public int RightAnswNum { get; set; }
        public int Mark { get; set; }
        public DateTime DateTime { get; set; }

        public string StudentId { get; set; }
        public Student Student { get; set; }

        public int TopicId { get; set; }
        public Topic Topic { get; set; }
    }
}