using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ServerET.Models
{
    public class Answer
    {
        public int Id { get; set; }
        public string Text { get; set; }
        public int IsRight { get; set; }

        public int QuestionId { get; set; }
        public Question Question{ get; set; }
    }
}