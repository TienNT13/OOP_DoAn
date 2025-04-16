package do_an_1;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PanelHoaDon extends JPanel {
    private List<HoaDon> dsHoaDon;
    private JTable table;
    private JTextField txtMaHD, txtMaKH, txtMaSP, txtSoLuong, txtTongTien;
    private DataManager<HoaDon> dataManager;
    private DataManager<SanPham> spDataManager;

    private String formatPrice(double price) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(price) + " VND";
    }

    // Tạo mã hóa đơn tự động
    private String generateMaHD() {
        int count = dsHoaDon.size() + 1;
        return "HD" + String.format("%03d", count);
    }

    public PanelHoaDon() {
    	
    	setBackground(Color.decode("#F8EAD9")); // Set background color
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Quản lý hóa đơn (Placeholder)", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
        
        dataManager = new FileDataManager<>();
        spDataManager = new FileDataManager<>();
        dsHoaDon = new ArrayList<>();
        try {
            dsHoaDon = dataManager.loadFromFile("hoadon.dat");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi đọc file hóa đơn: " + e.getMessage());
        }

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Bảng hiển thị hóa đơn
        String[] columns = {"Mã HD", "Mã KH", "Mã SP", "Số lượng", "Tổng tiền", "Ngày lập"};
        Object[][] data = new Object[dsHoaDon.size()][6];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (int i = 0; i < dsHoaDon.size(); i++) {
            HoaDon hd = dsHoaDon.get(i);
            data[i] = new Object[]{hd.getMaHD(), hd.getMaKH(), hd.getTenSP(), hd.getSoLuong(), formatPrice(hd.getTongTien()), hd.getNgayLap().format(formatter)};
        }
        table = new JTable(data, columns);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Form nhập liệu
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        formPanel.add(new JLabel("Mã HD:"));
        txtMaHD = new JTextField(generateMaHD());
        txtMaHD.setEditable(false); // Không cho phép chỉnh sửa mã hóa đơn
        formPanel.add(txtMaHD);
        formPanel.add(new JLabel("Mã KH:"));
        txtMaKH = new JTextField();
        formPanel.add(txtMaKH);
        formPanel.add(new JLabel("Mã SP:"));
        txtMaSP = new JTextField();
        formPanel.add(txtMaSP);
        formPanel.add(new JLabel("Số lượng:"));
        txtSoLuong = new JTextField();
        formPanel.add(txtSoLuong);
        formPanel.add(new JLabel("Tổng tiền:"));
        txtTongTien = new JTextField();
        txtTongTien.setEditable(false);
        formPanel.add(txtTongTien);

        JButton btnThem = new JButton("Thêm");
        btnThem.addActionListener(e -> themHoaDon());
        formPanel.add(btnThem);

        add(formPanel, BorderLayout.NORTH);
    }

    private void themHoaDon() {
        try {
            String maHD = txtMaHD.getText();
            String maKH = txtMaKH.getText();
            String maSP = txtMaSP.getText();
            int soLuong = Integer.parseInt(txtSoLuong.getText());

            if (maHD.isEmpty() || maKH.isEmpty() || maSP.isEmpty()) {
                throw new IllegalArgumentException("Vui lòng điền đầy đủ thông tin!");
            }

            // Load danh sách sản phẩm
            List<SanPham> dsSanPham = spDataManager.loadFromFile("sanpham.dat");
            SanPham sp = dsSanPham.stream()
                    .filter(p -> p.getMaSP().equals(maSP))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Mã sản phẩm không tồn tại!"));

            // Kiểm tra số lượng tồn kho
            if (sp.getSoLuong() < soLuong) {
                throw new IllegalArgumentException("Số lượng vượt quá tồn kho! Còn lại: " + sp.getSoLuong());
            }

            // Tính tổng tiền
            double tongTien = sp.getGia() * soLuong;
            txtTongTien.setText(formatPrice(tongTien));

            // Lấy ngày hiện tại
            LocalDate ngayLap = LocalDate.now();

            HoaDon hd = new HoaDon(maHD, maKH, maSP, soLuong, tongTien, ngayLap);
            dsHoaDon.add(hd);

            // Cập nhật số lượng sản phẩm
            sp.setSoLuong(sp.getSoLuong() - soLuong);
            spDataManager.saveToFile(dsSanPham, "sanpham.dat");

            dataManager.saveToFile(dsHoaDon, "hoadon.dat");
            refreshTable();
            txtMaHD.setText(generateMaHD()); // Tạo mã hóa đơn mới cho lần nhập tiếp theo
            txtMaKH.setText("");
            txtMaSP.setText("");
            txtSoLuong.setText("");
            txtTongTien.setText("");
            JOptionPane.showMessageDialog(this, "Thêm hóa đơn thành công!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số!");
        } catch (IllegalArgumentException | IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void refreshTable() {
        String[] columns = {"Mã HD", "Mã KH", "Mã SP", "Số lượng", "Tổng tiền", "Ngày lập"};
        Object[][] data = new Object[dsHoaDon.size()][6];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (int i = 0; i < dsHoaDon.size(); i++) {
            HoaDon hd = dsHoaDon.get(i);
            data[i] = new Object[]{hd.getMaHD(), hd.getMaKH(), hd.getTenSP(), hd.getSoLuong(), formatPrice(hd.getTongTien()), hd.getNgayLap().format(formatter)};
        }
        table.setModel(new javax.swing.table.DefaultTableModel(data, columns));
    }
}