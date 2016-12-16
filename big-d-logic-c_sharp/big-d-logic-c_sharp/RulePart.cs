namespace big_d_logic_c_sharp
{
    public class RulePart : Query
    {
        private string _operation;

        public RulePart(string operation, params string[] args) : base(args)
        {
            _operation = operation;
        }
    }
}