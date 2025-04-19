package do_an_1;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanelThongKe extends JPanel {
    private List<HoaDon> dsHoaDon; // Danh sách hóa đơn để thống kê
    private JLabel lblTongDoanhThu, lblTopSanPham; // Nhãn hiển thị tổng doanh thu và sản phẩm bán chạy
    private JTable table; // Bảng hiển thị chi tiết hóa đơn
    private DataManager<HoaDon> dataManager; // Quản lý lưu trữ dữ liệu hóa đơn

    // Định dạng giá tiền với dấu phẩy và đơn vị VND
    private String formatPrice(double price) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(price) + " VND";
    }

    public PanelThongKe() {
        // Thiết lập giao diện cơ bản cho panel
        setBackground(Color.decode("#F8EAD9")); // Đặt màu nền
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Thêm viền trống

        // Khởi tạo DataManager và danh sách hóa đơn
        dataManager = new FileDataManager<>();
        dsHoaDon = new ArrayList<>();
        try {
            dsHoaDon = dataManager.loadFromFile("hoadon.dat"); // Tải dữ liệu hóa đơn từ file
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi đọc file hóa đơn: " + e.getMessage());
        }

        // Tính tổng doanh thu từ danh sách hóa đơn
        double tongDoanhThu = 0;
        for (HoaDon hd : dsHoaDon) {
            tongDoanhThu += hd.getTongTien();
        }

        // Tính sản phẩm bán chạy nhất dựa trên số lượng bán
        Map<String, Integer> productSales = new HashMap<>();
        for (HoaDon hd : dsHoaDon) {
            productSales.merge(hd.getTenSP(), hd.getSoLuong(), Integer::sum); // Tổng hợp số lượng bán của từng sản phẩm
        }
        String topSanPham = productSales.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Chưa có dữ liệu"); // Lấy mã sản phẩm có số lượng bán cao nhất

        // Tạo panel hiển thị tổng doanh thu và sản phẩm bán chạy
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        lblTongDoanhThu = new JLabel("Tổng doanh thu: " + formatPrice(tongDoanhThu), SwingConstants.CENTER);
        lblTongDoanhThu.setFont(new Font("SansSerif", Font.BOLD, 16)); // Định dạng chữ đậm
        topPanel.add(lblTongDoanhThu);

        lblTopSanPham = new JLabel("Sản phẩm bán chạy: " + topSanPham, SwingConstants.CENTER);
        lblTopSanPham.setFont(new Font("SansSerif", Font.BOLD, 16));
        topPanel.add(lblTopSanPham);
        add(topPanel, BorderLayout.NORTH); // Thêm panel vào phía bắc của giao diện

        // Tạo bảng hiển thị chi tiết hóa đơn
        String[] columns = {"Mã HD", "Mã SP", "Số lượng", "Tổng tiền", "Ngày lập"};
        Object[][] data = new Object[dsHoaDon.size()][5];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (int i = 0; i < dsHoaDon.size(); i++) {
            HoaDon hd = dsHoaDon.get(i);
            data[i] = new Object[]{hd.getMaHD(), hd.getTenSP(), hd.getSoLuong(), formatPrice(hd.getTongTien()), hd.getNgayLap().format(formatter)}; // Điền dữ liệu hóa đơn vào bảng
        }
        table = new JTable(data, columns);
        add(new JScrollPane(table), BorderLayout.CENTER); // Thêm bảng vào trung tâm giao diện

        // Tạo nút làm mới để cập nhật dữ liệu
        JButton btnRefresh = new JButton("Làm mới");
        btnRefresh.addActionListener(e -> refresh()); // Gọi phương thức refresh khi nhấn
        add(btnRefresh, BorderLayout.SOUTH); // Thêm nút vào phía nam giao diện
    }

    // Làm mới dữ liệu thống kê và giao diện
    private void refresh() {
        try {
            dsHoaDon = dataManager.loadFromFile("hoadon.dat"); // Tải lại danh sách hóa đơn từ file

            // Cập nhật tổng doanh thu
            double tongDoanhThu = 0;
            for (HoaDon hd : dsHoaDon) {
                tongDoanhThu += hd.getTongTien();
            }
            lblTongDoanhThu.setText("Tổng doanh thu: " + formatPrice(tongDoanhThu)); // Cập nhật nhãn tổng doanh thu

            // Cập nhật sản phẩm bán chạy
            Map<String, Integer> productSales = new HashMap<>();
            for (HoaDon hd : dsHoaDon) {
                productSales.merge(hd.getTenSP(), hd.getSoLuong(), Integer::sum); // Tổng hợp số lượng bán
            }
            String topSanPham = productSales.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("Chưa có dữ liệu"); // Lấy sản phẩm có số lượng bán cao nhất
            lblTopSanPham.setText("Sản phẩm bán chạy: " + topSanPham); // Cập nhật nhãn sản phẩm bán chạy

            // Cập nhật bảng chi tiết hóa đơn
            String[] columns = {"Mã HD", "Mã SP", "Số lượng", "Tổng tiền", "Ngày lập"};
            Object[][] data = new Object[dsHoaDon.size()][5];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (int i = 0; i < dsHoaDon.size(); i++) {
                HoaDon hd = dsHoaDon.get(i);
                data[i] = new Object[]{hd.getMaHD(), hd.getTenSP(), hd.getSoLuong(), formatPrice(hd.getTongTien()), hd.getNgayLap().format(formatter)}; // Điền dữ liệu vào bảng
            }
            table.setModel(new javax.swing.table.DefaultTableModel(data, columns)); // Cập nhật mô hình bảng
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi đọc file hóa đơn: " + e.getMessage());
        }
    }
}