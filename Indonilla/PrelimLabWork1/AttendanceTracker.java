// Add these to your imports
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

// ... inside your main method, update the table logic:
String[] cols = {"Name", "Course", "Timestamp", "Signature"};
DefaultTableModel model = new DefaultTableModel(cols, 0) {
    @Override
    public Class<?> getColumnClass(int column) {
        return (column == 3) ? ImageIcon.class : Object.class;
    }
};

JTable table = new JTable(model);
table.setRowHeight(60); // Make rows tall enough for signatures

// Update the enterBtn logic:
enterBtn.addActionListener(e -> {
    if (nameField.getText().trim().isEmpty()) return;

    // Capture the signature as an Image
    BufferedImage img = new BufferedImage(sigArea.getWidth(), sigArea.getHeight(), BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = img.createGraphics();
    sigArea.paint(g2);
    g2.dispose();

    // Scale it down for the table
    ImageIcon icon = new ImageIcon(img.getScaledInstance(100, 40, Image.SCALE_SMOOTH));

    model.insertRow(0, new Object[]{
        nameField.getText(), 
        courseField.getText(), 
        timeInField.getText(), 
        icon
    });
    
    sigArea.clear();
});