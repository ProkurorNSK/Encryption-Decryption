import java.util.*;

public class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        String word = scanner.next();

        List<Integer> lengths = new ArrayList<>();
        char[] vowels = {'a', 'e', 'i', 'o', 'u', 'y'};

        boolean isPastVowel = false;
        int length = 0;

        for (char c : word.toCharArray()) {
            if (Arrays.binarySearch(vowels, c) >= 0) {
                if (isPastVowel) {
                    length++;
                } else {
                    lengths.add(length);
                    isPastVowel = true;
                    length = 1;
                }
            } else {
                if (!isPastVowel) {
                    length++;
                } else {
                    lengths.add(length);
                    isPastVowel = false;
                    length = 1;
                }
            }
        }
        lengths.add(length);

        int result = 0;
        for (int len : lengths) {
            result += (len - 1) / 2;
        }

        System.out.println(result);
    }
}