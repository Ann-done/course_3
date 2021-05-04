using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ServerET.Models
{
    public class Question
    {
        public int Id { get; set; }
        public string QuName { get; set; }

        public int TopicId { get; set; }
        public Topic Topic { get; set; }

        public List<Answer> Answers { get; set; } = new List<Answer>();
    }
}