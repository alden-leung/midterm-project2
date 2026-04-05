package prog2.edu.slu;
import prog2.edu.slu.pregroup01.Fraction;

import javax.swing.*;
import java.util.Scanner;

public class FractionTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        MixedNumber[] history = new MixedNumber[100];
        int count = 0;
        int choice = 0;
        while (true) {
            do {
                System.out.println("\n--- FRACTION CALCULATOR ---");
                System.out.println("1. Add");
                System.out.println("2. Subtract");
                System.out.println("3. Multiply");
                System.out.println("4. Divide");
                System.out.println("5. Exit");
                System.out.print("Choose: ");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5){
                    System.out.println("Invalid choice. Please try again.");
                }
            }while (choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5);

            if (choice == 5) break;

            Fraction f1;
            Fraction f2;
            try {
                System.out.print("Enter first number: ");
                f1 = extractInput(sc.nextLine());

                System.out.print("Enter second number: ");
                f2 = extractInput(sc.nextLine());


            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
                continue;
            }

            MixedNumber result = null;
            MixedNumber m1 = (f1 instanceof MixedNumber) ? (MixedNumber) f1 : new MixedNumber(f1);
            MixedNumber m2 = (f2 instanceof MixedNumber) ? (MixedNumber) f2 : new MixedNumber(f2);

            switch (choice) {
                case 1:
                    result = m1.add(m2);
                    break;
                case 2:
                    result = m1.subtract(m2);
                    break;
                case 3:
                    result = m1.multiplyBy(m2);
                    break;
                case 4:
                    result = m1.divideBy(m2);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    continue;
            }

            history[count++] = result;

            System.out.println("Result (prog2.edu.slu.pregroup01.Fraction): " + result);
            System.out.println("Result (Decimal): " + result.toDouble());

            if (count >= 100) {
                System.out.println("History full (100 operations stored).");
                break;
            }
        }

        System.out.println("HISTORY");
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + ": " + history[i]);
        }
        sc.close();
    }

    private static MixedNumber extractInput(String input) throws IllegalArgumentException {
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty");
        }

        int whole = 0;
        int numerator = 0;
        int denominator = 1;

        try {
            if (input.contains(" ")) {
                String[] parts = input.split(" ");
                if (parts.length != 2) throw new IllegalArgumentException("Invalid mixed fraction format");

                whole = Integer.parseInt(parts[0].trim());

                String[] fracParts = parts[1].split("/");
                if (fracParts.length != 2) throw new IllegalArgumentException("Invalid fraction format");

                numerator = Integer.parseInt(fracParts[0].trim());
                denominator = Integer.parseInt(fracParts[1].trim());
            } else if (input.contains("/")) {
                String[] fracParts = input.split("/");
                if (fracParts.length != 2) throw new IllegalArgumentException("Invalid fraction format");

                numerator = Integer.parseInt(fracParts[0].trim());
                denominator = Integer.parseInt(fracParts[1].trim());
            } else {
                whole = Integer.parseInt(input);
            }

            Fraction fractionPart = new Fraction(numerator, denominator);
            return new MixedNumber(whole, fractionPart);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format: " + input);
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }
    }
}