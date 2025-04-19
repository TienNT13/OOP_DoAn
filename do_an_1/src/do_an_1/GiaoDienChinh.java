package do_an_1;

import javax.swing.*;
import java.awt.*;

public class GiaoDienChinh extends JFrame {
    private JPanel panelSanPham, panelKhachHang, panelHoaDon, panelThongKe, panelTimKiem, welcomePanel;
    private JPanel contentPanel;
    private JPanel buttonPanel;
    private CardLayout cardLayout;

    public GiaoDienChinh() {
        // Thiết lập tiêu đề và kích thước cửa sổ
        setTitle("Quản Lý Cửa Hàng Điện Thoại");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Khởi tạo CardLayout để chuyển đổi giữa các panel
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Color.decode("#F8EAD9"));

        // Khởi tạo các panel giao diện
        panelSanPham = new PanelSanPham();
        panelKhachHang = new PanelKhachHang();
        panelHoaDon = new PanelHoaDon();
        panelThongKe = new PanelThongKe();
        panelTimKiem = new PanelTimKiem();
        welcomePanel = new WelcomePanel(() -> {
            cardLayout.show(contentPanel, "SanPham");
            buttonPanel.setVisible(true);
        });

        // Thêm các panel vào contentPanel với tên định danh
        contentPanel.add(welcomePanel, "Welcome");
        contentPanel.add(panelSanPham, "SanPham");
        contentPanel.add(panelKhachHang, "KhachHang");
        contentPanel.add(panelHoaDon, "HoaDon");
        contentPanel.add(panelThongKe, "ThongKe");
        contentPanel.add(panelTimKiem, "TimKiem");

        // Tạo panel chứa các nút điều hướng
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        JButton btnSanPham = new JButton("Sản Phẩm");
        JButton btnKhachHang = new JButton("Khách Hàng");
        JButton btnHoaDon = new JButton("Hóa Đơn");
        JButton btnThongKe = new JButton("Thống Kê");
        JButton btnTimKiem = new JButton("Tìm Kiếm");

        // Thiết lập font cho các nút
        Font buttonFont = new Font("Arial", Font.PLAIN, 14);
        btnSanPham.setFont(buttonFont);
        btnKhachHang.setFont(buttonFont);
        btnHoaDon.setFont(buttonFont);
        btnThongKe.setFont(buttonFont);
        btnTimKiem.setFont(buttonFont);

        // Thêm tooltip cho các nút
        btnSanPham.setToolTipText("Quản lý sản phẩm");
        btnKhachHang.setToolTipText("Quản lý khách hàng");
        btnHoaDon.setToolTipText("Quản lý hóa đơn");
        btnThongKe.setToolTipText("Xem thống kê doanh thu");
        btnTimKiem.setToolTipText("Tìm kiếm dữ liệu");

        // Gắn sự kiện chuyển panel cho các nút
        btnSanPham.addActionListener(e -> cardLayout.show(contentPanel, "SanPham"));
        btnKhachHang.addActionListener(e -> cardLayout.show(contentPanel, "KhachHang"));
        btnHoaDon.addActionListener(e -> cardLayout.show(contentPanel, "HoaDon"));
        btnThongKe.addActionListener(e -> cardLayout.show(contentPanel, "ThongKe"));
        btnTimKiem.addActionListener(e -> cardLayout.show(contentPanel, "TimKiem"));

        buttonPanel.add(btnSanPham);
        buttonPanel.add(btnKhachHang);
        buttonPanel.add(btnHoaDon);
        buttonPanel.add(btnThongKe);
        buttonPanel.add(btnTimKiem);

        // Ẩn buttonPanel khi khởi động
        buttonPanel.setVisible(false);
        add(buttonPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        // Hiển thị WelcomePanel khi khởi động
        showWelcomePanel();
    }

    private void showWelcomePanel() {
        // Chuyển sang panel chào mừng
        cardLayout.show(contentPanel, "Welcome");
        buttonPanel.setVisible(false);
    }

    public static void main(String[] args) {
        // Khởi chạy ứng dụng trên EDT
        SwingUtilities.invokeLater(() -> {
            GiaoDienChinh frame = new GiaoDienChinh();
            frame.setVisible(true);
        });
    }
}