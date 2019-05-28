import org.junit.Before;
import org.junit.Test;

public class EvenOddTest {

    private int[] array;
    private int[][] testArray;
    private EvenOdd evenOdd;

    @Before
    public void setup() {
        evenOdd = new EvenOdd();
        testArray = new int[][]{{50, 50}, {20, 80}, {80, 20}, {50000, 50000}, {20000, 80000}, {80000, 20000}};
    }

    @Test
    public void testWithAnotherArray() {
        for (int[] x : testArray) {
            array = evenOdd.evenOddSeparatorWithAnotherArray(
                    evenOdd.randomArrayGeneratorOddorEven(x[0], x[1]));
            int index = 0;
            while (array[index] % 2 == 0)
                index++;
            while (index < array.length) {
                assert (array[index] % 2 == 1);
                index++;
            }
        }
    }

    @Test
    public void testWithoutAnotherArray() {
        for (int[] x : testArray) {
            array = evenOdd.evenOddSeparatorWithoutAnotherArray(
                    evenOdd.randomArrayGeneratorOddorEven(x[0], x[1]));
            int index = 0;
            while (array[index] % 2 == 0)
                index++;
            while (index < array.length) {
                assert (array[index] % 2 == 1);
                index++;
            }
        }
    }

}
