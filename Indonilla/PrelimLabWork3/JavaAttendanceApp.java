import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Scanner;

public class JavaAttendanceApp {
    public static void main(String[] args) {
        // 1. Scanner for initial console input
        Scanner sc = new Scanner(System.in);
        System.out.println("Initialize System? (yes/no): ");
        if (!sc.next().equalsIgnoreCase("yes")) return;

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("BMI Attendance Logger");
            frame.setSize(600, 700);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout(10, 10));

            // Input Panel
            JPanel inputPanel = new JPanel(new GridLayout(3, 2));
            JTextField nameField = new JTextField();
            JTextField weightField = new JTextField(); // kg
            JTextField heightField = new JTextField(); // meters
            inputPanel.add(new JLabel(" Name:")); inputPanel.add(nameField);
            inputPanel.add(new JLabel(" Weight (kg):")); inputPanel.add(weightField);
            inputPanel.add(new JLabel(" Height (m):")); inputPanel.add(heightField);

            // Signature Area
            SignaturePanel sigPanel = new SignaturePanel();
            
            // Table with Image Support
            String[] cols = {"Name", "BMI", "Category", "Signature"};
            DefaultTableModel model = new DefaultTableModel(cols, 0) {
                @Override public Class<?> getColumnClass(int c) { return c == 3 ? ImageIcon.class : Object.class; }
            };
            JTable table = new JTable(model);
            table.setRowHeight(50);

            JButton btn = new JButton("Calculate & Log");
            btn.addActionListener(e -> {
                try {
                    double w = Double.parseDouble(weightField.getText());
                    double h = Double.parseDouble(heightField.getText());
                    // Formula
                    double bmi = w / (h * h);
                    // Conditionals
                    String cat = (bmi < 18.5) ? "Underweight" : (bmi < 25) ? "Normal" : "Overweight";
                    
                    // Capture Signature
                    BufferedImage img = new BufferedImage(sigPanel.getWidth(), sigPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
                    sigPanel.paint(img.getGraphics());
                    ImageIcon icon = new ImageIcon(img.getScaledInstance(80, 30, Image.SCALE_SMOOTH));

                    model.insertRow(0, new Object[]{nameField.getText(), String.format("%.2f", bmi), cat, icon});
                } catch (Exception ex) { JOptionPane.showMessageDialog(frame, "Enter valid numbers!"); }
            });

            frame.add(inputPanel, BorderLayout.NORTH);
            frame.add(sigPanel, BorderLayout.WEST);
            frame.add(new JScrollPane(table), BorderLayout.CENTER);
            frame.add(btn, BorderLayout.SOUTH);
            frame.setVisible(true);
        });
    }
}

class SignaturePanel extends JPanel {
    private ArrayList<Point> points = new ArrayList<>();
    public SignaturePanel() { 
        setPreferredSize(new Dimension(200, 150)); background(Color.WHITE); border(BorderFactory.createLineBorder(Color.BLACK));
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) { points.add(e.getPoint()); repaint(); }
        });
    }
    private void background(Color c) { setBackground(c); }
    private void border(javax.swing.border.Border b) { setBorder(b); }
    public void paintComponent(Graphics g) { super.paintComponent(g); for(Point p : points) g.fillOval(p.x, p.y, 2, 2); }
}