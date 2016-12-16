using System.Collections.Generic;

namespace big_d_logic_c_sharp
{
    public class Fact
    {
        public Fact(params string[] args)
        {
            //first argument is the functor
            Functor = args[0];
            ChildrenList = new List<string>();
            for (var i = 1; i < args.Length; i++)
                ChildrenList.Add(args[i]);
        }

        public string Functor { get; private set; }
        public List<string> ChildrenList { get; private set; }
    }
}