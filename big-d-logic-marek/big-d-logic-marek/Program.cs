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
            var f1 = new Fact("father", "a", "b", "c");
            var f2 = new Fact("father", "a", "b");
            var f3 = new Fact("father", "a", "a");
            var f4 = new Fact("father", "z", "a");
            var f5 = new Fact("father", "b", "c");
            var f6 = new Fact("father", "b", "a");

            var q1 = new Query("father", "X", "C");
            var q2 = new Query("father", "AB", "b");
            var q3 = new Query("father", "Basa", "a");
            var q4 = new Query("father", "a", "a");

            var parser = new Computer();

            
            parser.AddFact(f1);
            parser.AddFact(f2);
            parser.AddFact(f3);
            parser.AddFact(f4);
            parser.AddFact(f5);
            parser.AddFact(f6);
            
            var result = parser.Query(q1);
            //var result2 = parser.Query(q2);
            //var result3 = parser.Query(q3);
            //var result4 = parser.Query(q4);
            
            Console.WriteLine(result);
            //Console.WriteLine(result2);
            //Console.WriteLine(result3);
            //Console.WriteLine(result4);
            Console.ReadLine();
        }
    }
}