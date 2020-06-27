import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sum = 0;
        while (scanner.hasNextInt()) {
            int x = scanner.nextInt();
            if (x == 0) {
                break;
            } else {
                sum += x;
            }
        }
        System.out.println(sum);
    }
}