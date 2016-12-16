using System.Collections.Generic;
using System.Linq;

namespace big_d_logic_c_sharp
{
    public class Computer
    {
        private readonly List<Fact> _facts;

        public Computer()
        {
            _facts = new List<Fact>();
        }

        public void AddFact(Fact newFact)
        {
            _facts.Add(newFact);
        }

        public string Query(Query query)
        {
            var resultingFacts = _facts.Where(f => f.Functor.Equals(query.Functor));
            foreach (var resultingFact in resultingFacts)
            {
                //if the number of children is not the same I don't even have to compare the two
                if (resultingFact.ChildrenList.Count != query.ChildrenList.Count)
                    continue;

                if (query.IsMissingArgs)
                {
                    var missingArgsString = "";
                    var argsFound = true;
                    //treat as query to find missing information
                    for (var i = 0; i < query.MissingArgs.Length; i++)
                    {
                        var queryArg = query.ChildrenList[i];
                        var factArg = resultingFact.ChildrenList[i];
                        if (query.MissingArgs[i] == 1)
                        {
                            //this argument is missing so we fill it up
                            missingArgsString += queryArg + ": " + factArg + "\n";
                        }
                        else
                        {
                            //this argument from query must match the same argument from the fact, otherwise move to next fact
                            if (!queryArg.Equals(factArg))
                            {
                                i = query.MissingArgs.Length;
                                argsFound = false;
                            }
                        }
                    }
                    if (argsFound)
                        return missingArgsString;
                }
                else
                {
                    //if all the children are the same
                    var childrenSame = true;
                    for (var i = 0; i < resultingFact.ChildrenList.Count; i++)
                        if (!resultingFact.ChildrenList[i].Equals(query.ChildrenList[i]))
                        {
                            i = resultingFact.ChildrenList.Count;
                            childrenSame = false;
                        }
                    //if there is one fact where all the children are the same our fact is true, otherwise we move on to the next fact
                    if (childrenSame)
                        return "true";
                }
            }
            return "false";
        }
    }
}