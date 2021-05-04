using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ServerET.Models
{
    public class Topic
    {
        public int Id { get; set; }
        public string TopicName { get; set; }

        public int SubjectId { get; set; }
        public Subject Subject { get; set; }

        public List<Question> Questions { get; set; } = new List<Question>();
        public List<Result> Results { get; set; } = new List<Result>();
    }
}