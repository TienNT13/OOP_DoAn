package do_an_1;

import javax.swing.*;
import java.awt.*;

// Lớp WelcomePanel hiển thị giao diện chào mừng của ứng dụng
public class WelcomePanel extends JPanel {
    private JButton startButton; // Nút "Bắt đầu" để chuyển sang giao diện chính

    // Constructor nhận một Runnable để xử lý sự kiện khi nhấn nút "Bắt đầu"
    public WelcomePanel(Runnable onStart) {
        // Thiết lập giao diện cơ bản cho panel
        setLayout(new BorderLayout()); // Sử dụng BorderLayout để căn chỉnh các thành phần
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Thêm viền trống xung quanh
        setBackground(Color.decode("#F8EAD9")); // Đặt màu nền cho panel

        // Tạo panel chính giữa để chứa logo, tiêu đề và nút
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS)); // Sử dụng BoxLayout theo chiều dọc
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa theo chiều ngang
        centerPanel.setOpaque(false); // Đặt nền trong suốt để hiển thị màu nền của WelcomePanel

        // Thêm logo vào panel
        JLabel logoLabel = new JLabel();
        try {
            ImageIcon logoIcon = new ImageIcon("D:/PTITer-102/ki_2_nam_4/OOP/doan/hinhminhhoa.png"); // Tải hình ảnh logo
            Image scaledImage = logoIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH); // Thu nhỏ ảnh
            logoLabel.setIcon(new ImageIcon(scaledImage)); // Gán ảnh vào JLabel
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa logo
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải logo: " + e.getMessage()); // Hiển thị thông báo lỗi nếu tải thất bại
        }
        centerPanel.add(logoLabel); // Thêm logo vào panel

        // Thêm khoảng cách dọc
        centerPanel.add(Box.createVerticalStrut(20));

        // Thêm dòng chữ "Nhóm 1"
        JLabel titleLabel1 = new JLabel("Nhóm 1");
        titleLabel1.setFont(new Font("Arial", Font.BOLD, 24)); // Đặt phông chữ đậm, kích thước 24
        titleLabel1.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa dòng chữ
        centerPanel.add(titleLabel1); // Thêm dòng chữ vào panel

        // Thêm khoảng cách dọc
        centerPanel.add(Box.createVerticalStrut(30));

        // Thêm dòng chữ tiêu đề "Hệ thống quản lý cửa hàng bán điện thoại"
        JLabel titleLabel = new JLabel("Hệ thống quản lý cửa hàng bán điện thoại");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Đặt phông chữ đậm, kích thước 24
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa dòng chữ
        centerPanel.add(titleLabel); // Thêm tiêu đề vào panel

        // Thêm khoảng cách dọc
        centerPanel.add(Box.createVerticalStrut(30));

        // Thêm nút "Bắt đầu"
        startButton = new JButton("Bắt đầu");
        startButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Đặt phông chữ, kích thước 18
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa nút
        startButton.addActionListener(e -> onStart.run()); // Gọi Runnable khi nhấn nút
        centerPanel.add(startButton); // Thêm nút vào panel

        // Thêm panel chính vào trung tâm của WelcomePanel
        add(centerPanel, BorderLayout.CENTER);
    }
}