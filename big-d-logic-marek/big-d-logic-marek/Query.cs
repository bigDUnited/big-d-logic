using System;
using System.Linq;

namespace big_d_logic_marek
{
    public class Query : Fact
    {
        private bool _isMissingArgs = false;
        public Query(params string[] args) : base(args)
        {
            FindMissingArguments();
        }

        public int[] MissingArgs { get; private set; }

        public bool IsMissingArgs
        {
            get { return _isMissingArgs; }
        }

        private void FindMissingArguments()
        {
            MissingArgs = new int[ChildrenList.Count];
            for (int i = 0; i < ChildrenList.Count; i++)
            {
                if (IsAllUpper(ChildrenList[i]))
                {
                    MissingArgs[i] = 1;
                    _isMissingArgs = true;
                }
                else
                {
                    MissingArgs[i] = 0;
                }
            }
        }

        private static bool IsAllUpper(string input)
        {
            return input.All(t => !char.IsLetter(t) || char.IsUpper(t));
        }
    }
}