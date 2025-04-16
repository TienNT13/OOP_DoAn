package do_an_1;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PanelKhachHang extends JPanel {
    private List<KhachHang> dsKhachHang;
    private JTable table;
    private JTextField txtMaKH, txtTenKH, txtSdt;
    private DataManager<KhachHang> dataManager;
    private boolean sapXepTangDan = true; // Biến kiểm soát hướng sắp xếp

    public PanelKhachHang() {
        setBackground(Color.decode("#F8EAD9"));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        dataManager = new FileDataManager<>();
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
        txtMaKH = new JTextField();
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

        // Sự kiện chọn hàng trong bảng để điền thông tin vào form
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                KhachHang kh = dsKhachHang.get(row);
                txtMaKH.setText(kh.getMaKH());
                txtTenKH.setText(kh.getTenKH());
                txtSdt.setText(kh.getSdt());
            }
        });
    }

    private void themKhachHang() {
        try {
            String maKH = txtMaKH.getText().trim();
            String tenKH = txtTenKH.getText().trim();
            String sdt = txtSdt.getText().trim();

            if (maKH.isEmpty() || tenKH.isEmpty() || sdt.isEmpty()) {
                throw new IllegalArgumentException("Vui lòng điền đầy đủ thông tin!");
            }

            if (dsKhachHang.stream().anyMatch(kh -> kh.getMaKH().equals(maKH))) {
                throw new IllegalArgumentException("Mã khách hàng đã tồn tại!");
            }

            KhachHang kh = new KhachHang(maKH, tenKH, sdt);
            dsKhachHang.add(kh);
            dataManager.saveToFile(dsKhachHang, "khachhang.dat");
            refreshTable();
            clearForm();
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
            String maKH = txtMaKH.getText().trim();
            String tenKH = txtTenKH.getText().trim();
            String sdt = txtSdt.getText().trim();

            if (maKH.isEmpty() || tenKH.isEmpty() || sdt.isEmpty()) {
                throw new IllegalArgumentException("Vui lòng điền đầy đủ thông tin!");
            }

            if (dsKhachHang.stream().anyMatch(kh -> kh.getMaKH().equals(maKH) && !kh.getMaKH().equals(dsKhachHang.get(row).getMaKH()))) {
                throw new IllegalArgumentException("Mã khách hàng đã tồn tại!");
            }

            KhachHang kh = dsKhachHang.get(row);
            kh.setMaKH(maKH);
            kh.setTenKH(tenKH);
            kh.setSdt(sdt);

            dataManager.saveToFile(dsKhachHang, "khachhang.dat");
            refreshTable();
            clearForm();
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
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu file: " + e.getMessage());
            }
        }
    }

    private void sapXepKhachHang() {
        dsKhachHang.sort(Comparator.comparing(KhachHang::getMaKH, sapXepTangDan ? String::compareTo : Comparator.reverseOrder()));
        sapXepTangDan = !sapXepTangDan; // Đảo hướng sắp xếp cho lần nhấn tiếp theo
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
        txtMaKH.setText("");
        txtTenKH.setText("");
        txtSdt.setText("");
    }
}