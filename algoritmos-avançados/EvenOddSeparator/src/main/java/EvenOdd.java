public class EvenOdd {

    public static int iterações = 0;

    public int[] evenOddSeparatorWithAnotherArray(int[] array){
        int begin = 0;
        int end = array.length-1;
        int index = 0;
        int[] result = new int[array.length];
        while(begin < end){
            iterações++;
            if(array[index] % 2 == 0) {
                result[begin] = array[index];
                begin++;
            }else{
                result[end] = array[index];
                end--;
            }
            index++;
        }
        System.out.println(iterações + " iterações sem array auxiliar para vetor de "+ array.length + " posições");
        zeraIteracoes();
        return result;
    }

    public int[] evenOddSeparatorWithoutAnotherArray(int[] array){
        int begin = 0;
        int end = array.length-1;
        int index = 0;
        while(begin < end){
            while(array[begin] % 2 != 1 && begin < end) {
                begin++;
                iterações++;
            }
            while(array[end] % 2 != 0 && begin < end) {
                end--;
                iterações++;
            }
            int aux = array[begin];
            array[begin] = array[end];
            array[end] = aux;
        }
        System.out.println(iterações + " iterações sem array auxiliar para vetor de "+ array.length + " posições");
        zeraIteracoes();
        return array;
    }

    public String toString(int[] array){
        String result = "";
        for(int i : array)
            result += i + ", ";
        return result;
    }

    public static int getIterações() {
        return iterações;
    }

    public void zeraIteracoes(){
        iterações = 0;
    }

    public int[] randomArrayGeneratorOddorEven(int even, int odd){
        int[] array = new int[even+odd];
        int index = 0;
        while(index < array.length){
            int randomNumber = (int) (Math.random()*100);
            if(randomNumber % 2 == 0 && even != 0) {
                array[index] = randomNumber;
                even--;
            }
            if(randomNumber % 2 == 1 && odd != 0) {
                array[index] = randomNumber;
                odd--;
            }
            index++;
        }
        return array;
    }

}
