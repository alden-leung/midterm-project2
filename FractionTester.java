package prog2.edu.slu;
import java.util.Scanner;

public class FractionTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Fraction[] history = new Fraction[100];
        int count = 0;

        while (true) {
            System.out.println("\n--- FRACTION CALCULATOR ---");
            System.out.println("1. Add");
            System.out.println("2. Subtract");
            System.out.println("3. Multiply");
            System.out.println("4. Divide");
            System.out.println("5. Exit");
            System.out.print("Choose: ");

            int choice = sc.nextInt();

            if (choice == 5) break;

            System.out.print("Enter first number (can be decimal): ");
            double d1 = sc.nextDouble();

            System.out.print("Enter second number (can be decimal): ");
            double d2 = sc.nextDouble();

            Fraction f1 = convertToFraction(d1);
            Fraction f2 = convertToFraction(d2);

            Fraction result = null;

            switch (choice) {
                case 1:
                    result = f1.add(f2);
                    break;
                case 2:
                    result = f1.subtract(f2);
                    break;
                case 3:
                    result = f1.multiplyBy(f2);
                    break;
                case 4:
                    result = f1.divideBy(f2);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    continue;
            }

            history[count++] = result;

            System.out.println("Result (Fraction): " + result);
            System.out.println("Result (Decimal): " + result.toDouble());

            if (count >= 100) {
                System.out.println("History full (100 operations stored).");
                break;
            }
        }

        System.out.println("\n--- HISTORY ---");
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + ": " + history[i]);
        }

        sc.close();
    }

    // Converts double to fraction
    public static Fraction convertToFraction(double value) {
        int scale = 10000; // precision (4 decimal places)
        int numerator = (int) Math.round(value * scale);
        int denominator = scale;
        return new Fraction(numerator, denominator);
    }
}