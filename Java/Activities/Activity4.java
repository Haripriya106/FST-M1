package Activities;

public class Activity4 {
	public static void main(String[] args) {

        int[] numbers = {7, 8, 3, 1, 10, 4};

        System.out.println("Before sorting:");
        for (int n : numbers) {
            System.out.print(n + " ");
        }

        // Insertion Sort
        for (int i = 1; i < numbers.length; i++) {
            int key = numbers[i];
            int j = i - 1;

            while (j >= 0 && numbers[j] > key) {
                numbers[j + 1] = numbers[j];
                j--;
            }

            numbers[j + 1] = key;
        }

        System.out.println("\nAfter sorting:");
        for (int n : numbers) {
            System.out.print(n + " ");
        }
    }
}

