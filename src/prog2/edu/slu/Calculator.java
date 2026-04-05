package prog2.edu.slu;

import prog2.edu.slu.pregroup01.Fraction;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Calculator extends JFrame {

    public static void main(String[] args) {
        Calculator app = new Calculator();
        app.run();
    }

    private void run() {
        this.setTitle("Calculator");
        this.setSize(650, 340);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(10, 10));

        initializeComponents();

        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    private void initializeComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // north border
        JLabel header = new JLabel("Fraction Calculator");

        styleLabel(header, true);

        mainPanel.add(header, BorderLayout.NORTH);

        // center border
        JPanel centerContainer = new JPanel();
        centerContainer.setLayout(new BoxLayout(centerContainer, BoxLayout.X_AXIS));
        centerContainer.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTextField fraction1TF = new JTextField();
        JTextField fraction2TF = new JTextField();
        JComboBox<String> operationDropdown = new JComboBox<>(new String[]{"+", "-", "x", "/"});

        styleTextField(fraction1TF, true);
        styleTextField(fraction2TF, true);
        styleDropdown(operationDropdown);

        centerContainer.add(fraction1TF);
        centerContainer.add(Box.createRigidArea(new Dimension(10, 0)));
        centerContainer.add(operationDropdown);
        centerContainer.add(Box.createRigidArea(new Dimension(10, 0)));
        centerContainer.add(fraction2TF);

        mainPanel.add(centerContainer, BorderLayout.CENTER);

        // south border
        JPanel southContainer = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton calculateButton = new JButton("Calculate");
        JLabel fractionLB = new JLabel("Fraction: ");
        JLabel decimalLB = new JLabel("Decimal: ");
        JTextField fractionResultTF = new JTextField();
        JTextField decimalResultTF = new JTextField();

        calculateButton.addActionListener(e -> {
            try {
                Fraction fraction1 = extractInput(fraction1TF);
                Fraction fraction2 = extractInput(fraction2TF);
                String operation = (String) operationDropdown.getSelectedItem();

                MixedNumber result;
                MixedNumber mixedNumber1 = (fraction1 instanceof MixedNumber) ? (MixedNumber) fraction1 : new MixedNumber(fraction1);
                MixedNumber mixedNumber2 = (fraction2 instanceof MixedNumber) ? (MixedNumber) fraction2 : new MixedNumber(fraction2);

                result = calculate(mixedNumber1, mixedNumber2, operation);

                fractionResultTF.setText(result.toString());
                decimalResultTF.setText(String.valueOf(result.toDouble()));
            } catch (InvalidMixedNumberException exception) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + exception.getMessage());
            } catch (ArithmeticException exception) {
                fractionResultTF.setText("Error: " + exception.getMessage());
            } catch (Exception exception) {
                fractionResultTF.setText("Error");
            }
        });

        styleButton(calculateButton);
        styleLabel(fractionLB, false);
        styleTextField(fractionResultTF, false);
        styleLabel(decimalLB, false);
        styleTextField(decimalResultTF, false);

        gbc.weightx = 1.0; gbc.weighty = 1.0;
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        southContainer.add(calculateButton, gbc);

        gbc.gridwidth = 1;
        gbc.weightx = 0; gbc.weighty = 0;
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        southContainer.add(fractionLB, gbc);

        gbc.weightx = 1.0; gbc.weighty = 1.0;
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        southContainer.add(fractionResultTF, gbc);

        gbc.weightx = 0; gbc.weighty = 0;
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        southContainer.add(decimalLB, gbc);

        gbc.weightx = 1.0; gbc.weighty = 1.0;
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        southContainer.add(decimalResultTF, gbc);

        // add components to frame
        mainPanel.add(southContainer, BorderLayout.SOUTH);
        this.add(mainPanel);
    }

    private void styleTextField(JTextField textField, boolean editable) {
        textField.setFont(new Font("Arial", Font.PLAIN, 20));
        textField.setPreferredSize(new Dimension(200, 40));
        textField.setMinimumSize(new Dimension(200, 40));
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setBorder(new EmptyBorder(0, 10, 0, 0));

        if (!editable) {
            textField.setHorizontalAlignment(JTextField.LEFT);
            textField.setEnabled(false);
            textField.setDisabledTextColor(Color.BLACK);
        }
    }

    private void styleDropdown(JComboBox dropdown) {
        dropdown.setFont(new Font("Arial", Font.BOLD, 20));

        dropdown.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setHorizontalAlignment(SwingConstants.CENTER); // center text
                return label;
            }
        });

        dropdown.setPreferredSize(new Dimension(120, 50));
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void styleLabel(JLabel label, boolean isHeader) {
        if (isHeader) {
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setFont(new Font("Arial", Font.PLAIN, 32));
        } else {
            label.setFont(new Font("Arial", Font.PLAIN, 20));
        }
    }

    private MixedNumber calculate(MixedNumber firstFraction, MixedNumber secondFraction, String operation) {
        MixedNumber result = null;

        switch (operation) {
            case "+" -> {
                result = firstFraction.add(secondFraction);
            }
            case "-" -> {
                result = firstFraction.subtract(secondFraction);
            }
            case "x" -> {
                result = firstFraction.multiplyBy(secondFraction);
            }
            case "/" -> {
                result = firstFraction.divideBy(secondFraction);
            }
        }

        return result;
    }

    private MixedNumber extractInput(JTextField textField) throws InvalidMixedNumberException {
        String input = textField.getText().trim();

        if (input.isEmpty()) {
            throw new InvalidMixedNumberException("Input cannot be empty");
        }

        int whole = 0;
        int numerator = 0;
        int denominator = 1;

        try {
            if (input.contains(" ")) {
                String[] parts = input.split(" ");
                if (parts.length != 2) throw new InvalidMixedNumberException("Invalid mixed fraction format");

                whole = Integer.parseInt(parts[0].trim());

                String[] fracParts = parts[1].split("/");
                if (fracParts.length != 2) throw new InvalidMixedNumberException("Invalid fraction format");

                numerator = Integer.parseInt(fracParts[0].trim());
                denominator = Integer.parseInt(fracParts[1].trim());
            } else if (input.contains("/")) {
                String[] fracParts = input.split("/");
                if (fracParts.length != 2) throw new InvalidMixedNumberException("Invalid fraction format");

                numerator = Integer.parseInt(fracParts[0].trim());
                denominator = Integer.parseInt(fracParts[1].trim());
            } else {
                whole = Integer.parseInt(input);
            }

            Fraction fractionPart = new Fraction(numerator, denominator);
            return new MixedNumber(whole, fractionPart);
        } catch (NumberFormatException e) {
            throw new InvalidMixedNumberException("Invalid number format: " + input);
        } catch (ArithmeticException e) {
            throw new InvalidMixedNumberException("Denominator cannot be zero.");
        }
    }

    public class InvalidMixedNumberException extends Exception {
        public InvalidMixedNumberException(String message) {
            super(message);
        }
    }
}
