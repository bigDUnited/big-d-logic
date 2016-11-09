using System.Collections.Generic;
using System.Linq;

namespace big_d_logic_marek
{
    public class Parser
    {
        private List<Fact> _facts;

        public Parser()
        {
            _facts = new List<Fact>();
        }

        public void AddFact(Fact newFact)
        {
            _facts.Add(newFact);
        }

        public bool QueryFact(Fact query)
        {
            var resultingFacts = _facts.Where(f => f.Functor.Equals(query.Functor));
            foreach (var resultingFact in resultingFacts)
            {
                //if the number of children is not the same I don't even have to compare the two
                if (resultingFact.ChildrenList.Count != query.ChildrenList.Count)
                {
                    continue;
                }
                //if all the children are the same
                var childrenSame = true;
                for (int i = 0; i < resultingFact.ChildrenList.Count; i++)
                {
                    if (!resultingFact.ChildrenList[i].Equals(query.ChildrenList[i]))
                    {
                        i = resultingFact.ChildrenList.Count;
                        childrenSame = false;
                    }
                }
                //if there is one fact where all the children are the same our fact is true, otherwise we move on to the next fact
                if (childrenSame)
                {
                    return true;
                }
            }
            return false;
        }
    }
}