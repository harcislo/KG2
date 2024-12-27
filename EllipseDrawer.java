import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class EllipseDrawer {

    static Ellipse ellipse;
    static JTextField xField, yField, widthField, heightField;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Эллипс");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                if (ellipse != null) {
                    ellipse.drawBoundary(g2d);
                }
            }
        };
        frame.add(drawingPanel);
        JPanel inputPanel = new JPanel();
        // Создание текстовых полей для ввода координат и размеров
        xField = new JTextField(5);
        yField = new JTextField(5);
        widthField = new JTextField(5);
        heightField = new JTextField(5);
        inputPanel.add(new JLabel("X:"));
        inputPanel.add(xField);
        inputPanel.add(new JLabel("Y:"));
        inputPanel.add(yField);
        inputPanel.add(new JLabel("Ширина:"));
        inputPanel.add(widthField);
        inputPanel.add(new JLabel("Высота:"));
        inputPanel.add(heightField);
        JButton createButton = new JButton("Создать эллипс");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createEllipse(drawingPanel);
            }
        });
        inputPanel.add(createButton);
        frame.add(inputPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private static void createEllipse(JPanel drawingPanel) {
        try {
            int x = Integer.parseInt(xField.getText());
            int y = Integer.parseInt(yField.getText());
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            ellipse = new Ellipse(x, y, width, height);
            drawingPanel.repaint();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Пожалуйста, введите корректные числовые значения.", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
        }
    }

    static class Ellipse implements Serializable {
        private static final long serialVersionUID = 1L;
        private final int x;
        private int y;
        private int width;
        private int height;

        public Ellipse(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public void drawBoundary(Graphics2D g2d) {
            int centerX = x + width / 2;
            int centerY = y + height / 2;
            double threshold = 0.0555;
            for (int j = y - 10; j < y + height + 10; j++) {
                for (int i = x - 10; i < x + width + 10; i++) {
                    double a = (double) width / 2 ;
                    double b = (double) height / 2;
                    double equation = (Math.pow(i - centerX, 2) / (a * a)) + (Math.pow(j - centerY, 2) / (b * b));
                    if (Math.abs(equation - 1) < threshold) {
                        g2d.fillRect(i, j, 1, 1);
                    }
                }
            }
        }
    }
}