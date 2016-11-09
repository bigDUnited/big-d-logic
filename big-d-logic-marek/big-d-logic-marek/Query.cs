namespace big_d_logic_marek
{
    public class Query : Fact
    {
        public Query(string functor, params string[] children) : base(functor, children)
        {

        }
    }
}