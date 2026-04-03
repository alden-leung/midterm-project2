import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
public class FractionTester {
    private static JTextField display;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Fraction[] history = new Fraction[100];
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
                if (choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5){
                    System.out.println("Invalid choice. Please try again.");
                }
            }while (choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5);

            if (choice == 5) break;

            System.out.print("Enter first number: ");
            String input1 = sc.next();

            System.out.print("Enter second number: ");
            String input2 = sc.next();

            Fraction f1 = new Fraction(input1);
            Fraction f2 = new Fraction(input2);

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

        System.out.println("HISTORY");
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + ": " + history[i]);
        }
        sc.close();
        
        //ui
        JFrame frame = new JFrame("Fraction Calculator");
        frame.setSize(350, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(Color.BLACK);

        JTextField display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 36));
        display.setForeground(Color.WHITE);
        display.setBackground(Color.BLACK);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false); // UI only
        display.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        frame.add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(6, 4, 10, 10));
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] buttons = {
                "C", "(", ")", "/",
                "7", "8", "9", "*",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "0", ".", "=", "",
                "Frac", "Mixed", "ToDouble", ""
        };

        for (String text : buttons) {

            if (text.equals("")) {
                panel.add(new JLabel()); 
                continue;
            }

            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.setFocusPainted(false);
            btn.setEnabled(true);

            
            if (text.matches("[0-9]")) {
                btn.setBackground(new Color(50, 50, 50));
                btn.setForeground(Color.WHITE);
            } else if (text.equals("=")) {
                btn.setBackground(Color.ORANGE);
                btn.setForeground(Color.WHITE);
            } else if (text.equals("C")) {
                btn.setBackground(Color.RED);
                btn.setForeground(Color.WHITE);
            } else {
                btn.setBackground(new Color(80, 80, 80));
                btn.setForeground(Color.WHITE);
            }

            panel.add(btn);
        }

        frame.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);
     
    }    
}