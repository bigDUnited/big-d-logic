using System.Linq;

namespace big_d_logic_c_sharp
{
    public class Query : Fact
    {
        public Query(params string[] args) : base(args)
        {
            IsMissingArgs = false;
            FindMissingArguments();
        }

        public int[] MissingArgs { get; private set; }

        public bool IsMissingArgs { get; private set; }

        private void FindMissingArguments()
        {
            MissingArgs = new int[ChildrenList.Count];
            for (var i = 0; i < ChildrenList.Count; i++)
                if (IsAllUpper(ChildrenList[i]))
                {
                    MissingArgs[i] = 1;
                    IsMissingArgs = true;
                }
                else
                {
                    MissingArgs[i] = 0;
                }
        }

        private static bool IsAllUpper(string input)
        {
            return input.All(t => !char.IsLetter(t) || char.IsUpper(t));
        }
    }
}