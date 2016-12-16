using System;

namespace big_d_logic_c_sharp
{
    public class Rule
    {
        public Rule(params RulePart[] args)
        {
            if (args.Length%2 == 0)
            {
                //no need to panic all the parts are good
            }
            else
            {
                throw new Exception("You need more arguments");
            }
            var fact = args[0];
            for (var i = 1; i < args.Length; i++)
            {
            }
        }
    }
}