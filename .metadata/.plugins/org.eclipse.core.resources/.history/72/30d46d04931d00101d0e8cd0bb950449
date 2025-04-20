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
    private List<HoaDon> dsHoaDon;
    private JLabel lblTongDoanhThu, lblTopSanPham;
    private JTable table;
    private DataManager<HoaDon> dataManager;

    private String formatPrice(double price) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(price) + " VND";
    }

    public PanelThongKe() {
        setBackground(Color.decode("#F8EAD9"));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        dataManager = new FileDataManager<>();
        dsHoaDon = new ArrayList<>();
        try {
            dsHoaDon = dataManager.loadFromFile("hoadon.dat");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi đọc file hóa đơn: " + e.getMessage());
        }

        double tongDoanhThu = dsHoaDon.stream().mapToDouble(HoaDon::getTongTien).sum();
        Map<String, Integer> productSales = new HashMap<>();
        for (HoaDon hd : dsHoaDon) {
            productSales.merge(hd.getTenSP(), hd.getSoLuong(), Integer::sum);
        }
        String topSanPham = productSales.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Chưa có dữ liệu");

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        lblTongDoanhThu = new JLabel("Tổng doanh thu: " + formatPrice(tongDoanhThu), SwingConstants.CENTER);
        lblTongDoanhThu.setFont(new Font("SansSerif", Font.BOLD, 16));
        topPanel.add(lblTongDoanhThu);

        lblTopSanPham = new JLabel("Sản phẩm bán chạy: " + topSanPham, SwingConstants.CENTER);
        lblTopSanPham.setFont(new Font("SansSerif", Font.BOLD, 16));
        topPanel.add(lblTopSanPham);
        add(topPanel, BorderLayout.NORTH);

        String[] columns = {"Mã HD", "Mã SP", "Số lượng", "Tổng tiền", "Ngày lập"};
        Object[][] data = new Object[dsHoaDon.size()][5];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (int i = 0; i < dsHoaDon.size(); i++) {
            HoaDon hd = dsHoaDon.get(i);
            data[i] = new Object[]{hd.getMaHD(), hd.getTenSP(), hd.getSoLuong(), formatPrice(hd.getTongTien()), hd.getNgayLap().format(formatter)};
        }
        table = new JTable(data, columns);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnRefresh = new JButton("Làm mới");
        btnRefresh.addActionListener(e -> refresh());
        add(btnRefresh, BorderLayout.SOUTH);
    }

    private void refresh() {
        try {
            dsHoaDon = dataManager.loadFromFile("hoadon.dat");
            double tongDoanhThu = dsHoaDon.stream().mapToDouble(HoaDon::getTongTien).sum();
            lblTongDoanhThu.setText("Tổng doanh thu: " + formatPrice(tongDoanhThu));

            Map<String, Integer> productSales = new HashMap<>();
            for (HoaDon hd : dsHoaDon) {
                productSales.merge(hd.getTenSP(), hd.getSoLuong(), Integer::sum);
            }
            String topSanPham = productSales.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("Chưa có dữ liệu");
            lblTopSanPham.setText("Sản phẩm bán chạy: " + topSanPham);

            String[] columns = {"Mã HD", "Mã SP", "Số lượng", "Tổng tiền", "Ngày lập"};
            Object[][] data = new Object[dsHoaDon.size()][5];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (int i = 0; i < dsHoaDon.size(); i++) {
                HoaDon hd = dsHoaDon.get(i);
                data[i] = new Object[]{hd.getMaHD(), hd.getTenSP(), hd.getSoLuong(), formatPrice(hd.getTongTien()), hd.getNgayLap().format(formatter)};
            }
            table.setModel(new javax.swing.table.DefaultTableModel(data, columns));
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi đọc file hóa đơn: " + e.getMessage());
        }
    }
}