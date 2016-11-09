using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace big_d_logic_marek
{
    class Program
    {
        private static void Main(string[] args)
        {
            Fact f1 = new Fact("father", "a", "b", "c");
            Fact f2 = new Fact("father", "a", "b");
            Fact f3 = new Fact("father", "a", "a");
            Fact f4 = new Fact("father", "z", "a");
            Fact f5 = new Fact("father", "b", "c");
            Fact f6 = new Fact("father", "b", "a");

            Query q1 = new Query("father", "a", "b", "c");
            Query q2 = new Query("father", "a", "b");
            Query q3 = new Query("father", "b", "a");
            Query q4 = new Query("father", "a", "a");

            Parser parser = new Parser();

            
            parser.AddFact(f1);
            parser.AddFact(f2);
            parser.AddFact(f3);
            parser.AddFact(f4);
            parser.AddFact(f5);
            parser.AddFact(f6);
            
            bool result = parser.QueryFact(q1);
            bool result2 = parser.QueryFact(q2);
            bool result3 = parser.QueryFact(q3);
            bool result4 = parser.QueryFact(q4);
            
            Console.WriteLine(result);
            Console.WriteLine(result2);
            Console.WriteLine(result3);
            Console.WriteLine(result4);
            Console.ReadLine();
        }
    }
}