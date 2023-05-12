import java.util.Arrays;
import java.util.Random;

public class MainClass{
    private static final int ARRAY_LENGTH = 10000;

    public static void main(String[] args) {
        int[] input1 = generateRandomArray();
        int[] input2 = generateRandomArray();
        
        // synchronous multiplication
        long time1 = System.currentTimeMillis();
        int[] result1 = multiplyArraysSync(input1, input2, 0);
        System.out.printf("sync : %s\n", System.currentTimeMillis() - time1);

        // parallel multiplication
        long time2 = System.currentTimeMillis();
        int[] result2 = multiplyArraysParallel(input1, input2, 0);
        System.out.printf("parallel : %s\n", System.currentTimeMillis() - time2);

        // check if results are the same
        System.out.println(Arrays.equals(result1, result2));
    }

    private static int[] generateRandomArray() {
        int[] arr = new int[ARRAY_LENGTH];
        Random rand = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rand.nextInt(101); 
        }
        return arr;
    }

    private static int[] multiplyArraysSync(int[] arr1, int[] arr2, int sleep) {
        int[] result = new int[ARRAY_LENGTH];
        for (int i = 0; i < arr1.length; i++) {
            result[i] = arr1[i] * arr2[i];
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    private static int[] multiplyArraysParallel(int[] arr1, int[] arr2, int sleep) {
        int[] result = new int[ARRAY_LENGTH];
        Arrays.parallelSetAll(result, i -> arr1[i] * arr2[i]);
        Arrays.stream(result).forEach(x -> {
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return result;
    }
}