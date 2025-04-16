package do_an_1;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PanelKhachHang extends JPanel {
    private List<KhachHang> dsKhachHang;
    private JTable table;
    private JTextField txtMaKH, txtTenKH, txtSdt;
    private DataManager<KhachHang> dataManager;
    private DataManager<HoaDon> hoaDonDataManager;
    private boolean sapXepTangDan = true;

    // Tạo mã khách hàng tự động theo mẫu NTx
    private String generateMaKH() {
        int count = dsKhachHang.size() + 1;
        return "NT" + count;
    }

    public PanelKhachHang() {
        setBackground(Color.decode("#F8EAD9"));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        dataManager = new FileDataManager<>();
        hoaDonDataManager = new FileDataManager<>();
        dsKhachHang = new ArrayList<>();
        try {
            dsKhachHang = dataManager.loadFromFile("khachhang.dat");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi đọc file khách hàng: " + e.getMessage());
        }

        // Bảng hiển thị khách hàng
        String[] columns = {"Mã KH", "Tên KH", "SĐT"};
        Object[][] data = new Object[dsKhachHang.size()][3];
        for (int i = 0; i < dsKhachHang.size(); i++) {
            KhachHang kh = dsKhachHang.get(i);
            data[i] = new Object[]{kh.getMaKH(), kh.getTenKH(), kh.getSdt()};
        }
        table = new JTable(data, columns);
        table.setFillsViewportHeight(true);
        JScrollPane tableScrollPane = new JScrollPane(table);
        add(tableScrollPane, BorderLayout.CENTER);

        // Form nhập liệu và nút điều khiển
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBackground(Color.decode("#F8EAD9"));

        formPanel.add(new JLabel("Mã KH:"));
        txtMaKH = new JTextField(generateMaKH());
        txtMaKH.setEditable(false);
        formPanel.add(txtMaKH);

        formPanel.add(new JLabel("Tên KH:"));
        txtTenKH = new JTextField();
        formPanel.add(txtTenKH);

        formPanel.add(new JLabel("SĐT:"));
        txtSdt = new JTextField();
        formPanel.add(txtSdt);

        // Panel chứa các nút
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#F8EAD9"));

        JButton btnThem = new JButton("Thêm");
        btnThem.addActionListener(e -> themKhachHang());
        buttonPanel.add(btnThem);

        JButton btnSua = new JButton("Sửa");
        btnSua.addActionListener(e -> suaKhachHang());
        buttonPanel.add(btnSua);

        JButton btnXoa = new JButton("Xóa");
        btnXoa.addActionListener(e -> xoaKhachHang());
        buttonPanel.add(btnXoa);

        JButton btnSapXep = new JButton("Sắp xếp theo mã KH");
        btnSapXep.addActionListener(e -> sapXepKhachHang());
        buttonPanel.add(btnSapXep);

        formPanel.add(new JLabel(""));
        formPanel.add(buttonPanel);

        add(formPanel, BorderLayout.NORTH);

        // Sự kiện chọn hàng trong bảng
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1 && !e.getValueIsAdjusting()) {
                KhachHang kh = dsKhachHang.get(row);
                txtMaKH.setText(kh.getMaKH());
                txtTenKH.setText(kh.getTenKH());
                txtSdt.setText(kh.getSdt());
            }
        });

        // Sự kiện nhấn chuột để hiển thị chi tiết
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row != -1) {
                    hienThiChiTietKhachHang(row);
                }
            }
        });
    }

    private void hienThiChiTietKhachHang(int row) {
        KhachHang kh = dsKhachHang.get(row);

        // Tạo cửa sổ chi tiết
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chi tiết khách hàng", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(Color.decode("#F8EAD9"));

        // Panel thông tin cơ bản
        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        infoPanel.setBackground(Color.decode("#F8EAD9"));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        infoPanel.add(new JLabel("Mã KH:"));
        infoPanel.add(new JLabel(kh.getMaKH()));

        infoPanel.add(new JLabel("Tên KH:"));
        infoPanel.add(new JLabel(kh.getTenKH()));

        infoPanel.add(new JLabel("SĐT:"));
        infoPanel.add(new JLabel(kh.getSdt()));

        // Panel lịch sử mua hàng
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBackground(Color.decode("#F8EAD9"));
        historyPanel.setBorder(BorderFactory.createTitledBorder("Lịch sử mua hàng"));

        List<HoaDon> lichSuMuaHang;
        try {
            lichSuMuaHang = hoaDonDataManager.loadFromFile("hoadon.dat").stream()
                    .filter(hd -> hd.getMaKH().equals(kh.getMaKH()))
                    .collect(Collectors.toList());
        } catch (IOException | ClassNotFoundException e) {
            lichSuMuaHang = new ArrayList<>();
            JOptionPane.showMessageDialog(this, "Lỗi khi đọc file hóa đơn: " + e.getMessage());
        }

        String[] columns = {"Mã HD", "Mã SP", "Số lượng", "Tổng tiền", "Ngày lập"};
        Object[][] data = new Object[lichSuMuaHang.size()][5];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormat priceFormatter = new DecimalFormat("#,### VND");
        for (int i = 0; i < lichSuMuaHang.size(); i++) {
            HoaDon hd = lichSuMuaHang.get(i);
            data[i] = new Object[]{hd.getMaHD(), hd.getTenSP(), hd.getSoLuong(), priceFormatter.format(hd.getTongTien()), hd.getNgayLap().format(formatter)};
        }

        JTable historyTable = new JTable(data, columns);
        historyTable.setFillsViewportHeight(true);
        JScrollPane historyScrollPane = new JScrollPane(historyTable);
        historyPanel.add(historyScrollPane, BorderLayout.CENTER);

        // Thêm các panel vào dialog
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.decode("#F8EAD9"));
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(historyPanel, BorderLayout.CENTER);

        dialog.add(mainPanel, BorderLayout.CENTER);

        // Nút đóng
        JButton btnDong = new JButton("Đóng");
        btnDong.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#F8EAD9"));
        buttonPanel.add(btnDong);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void themKhachHang() {
        try {
            String maKH = txtMaKH.getText().trim();
            String tenKH = txtTenKH.getText().trim();
            String sdt = txtSdt.getText().trim();

            if (tenKH.isEmpty() || sdt.isEmpty()) {
                throw new IllegalArgumentException("Vui lòng điền đầy đủ thông tin!");
            }

            KhachHang kh = new KhachHang(maKH, tenKH, sdt);
            dsKhachHang.add(kh);
            dataManager.saveToFile(dsKhachHang, "khachhang.dat");
            refreshTable();
            clearForm();
            txtMaKH.setText(generateMaKH());
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
        } catch (IllegalArgumentException | IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void suaKhachHang() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để sửa!");
            return;
        }

        try {
            String tenKH = txtTenKH.getText().trim();
            String sdt = txtSdt.getText().trim();

            if (tenKH.isEmpty() || sdt.isEmpty()) {
                throw new IllegalArgumentException("Vui lòng điền đầy đủ thông tin!");
            }

            KhachHang kh = dsKhachHang.get(row);
            kh.setTenKH(tenKH);
            kh.setSdt(sdt);

            dataManager.saveToFile(dsKhachHang, "khachhang.dat");
            refreshTable();
            clearForm();
            txtMaKH.setText(generateMaKH());
            JOptionPane.showMessageDialog(this, "Sửa khách hàng thành công!");
        } catch (IllegalArgumentException | IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void xoaKhachHang() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa khách hàng này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                dsKhachHang.remove(row);
                dataManager.saveToFile(dsKhachHang, "khachhang.dat");
                refreshTable();
                clearForm();
                txtMaKH.setText(generateMaKH());
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu file: " + e.getMessage());
            }
        }
    }

    private void sapXepKhachHang() {
        dsKhachHang.sort(Comparator.comparing(KhachHang::getMaKH, sapXepTangDan ? String::compareTo : Comparator.reverseOrder()));
        sapXepTangDan = !sapXepTangDan;
        refreshTable();
        JOptionPane.showMessageDialog(this, "Đã sắp xếp khách hàng theo mã " + (sapXepTangDan ? "giảm dần" : "tăng dần") + "!");
    }

    private void refreshTable() {
        String[] columns = {"Mã KH", "Tên KH", "SĐT"};
        Object[][] data = new Object[dsKhachHang.size()][3];
        for (int i = 0; i < dsKhachHang.size(); i++) {
            KhachHang kh = dsKhachHang.get(i);
            data[i] = new Object[]{kh.getMaKH(), kh.getTenKH(), kh.getSdt()};
        }
        table.setModel(new javax.swing.table.DefaultTableModel(data, columns));
        table.revalidate();
        table.repaint();
    }

    private void clearForm() {
        txtMaKH.setText(generateMaKH());
        txtTenKH.setText("");
        txtSdt.setText("");
    }
}