using System;
using System.Collections.Generic;
using System.Linq;

namespace big_d_logic_marek
{
    public class Fact
    {
        public string Functor { get; private set; }
        public List<string> ChildrenList { get; private set; }

        public Fact(string functor, params string[] children)
        {
            this.Functor = functor;
            ChildrenList = children.ToList();
        }
    }
}