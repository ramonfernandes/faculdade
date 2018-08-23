public class Main {

    public static void main(String[] args) {
//        100 elementos, 50 pares e 50 ímpares;
        EvenOdd evenOdd = new EvenOdd();
        int[] array = evenOdd.randomArrayGeneratorOddorEven(50, 50);
        System.out.println(
                evenOdd.toString
                        (evenOdd.evenOddSeparatorWithAnotherArray(array)));

        System.out.println(
                evenOdd.toString
                        (evenOdd.evenOddSeparatorWithoutAnotherArray(array)));


//        100 elementos, 20 pares e 80 ímpares;
        array = evenOdd.randomArrayGeneratorOddorEven(20, 80);
        System.out.println(
                evenOdd.toString
                        (evenOdd.evenOddSeparatorWithAnotherArray(array)));

        System.out.println(
                evenOdd.toString
                        (evenOdd.evenOddSeparatorWithoutAnotherArray(array)));

//        100 elementos, 80 pares e 20 ímpares;
        array = evenOdd.randomArrayGeneratorOddorEven(80, 20);
        System.out.println(
                evenOdd.toString
                        (evenOdd.evenOddSeparatorWithAnotherArray(array)));

        System.out.println(
                evenOdd.toString
                        (evenOdd.evenOddSeparatorWithoutAnotherArray(array)));

//        100000 elementos, 50000 pares e 50000 ímpares;
        array = evenOdd.randomArrayGeneratorOddorEven(50000, 50000);
        System.out.println(
                evenOdd.toString
                        (evenOdd.evenOddSeparatorWithAnotherArray(array)));

        System.out.println(
                evenOdd.toString
                        (evenOdd.evenOddSeparatorWithoutAnotherArray(array)));

//        100000 elementos, 20000 pares e 80000 ímpares;
        array = evenOdd.randomArrayGeneratorOddorEven(20000, 80000);
        System.out.println(
                evenOdd.toString
                        (evenOdd.evenOddSeparatorWithAnotherArray(array)));

        System.out.println(
                evenOdd.toString
                        (evenOdd.evenOddSeparatorWithoutAnotherArray(array)));

//        100000 elementos, 80000 pares e 20000 ímpares;
        array = evenOdd.randomArrayGeneratorOddorEven(80000, 20000);
        System.out.println(
                evenOdd.toString
                        (evenOdd.evenOddSeparatorWithAnotherArray(array)));

        System.out.println(
                evenOdd.toString
                        (evenOdd.evenOddSeparatorWithoutAnotherArray(array)));
    }
}
