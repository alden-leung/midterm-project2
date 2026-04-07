package prog2.edu.slu;

import prog2.edu.slu.pregroup01.Fraction;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.util.ArrayList;

/**
 * A GUI-based Fraction Calculator using Swing.
 * <p>
 *     Provides input fields for two fractions, a dropdown to select an operation,
 *     and displays the result as both a mixed fraction and a decimal.
 * </p>
 */
public class Calculator extends JFrame {
    private JTextField fractionResultTF;
    private JTextField decimalResultTF;
    private JComboBox<String> operatorDropdown;
    JSpinner operandCounter;
    private JPanel operandPanel;
    private JButton calculateButton;
    private JLabel nFractionLB;

    private final ArrayList<JTextField> fractionFields = new ArrayList<>();
    private final ComponentStyler styler = new ComponentStyler();

    /**
     * Main method to launch the calculator.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Calculator app = new Calculator();
        app.run();
    }

    /**
     * Initializes and displays the main frame of the calculator.
     */
    private void run() {
        this.setTitle("Calculator");
        this.setSize(558, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setContentPane(initializeUI());

        setActions();

        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    /**
     * Constructs the main user interface panel.
     * @return The main JPanel containing all UI components
     */
    private JPanel initializeUI () {
        // initialize main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 0));

        // style main panel
        styler.styleMainPanel(mainPanel);

        // add contents to main panel
        mainPanel.add(createLeftPanel(), BorderLayout.WEST);
        mainPanel.add(createRightPanel(), BorderLayout.EAST);

        return mainPanel;
    }

    /**
     * Creates and configures the left panel containing
     * result fields, operator selection, and controls.
     * @return The constructed left panel.
     */
    private JPanel createLeftPanel () {
        // initialize panels
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        JPanel informationPanel = new JPanel(new GridBagLayout());

        // initialize components
        JLabel title = new JLabel("Fraction Calculator");
        JLabel fractionLB = new JLabel("Fraction: ");
        JLabel decimalLB = new JLabel("Decimal: ");
        JLabel operatorLB = new JLabel("Operator: ");
        JLabel numInputsLB = new JLabel("Inputs: ");
        fractionResultTF = new JTextField();
        decimalResultTF = new JTextField();
        operatorDropdown = new JComboBox<>(new String[]{"+", "-", "x", "/"});
        operandCounter = new JSpinner(new SpinnerNumberModel(2, 2, 15, 1));
        calculateButton = new RoundedButton("Calculate", 24, styler.PRIMARY);

        // left and right column widths
        double leftWeight = 0;
        double rightWeight = 1.0;

        // style panels
        styler.styleLeftPanel(leftPanel);
        styler.styleInformationPanel(informationPanel);

        // style components
        styler.styleLabel(title, 2, true);
        styler.styleLabel(fractionLB, 3, false);
        styler.styleLabel(decimalLB, 3, false);
        styler.styleLabel(operatorLB, 3, false);
        styler.styleLabel(numInputsLB, 3, false);
        styler.styleTextField(fractionResultTF, false);
        styler.styleTextField(decimalResultTF, false);
        styler.styleDropdown(operatorDropdown);
        styler.styleCounter(operandCounter);
        styler.styleButton(calculateButton);

        // set grid bag constraints for layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // add row 1 contents
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = leftWeight;
        informationPanel.add(fractionLB, gbc);

        gbc.gridx = 1; gbc.weightx = rightWeight;
        informationPanel.add(fractionResultTF, gbc);

        // add row 2 contents
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = leftWeight;
        informationPanel.add(decimalLB, gbc);

        gbc.gridx = 1; gbc.weightx = rightWeight;
        informationPanel.add(decimalResultTF, gbc);

        // add row 3 contents
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = leftWeight;
        informationPanel.add(operatorLB, gbc);

        gbc.gridx = 1; gbc.weightx = rightWeight;
        informationPanel.add(operatorDropdown, gbc);

        // add row 4 contents
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = leftWeight;
        informationPanel.add(numInputsLB, gbc);

        gbc.gridx = 1; gbc.weightx = rightWeight;
        informationPanel.add(operandCounter, gbc);

        // add contents to left panel
        leftPanel.add(title, BorderLayout.NORTH);
        leftPanel.add(informationPanel, BorderLayout.CENTER);
        leftPanel.add(calculateButton, BorderLayout.SOUTH);

        return leftPanel;
    }

    /**
     * Creates and configures the right panel containing
     * dynamically generated operand input fields.
     * @return The constructed right panel.
     */
    private JPanel createRightPanel () {
        // initialize right panel
        JPanel rightPanel = new RoundedPanel(60);

        // initialize components
        operandPanel = new JPanel();
        nFractionLB = new JLabel("Computing " + operandCounter.getValue() + " Operands");
        JScrollPane scrollPane = new JScrollPane(operandPanel);

        // style panel and components
        styler.styleRightPanel(rightPanel);
        styler.styleOperandPanel(operandPanel);
        styler.styleScrollPane(scrollPane);
        styler.styleLabel(nFractionLB, 2, false);

        // add components to right panel
        rightPanel.add(nFractionLB, BorderLayout.NORTH);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // update operandContainerFields
        updateFields((Integer) operandCounter.getValue());

        return rightPanel;
    }

    /**
     * Sets up all event listeners for user interactions.
     */
    private void setActions() {
        // action for increment and decrement in operand counter
        operandCounter.addChangeListener(_ -> {
            int value = (Integer) operandCounter.getValue();

            nFractionLB.setText("Computing " + operandCounter.getValue() + " Operands");

            updateFields(value);
        });

        // action for calculate button
        calculateButton.addActionListener(_ -> {
            try {
                ArrayList<MixedNumber> numbers = new ArrayList<>();

                // iterate fields to extract inputs and store to numbers array
                for (JTextField textField : fractionFields) {
                    if (!textField.getText().isBlank()) {
                        MixedNumber fraction = extractInput(textField);
                        numbers.add(fraction);
                    } else {
                        throw new InvalidMixedNumberException("One of the inputs is empty");
                    }
                }

                // stop if no input is recorded
                if (numbers.isEmpty()) return;

                // obtain operation
                String operation = (String) operatorDropdown.getSelectedItem();

                // calculate the items in numbers array
                MixedNumber result = numbers.getFirst();
                for (int i = 1; i < numbers.size(); i++) {
                    MixedNumber next = numbers.get(i);
                    result = calculate(result, next, operation);
                }

                // display result in the fraction and decimal text field
                fractionResultTF.setText(result.toString());
                decimalResultTF.setText(String.format("%.4f", result.toDouble()));
            } catch (InvalidMixedNumberException exception) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + exception.getMessage());
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this, "Error: " + exception.getMessage());
            }
        });
    }

    /**
     * Updates the operand input fields based on the selected count.
     * <p>
     *     This method preserves previously entered values, recreates
     *     the input rows, and applies styling and input filtering.
     * </p>
     * @param count The number of operand fields to display
     */
    private void updateFields(int count) {
        // store currently added inputs
        ArrayList<String> values = new ArrayList<>();
        for (JTextField textField : fractionFields) {
            values.add(textField.getText());
        }

        // clear all components from the panel
        operandPanel.removeAll();
        fractionFields.clear();

        // add text fields based on operand count
        for (int i = 0; i < count; i++) {
            // initialize components
            JPanel row = new JPanel(new BorderLayout(8, 0));
            JLabel number = new JLabel((i + 1) + ".");
            JTextField field = new RoundedTextField(16, styler.BACKGROUND, styler.PRIMARY);

            // style components
            styler.styleRow(row);
            styler.styleLabel(number, 3, false);
            number.setPreferredSize(new Dimension(24, 30));

            // add document filter to only allow certain characters for the text field
            ((AbstractDocument) field.getDocument()).setDocumentFilter(new FractionDocumentFilter());
            styler.styleTextField(field, true);

            // add the recorded value of the text field if applicable
            if (i < values.size()) {
                field.setText(values.get(i));
            }

            // add field to fractionFields array for computations
            fractionFields.add(field);

            // add components to row
            row.add(number, BorderLayout.WEST);
            row.add(field, BorderLayout.CENTER);

            // add row to the operand panel
            operandPanel.add(row);

            // add gap between rows
            operandPanel.add(Box.createVerticalStrut(12));
        }

        // update operand panel display
        operandPanel.revalidate();
        operandPanel.repaint();
    }

    /**
     * Performs the selected arithmetic operation on two mixed numbers.
     * @param firstFraction The first mixed number.
     * @param secondFraction The second mixed number.
     * @param operation The operation plus, minus, multiply, and divide in symbols (+, -, x, /).
     * @return The result of the operation in the form of a MixedNumber.
     */
    private MixedNumber calculate(MixedNumber firstFraction, MixedNumber secondFraction, String operation) {
        MixedNumber result = null;

        switch (operation) {
            case "+" -> result = firstFraction.add(secondFraction);
            case "-" -> result = firstFraction.subtract(secondFraction);
            case "x" -> result = firstFraction.multiplyBy(secondFraction);
            case "/" -> result = firstFraction.divideBy(secondFraction);
        }

        return result;
    }

    /**
     * Extracts a MixedNumber from a JTextField input.
     * @param textField The text field containing the input.
     * @return The corresponding MixedNumber.
     * @throws InvalidMixedNumberException If the input is invalid.
     */
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

    //------------------------------------------------------------------------------------------------------------------

    /**
     * Custom exception for invalid mixed number input.
     */
    static class InvalidMixedNumberException extends Exception {
        /**
         * Constructs the exception with a message.
         * @param message The error message.
         */
        public InvalidMixedNumberException(String message) {
            super(message);
        }
    }

    //------------------------------------------------------------------------------------------------------------------

    /**
     * A DocumentFilter that restricts JTextField input
     * to digits, spaces, and the slash character
     * <p>
     *     Used for fraction/mixed-number input validation.
     * </p>
     */
    static class FractionDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (string != null && string.matches("[0-9 /]*")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (text != null && text.matches("[0-9 /]*")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------

    /**
     * A utility class responsible for styling Swing components
     * used in the calculator UI.
     * <p>
     *     Provides consistent colors, fonts, sizes, and layout adjustments
     *     to ensure a cohesive design across the application.
     * </p>
     */
    class ComponentStyler {
        private final Color BACKGROUND = new Color(243, 243, 243);
        private final Color SURFACE = new Color(255, 255, 255);
        private final Color PRIMARY = new Color(6, 148, 148);
        private final Color TEXT_PRIMARY = new Color(14, 14, 14);

        private final Font HEADER_FONT = new Font("Arial", Font.BOLD, 24);
        private final Font BODY_FONT = new Font("Arial", Font.PLAIN, 16);

        /**
         * Styles a JButton with font, size, and alignment.
         * @param button The button to style.
         */
        private void styleButton(JButton button) {
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setFont(BODY_FONT);
        }

        /**
         * Styles a JLabel either as a header or as a standard label.
         * @param label The label to style.
         * @param isHeader Whether the label is a header or not.
         */
        private void styleLabel(JLabel label, int alignment, boolean isHeader) {
            switch (alignment) {
                case 1 -> label.setHorizontalAlignment(JLabel.LEFT);
                case 2 -> label.setHorizontalAlignment(JLabel.CENTER);
                case 3 -> label.setHorizontalAlignment(JLabel.RIGHT);
            }

            if (isHeader) {
                label.setFont(HEADER_FONT);
            } else {
                label.setFont(BODY_FONT);
            }
        }

        /**
         * Styles a JTextField with font, size, and alignment.
         * @param textField The text field to style.
         * @param editable Whether the field is editable or not.
         */
        private void styleTextField(JTextField textField, boolean editable) {
            textField.setFont(BODY_FONT);
            textField.setPreferredSize(new Dimension(0, 30));
            textField.setMinimumSize(new Dimension(0, 30));
            textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            textField.setHorizontalAlignment(JTextField.CENTER);
            textField.setEditable(editable);
            textField.setEnabled(editable);

            if (!editable) {
                textField.setDisabledTextColor(TEXT_PRIMARY);
                textField.setHorizontalAlignment(JTextField.LEFT);

                Border originalBorder = textField.getBorder();
                textField.setBorder(BorderFactory.createCompoundBorder(
                        originalBorder,
                        new EmptyBorder(0, 10, 0, 0)
                ));
            }
        }

        /**
         * Styles a JComboBox with font and centered text alignment.
         * @param dropdown The JComboBox to style.
         */
        private void styleDropdown(JComboBox dropdown) {
            dropdown.setFont(BODY_FONT);
            dropdown.setPreferredSize(new Dimension(200, 30));
            dropdown.setMinimumSize(new Dimension(200, 30));
            dropdown.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

            dropdown.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                              boolean isSelected, boolean cellHasFocus) {
                    JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    return label;
                }
            });
        }

        /**
         * Styles a JSpinner used for selecting the number of operands.
         * @param spinner The JSpinner to style
         */
        private void styleCounter(JSpinner spinner) {
            spinner.setPreferredSize(new Dimension(200, 30));
            spinner.setMinimumSize(new Dimension(200, 30));
            spinner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

            JComponent editor = spinner.getEditor();
            if (editor instanceof JSpinner.DefaultEditor defaultEditor) {
                JTextField tf = defaultEditor.getTextField();
                tf.setHorizontalAlignment(JTextField.CENTER);
                tf.setFont(BODY_FONT);
            }
        }

        /**
         * Styles a JScrollPane for the operand input panel.
         * @param scrollPane The JScrollPane to style.
         */
        private void styleScrollPane(JScrollPane scrollPane) {
            scrollPane.setOpaque(false);
            scrollPane.getViewport().setOpaque(false);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

            JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
            verticalBar.setPreferredSize(new Dimension(8, Integer.MAX_VALUE));
            verticalBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                    this.thumbColor = styler.PRIMARY;
                    this.trackColor = new Color(0, 0, 0, 0);
                }

                @Override
                protected JButton createDecreaseButton(int orientation) {
                    return createZeroButton();
                }

                @Override
                protected JButton createIncreaseButton(int orientation) {
                    return createZeroButton();
                }

                @Override
                protected Dimension getMinimumThumbSize() {
                    return new Dimension(5, 40);
                }

                @Override
                protected Dimension getMaximumThumbSize() {
                    return new Dimension(5, 40);
                }

                private JButton createZeroButton() {
                    JButton button = new JButton();
                    button.setPreferredSize(new Dimension(0, 0));
                    button.setMinimumSize(new Dimension(0, 0));
                    button.setMaximumSize(new Dimension(0, 0));
                    return button;
                }
            });
        }

        /**
         * Styles the main container panel of the calculator.
         * @param panel The main JPanel to style.
         */
        private void styleMainPanel(JPanel panel) {
            panel.setBorder(new EmptyBorder(24, 24, 24, 24));
            panel.setBackground(styler.BACKGROUND);
        }

        /**
         * Styles the left panel containing input controls and result fields.
         * @param panel The left JPanel to style.
         */
        private void styleLeftPanel(JPanel panel) {
            panel.setPreferredSize(new Dimension(250, Integer.MAX_VALUE));
            panel.setMaximumSize(new Dimension(250, Integer.MAX_VALUE));
            panel.setMinimumSize(new Dimension(250, Integer.MAX_VALUE));

            panel.setBackground(BACKGROUND);
        }

        /**
         * Styles the information panel that holds labels and result fields.
         * @param panel The JPanel containing input and result labels/fields.
         */
        private void styleInformationPanel(JPanel panel) {
            panel.setBackground(BACKGROUND);
        }

        /**
         * Applies layout and transparency styling to the operand panel.
         * @param panel the panel that holds dynamically generated input rows.
         */
        private void styleOperandPanel(JPanel panel) {
            panel.setLayout(new BoxLayout(operandPanel, BoxLayout.Y_AXIS));
            panel.setOpaque(false);
        }

        /**
         * Styles a row container used for each operand input.
         * @param panel the row panel containing label and text field.
         */
        private void styleRow(JPanel panel) {
            panel.setOpaque(false);
            panel.setPreferredSize(new Dimension(150, 30));
            panel.setMaximumSize(new Dimension(150, 30));
            panel.setMinimumSize(new Dimension(150, 30));
        }

        /**
         * Styles the right panel that contains dynamically generated operand fields.
         * @param panel The right JPanel to style.
         */
        private void styleRightPanel(JPanel panel) {
            panel.setPreferredSize(new Dimension(220, Integer.MAX_VALUE));
            panel.setMaximumSize(new Dimension(220, Integer.MAX_VALUE));
            panel.setMinimumSize(new Dimension(220, Integer.MAX_VALUE));

            panel.setLayout(new BorderLayout(0, 20));
            panel.setBorder(new EmptyBorder(20, 20, 0, 20));
            panel.setBackground(SURFACE);
            panel.setOpaque(false);
        }
    }

    /**
     * A custom JPanel with rounded corners.
     * <p>
     *     This panel overrides the default painting behavior to draw
     *     a rounded rectangle as its background using antialiasing
     *     for smoother edges.
     * </p>
     */
    static class RoundedPanel extends JPanel {
        private final int arc;

        /**
         * <p>
         *     Creates a rounded panel with the specified corner radius.
         * </p>
         * @param arc the arc width and height used to round the corners
         */
        public RoundedPanel(int arc) {
            this.arc = arc;
            setOpaque(false);
        }

        /**
         * <p>
         *     Paints the rounded background of the panel.
         * </p>
         * @param g the Graphics context used for painting.
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
        }
    }

    /**
     * A custom JButton with rounded corners and a solid background color.
     * <p>
     *     This button uses custom painting to give a rounded look and
     *     disables the default Swing button styling.
     * </p>
     */
    static class RoundedButton extends JButton {
        private final int arc;
        private final Color backgroundColor;

        /**
         * Creates a rounded button with custom text, corner radius, and color.
         * @param text the text displayed on the button.
         * @param arc the arc width and height for rounded corners.
         * @param backgroundColor the background color of the button.
         */
        public RoundedButton(String text, int arc, Color backgroundColor) {
            super(text);
            this.arc = arc;
            this.backgroundColor = backgroundColor;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setForeground(Color.WHITE);
            setFont(new Font("Arial", Font.BOLD, 16));
            setBorder(new EmptyBorder(5, 15, 5, 15));
        }

        /**
         * Paints the rounded background and button content.
         * @param g the Graphics context used for painting.
         */
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(backgroundColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

            super.paintComponent(g2);
            g2.dispose();
        }
    }

    /**
     * A custom JTextField with rounded corners and customizable background
     * and border colors.
     * <p>
     *     This component overrides painting to render a rounded rectangle
     *     as the background and border instead of the default rectangular style.
     * </p>
     */
    static class RoundedTextField extends JTextField {
        private final int arc;
        private Color backgroundColor = new Color(243, 243, 243);
        private Color borderColor = new Color(128, 128, 128);

        /**
         * Creates a rounded text field with custom colors and corner radius.
         * @param arc the arc width and height for rounded corners.
         * @param background the background color of the text field.
         * @param border the border color of the text field.
         */
        public RoundedTextField(int arc, Color background, Color border) {
            this.arc = arc;
            this.backgroundColor = background;
            this.borderColor = border;
            setOpaque(false);
            setBorder(new EmptyBorder(5, 10, 5, 10));
        }

        /**
         * Paints the rounded background and text content.
         * @param g the Graphics context used for painting.
         */
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(backgroundColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

            super.paintComponent(g2);
            g2.dispose();
        }

        /**
         * Paints the rounded border of the text field.
         * @param g  the Graphics context used for painting.
         *
         */
        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(borderColor);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);

            g2.dispose();
        }
    }
}
