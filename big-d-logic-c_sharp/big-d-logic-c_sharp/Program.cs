using System;

namespace big_d_logic_c_sharp
{
    internal class Program
    {
        private static void Main(string[] args)
        {
            var f1 = new Fact("father", "a", "b", "c");
            var f2 = new Fact("father", "a", "b");
            var f3 = new Fact("father", "a", "a");
            var f4 = new Fact("father", "z", "a");
            var f5 = new Fact("father", "b", "c");
            var f6 = new Fact("father", "b", "a");

            var q1 = new Query("father", "X", "b");
            var q2 = new Query("father", "AB", "b");
            var q3 = new Query("father", "Example", "a");
            var q4 = new Query("father", "a", "a");

            var computer = new Computer();


            computer.AddFact(f1);
            computer.AddFact(f2);
            computer.AddFact(f3);
            computer.AddFact(f4);
            computer.AddFact(f5);
            computer.AddFact(f6);

            var result = computer.Query(q1);
            var result2 = computer.Query(q2);
            var result3 = computer.Query(q3);
            var result4 = computer.Query(q4);

            Console.WriteLine(result);
            Console.WriteLine(result2);
            Console.WriteLine(result3);
            Console.WriteLine(result4);
            Console.ReadLine();
        }
    }
}