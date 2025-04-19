package do_an_1;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PanelKhachHang extends JPanel {
    private CustomerManager customerManager;
    private JTable table;
    private JTextField txtMaKH, txtTenKH, txtSdt;
    private DataManager<HoaDon> hoaDonDataManager;
    private boolean sapXepTangDan = true;

    public PanelKhachHang() {
        setBackground(Color.decode("#F8EAD9"));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        customerManager = new CustomerManager();
        hoaDonDataManager = new FileDataManager<>();

        String[] columns = {"Mã KH", "Tên KH", "SĐT"};
        Object[][] data = new Object[customerManager.getDsKhachHang().size()][3];
        for (int i = 0; i < customerManager.getDsKhachHang().size(); i++) {
            KhachHang kh = customerManager.getDsKhachHang().get(i);
            data[i] = new Object[]{kh.getMaKH(), kh.getTenKH(), kh.getSdt()};
        }
        table = new JTable(data, columns);
        table.setFillsViewportHeight(true);
        JScrollPane tableScrollPane = new JScrollPane(table);
        add(tableScrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBackground(Color.decode("#F8EAD9"));

        formPanel.add(new JLabel("Mã KH:"));
        txtMaKH = new JTextField(customerManager.generateMaKH());
        txtMaKH.setEditable(false);
        formPanel.add(txtMaKH);

        formPanel.add(new JLabel("Tên KH:"));
        txtTenKH = new JTextField();
        formPanel.add(txtTenKH);

        formPanel.add(new JLabel("SĐT:"));
        txtSdt = new JTextField();
        formPanel.add(txtSdt);

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

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1 && !e.getValueIsAdjusting()) {
                KhachHang kh = customerManager.getDsKhachHang().get(row);
                txtMaKH.setText(kh.getMaKH());
                txtTenKH.setText(kh.getTenKH());
                txtSdt.setText(kh.getSdt());
            }
        });

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
        KhachHang kh = customerManager.getDsKhachHang().get(row);

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chi tiết khách hàng", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(Color.decode("#F8EAD9"));

        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        infoPanel.setBackground(Color.decode("#F8EAD9"));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        infoPanel.add(new JLabel("Mã KH:"));
        infoPanel.add(new JLabel(kh.getMaKH()));

        infoPanel.add(new JLabel("Tên KH:"));
        infoPanel.add(new JLabel(kh.getTenKH()));

        infoPanel.add(new JLabel("SĐT:"));
        infoPanel.add(new JLabel(kh.getSdt()));

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

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.decode("#F8EAD9"));
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(historyPanel, BorderLayout.CENTER);

        dialog.add(mainPanel, BorderLayout.CENTER);

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

            String finalTenKH = tenKH.toLowerCase();
            String finalSdt = sdt.replaceAll("\\s+", "");
            boolean isDuplicate = customerManager.getDsKhachHang().stream()
                    .anyMatch(kh -> kh.getTenKH().toLowerCase().equals(finalTenKH) || 
                                    kh.getSdt().replaceAll("\\s+", "").equals(finalSdt));
            if (isDuplicate) {
                throw new IllegalArgumentException("Khách hàng đã tồn tại!");
            }

            KhachHang kh = new KhachHang(maKH, tenKH, sdt);
            customerManager.addKhachHang(kh);
            refreshTable();
            clearForm();
            txtMaKH.setText(customerManager.generateMaKH());
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

            String finalTenKH = tenKH.toLowerCase();
            String finalSdt = sdt.replaceAll("\\s+", "");
            KhachHang currentKH = customerManager.getDsKhachHang().get(row);
            boolean isDuplicate = customerManager.getDsKhachHang().stream()
                    .anyMatch(kh -> kh != currentKH && 
                                    (kh.getTenKH().toLowerCase().equals(finalTenKH) || 
                                     kh.getSdt().replaceAll("\\s+", "").equals(finalSdt)));
            if (isDuplicate) {
                throw new IllegalArgumentException("Khách hàng đã tồn tại!");
            }

            currentKH.setTenKH(tenKH);
            currentKH.setSdt(sdt);
            customerManager.updateKhachHang(currentKH);
            refreshTable();
            clearForm();
            txtMaKH.setText(customerManager.generateMaKH());
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
                customerManager.deleteKhachHang(customerManager.getDsKhachHang().get(row).getMaKH());
                refreshTable();
                clearForm();
                txtMaKH.setText(customerManager.generateMaKH());
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu file: " + e.getMessage());
            }
        }
    }

    private void sapXepKhachHang() {
        customerManager.sortByMaKH(sapXepTangDan);
        sapXepTangDan = !sapXepTangDan;
        refreshTable();
        JOptionPane.showMessageDialog(this, "Đã sắp xếp khách hàng theo mã " + (sapXepTangDan ? "giảm dần" : "tăng dần") + "!");
    }

    private void refreshTable() {
        String[] columns = {"Mã KH", "Tên KH", "SĐT"};
        Object[][] data = new Object[customerManager.getDsKhachHang().size()][3];
        for (int i = 0; i < customerManager.getDsKhachHang().size(); i++) {
            KhachHang kh = customerManager.getDsKhachHang().get(i);
            data[i] = new Object[]{kh.getMaKH(), kh.getTenKH(), kh.getSdt()};
        }
        table.setModel(new javax.swing.table.DefaultTableModel(data, columns));
        table.revalidate();
        table.repaint();
    }

    private void clearForm() {
        txtMaKH.setText(customerManager.generateMaKH());
        txtTenKH.setText("");
        txtSdt.setText("");
    }
}