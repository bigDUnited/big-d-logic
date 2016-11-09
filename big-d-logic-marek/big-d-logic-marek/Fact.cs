using System.Collections.Generic;

namespace big_d_logic_marek
{
    public class Fact
    {
        public string Functor { get; private set; }
        public List<string> ChildrenList { get; private set; }

        public Fact(params string[] args)
        {
            //first argument is the functor
            Functor = args[0];
            ChildrenList = new List<string>();
            for (var i = 1; i < args.Length; i++)
            {
                //iterate the array of arguments after the 1st one to make the children
                ChildrenList.Add(args[i]);
            }
        }
    }
}