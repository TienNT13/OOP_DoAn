package do_an_1;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PanelHoaDon extends JPanel {
    private InvoiceManager invoiceManager;
    private ProductManager productManager;
    private CustomerManager customerManager;
    private JTable table, tempTable;
    private JTextField txtMaHD, txtMaKH, txtMaSP, txtSoLuong;
    private JButton btnThemSP, btnXoaSP, btnXacNhanHD, btnSuaHD, btnXoaHD;
    private List<Object[]> tempSanPham;

    private String formatPrice(double price) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(price) + " VND";
    }

    public PanelHoaDon() {
        setBackground(Color.decode("#F8EAD9"));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        invoiceManager = new InvoiceManager();
        productManager = new ProductManager();
        customerManager = new CustomerManager();
        tempSanPham = new ArrayList<>();

        String[] columns = {"Mã HD", "Tên KH", "Tổng tiền", "Ngày lập"};
        Object[][] data = new Object[0][4];
        table = new JTable(data, columns);
        table.setFillsViewportHeight(true);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách hóa đơn đã xác nhận"));
        add(tableScrollPane, BorderLayout.CENTER);
        refreshTable();

        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBackground(Color.decode("#F8EAD9"));

        JPanel infoPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        infoPanel.setBackground(Color.decode("#F8EAD9"));
        infoPanel.add(new JLabel("Mã HD:"));
        txtMaHD = new JTextField(invoiceManager.generateMaHD());
        txtMaHD.setEditable(false);
        infoPanel.add(txtMaHD);
        infoPanel.add(new JLabel("Mã KH:"));
        txtMaKH = new JTextField();
        infoPanel.add(txtMaKH);

        JPanel spPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        spPanel.setBackground(Color.decode("#F8EAD9"));
        spPanel.setBorder(BorderFactory.createTitledBorder("Thêm sản phẩm"));
        spPanel.add(new JLabel("Mã SP:"));
        txtMaSP = new JTextField();
        spPanel.add(txtMaSP);
        spPanel.add(new JLabel("Số lượng:"));
        txtSoLuong = new JTextField();
        spPanel.add(txtSoLuong);
        btnThemSP = new JButton("Thêm SP");
        btnThemSP.addActionListener(e -> themSanPhamTam());
        spPanel.add(btnThemSP);
        btnXoaSP = new JButton("Xóa SP");
        btnXoaSP.addActionListener(e -> xoaSanPhamTam());
        spPanel.add(btnXoaSP);

        String[] tempColumns = {"Mã SP", "Tên SP", "Số lượng", "Giá", "Thành tiền"};
        Object[][] tempData = new Object[0][5];
        tempTable = new JTable(tempData, tempColumns);
        JScrollPane tempScrollPane = new JScrollPane(tempTable);
        tempScrollPane.setBorder(BorderFactory.createTitledBorder("Sản phẩm trong hóa đơn"));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#F8EAD9"));
        btnXacNhanHD = new JButton("Xác nhận hóa đơn");
        btnXacNhanHD.addActionListener(e -> xacNhanHoaDon());
        buttonPanel.add(btnXacNhanHD);
        btnSuaHD = new JButton("Sửa hóa đơn");
        btnSuaHD.addActionListener(e -> suaHoaDon());
        buttonPanel.add(btnSuaHD);
        btnXoaHD = new JButton("Xóa hóa đơn");
        btnXoaHD.addActionListener(e -> xoaHoaDon());
        buttonPanel.add(btnXoaHD);

        formPanel.add(infoPanel, BorderLayout.NORTH);
        formPanel.add(spPanel, BorderLayout.CENTER);
        formPanel.add(tempScrollPane, BorderLayout.SOUTH);
        formPanel.add(buttonPanel, BorderLayout.EAST);
        add(formPanel, BorderLayout.NORTH);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row != -1) {
                    hienThiChiTietHoaDon(row);
                }
            }
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1 && !e.getValueIsAdjusting()) {
                String maHD = (String) table.getValueAt(row, 0);
                List<HoaDon> hds = invoiceManager.getHoaDonByMaHD(maHD);
                if (!hds.isEmpty()) {
                    txtMaHD.setText(maHD);
                    txtMaKH.setText(hds.get(0).getMaKH());
                    tempSanPham.clear();
                    for (HoaDon hd : hds) {
                        String maSP = hd.getTenSP();
                        SanPham sp = productManager.getDsSanPham().stream()
                                .filter(p -> p.getMaSP().equals(maSP))
                                .findFirst()
                                .orElse(null);
                        String tenSP = sp != null ? sp.getTen() : maSP;
                        double gia = sp != null ? sp.getGia() : hd.getTongTien() / hd.getSoLuong();
                        tempSanPham.add(new Object[]{maSP, tenSP, hd.getSoLuong(), formatPrice(gia), formatPrice(hd.getTongTien())});
                    }
                    refreshTempTable();
                }
            }
        });
    }

    private void themSanPhamTam() {
        try {
            String maSP = txtMaSP.getText().trim();
            String soLuongStr = txtSoLuong.getText().trim();

            if (maSP.isEmpty()) {
                throw new IllegalArgumentException("Vui lòng điền mã SP!");
            }

            int soLuong;
            try {
                soLuong = Integer.parseInt(soLuongStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Số lượng phải là số nguyên!");
            }

            if (soLuong <= 0) {
                throw new IllegalArgumentException("Hãy nhập số lượng sản phẩm lớn hơn 0");
            }

            SanPham sp = productManager.getDsSanPham().stream()
                    .filter(p -> p.getMaSP().equals(maSP))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Mã sản phẩm không tồn tại!"));

            if (sp.getSoLuong() < soLuong) {
                throw new IllegalArgumentException("Số lượng vượt quá tồn kho! Còn lại: " + sp.getSoLuong());
            }

            for (Object[] item : tempSanPham) {
                if (item[0].equals(maSP)) {
                    throw new IllegalArgumentException("Sản phẩm đã được thêm, vui lòng chọn sản phẩm khác!");
                }
            }

            double gia = sp.getGia();
            double thanhTien = gia * soLuong;
            tempSanPham.add(new Object[]{maSP, sp.getTen(), soLuong, formatPrice(gia), formatPrice(thanhTien)});
            refreshTempTable();
            txtMaSP.setText("");
            txtSoLuong.setText("");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void xoaSanPhamTam() {
        int row = tempTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xóa!");
            return;
        }
        tempSanPham.remove(row);
        refreshTempTable();
        JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!");
    }

    private void xacNhanHoaDon() {
        try {
            String maHD = txtMaHD.getText().trim();
            String maKH = txtMaKH.getText().trim();

            if (maKH.isEmpty()) {
                throw new IllegalArgumentException("Vui lòng nhập mã khách hàng!");
            }

            if (tempSanPham.isEmpty()) {
                throw new IllegalArgumentException("Vui lòng thêm ít nhất một sản phẩm!");
            }

            if (customerManager.getDsKhachHang().stream().noneMatch(kh -> kh.getMaKH().equals(maKH))) {
                throw new IllegalArgumentException("Mã khách hàng không tồn tại!");
            }

            double tongTien = 0;
            List<SanPham> dsSanPham = productManager.getDsSanPham();
            List<HoaDon> newHDs = new ArrayList<>();
            for (Object[] item : tempSanPham) {
                String maSP = (String) item[0];
                int soLuong = (int) item[2];
                SanPham sp = dsSanPham.stream()
                        .filter(p -> p.getMaSP().equals(maSP))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Mã sản phẩm không hợp lệ!"));
                tongTien += sp.getGia() * soLuong;
                sp.setSoLuong(sp.getSoLuong() - soLuong);

                HoaDon hd = new HoaDon(maHD, maKH, maSP, soLuong, sp.getGia() * soLuong, LocalDate.now());
                newHDs.add(hd);
            }

            invoiceManager.addHoaDon(newHDs);
            for (SanPham sp : dsSanPham) {
                productManager.updateSanPham(sp); // Cập nhật tồn kho
            }
            refreshTable();
            hienThiHoaDonTong(maHD, maKH, newHDs, tongTien);
            tempSanPham.clear();
            refreshTempTable();
            txtMaHD.setText(invoiceManager.generateMaHD());
            txtMaKH.setText("");
            txtMaSP.setText("");
            txtSoLuong.setText("");
            JOptionPane.showMessageDialog(this, "Xác nhận hóa đơn thành công!");
        } catch (IllegalArgumentException | IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void suaHoaDon() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để sửa!");
            return;
        }

        try {
            String maHD = txtMaHD.getText().trim();
            String maKH = txtMaKH.getText().trim();

            if (maKH.isEmpty()) {
                throw new IllegalArgumentException("Vui lòng nhập mã khách hàng!");
            }

            if (tempSanPham.isEmpty()) {
                throw new IllegalArgumentException("Vui lòng thêm ít nhất một sản phẩm!");
            }

            if (customerManager.getDsKhachHang().stream().noneMatch(kh -> kh.getMaKH().equals(maKH))) {
                throw new IllegalArgumentException("Mã khách hàng không tồn tại!");
            }

            List<HoaDon> oldHDs = invoiceManager.getHoaDonByMaHD(maHD);
            for (HoaDon hd : oldHDs) {
                SanPham sp = productManager.getDsSanPham().stream()
                        .filter(p -> p.getMaSP().equals(hd.getTenSP()))
                        .findFirst()
                        .orElse(null);
                if (sp != null) {
                    sp.setSoLuong(sp.getSoLuong() + hd.getSoLuong());
                }
            }

            List<HoaDon> newHDs = new ArrayList<>();
            for (Object[] item : tempSanPham) {
                String maSP = (String) item[0];
                int soLuong = (int) item[2];
                SanPham sp = productManager.getDsSanPham().stream()
                        .filter(p -> p.getMaSP().equals(maSP))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Mã sản phẩm không hợp lệ!"));
                if (sp.getSoLuong() < soLuong) {
                    throw new IllegalArgumentException("Số lượng vượt quá tồn kho cho sản phẩm: " + sp.getTen());
                }
                sp.setSoLuong(sp.getSoLuong() - soLuong);
                HoaDon hd = new HoaDon(maHD, maKH, maSP, soLuong, sp.getGia() * soLuong, LocalDate.now());
                newHDs.add(hd);
            }

            invoiceManager.updateHoaDon(maHD, newHDs);
            for (SanPham sp : productManager.getDsSanPham()) {
                productManager.updateSanPham(sp); // Cập nhật tồn kho
            }
            refreshTable();
            tempSanPham.clear();
            refreshTempTable();
            txtMaHD.setText(invoiceManager.generateMaHD());
            txtMaKH.setText("");
            txtMaSP.setText("");
            txtSoLuong.setText("");
            JOptionPane.showMessageDialog(this, "Sửa hóa đơn thành công!");
        } catch (IllegalArgumentException | IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void xoaHoaDon() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa hóa đơn này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String maHD = (String) table.getValueAt(row, 0);
                List<HoaDon> hds = invoiceManager.getHoaDonByMaHD(maHD);
                for (HoaDon hd : hds) {
                    SanPham sp = productManager.getDsSanPham().stream()
                            .filter(p -> p.getMaSP().equals(hd.getTenSP()))
                            .findFirst()
                            .orElse(null);
                    if (sp != null) {
                        sp.setSoLuong(sp.getSoLuong() + hd.getSoLuong());
                    }
                }
                invoiceManager.deleteHoaDon(maHD);
                for (SanPham sp : productManager.getDsSanPham()) {
                    productManager.updateSanPham(sp); // Cập nhật tồn kho
                }
                refreshTable();
                tempSanPham.clear();
                refreshTempTable();
                txtMaHD.setText(invoiceManager.generateMaHD());
                txtMaKH.setText("");
                txtMaSP.setText("");
                txtSoLuong.setText("");
                JOptionPane.showMessageDialog(this, "Xóa hóa đơn thành công!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu file: " + e.getMessage());
            }
        }
    }

    private void hienThiChiTietHoaDon(int row) {
        String maHD = (String) table.getValueAt(row, 0);
        List<HoaDon> chiTietHD = invoiceManager.getHoaDonByMaHD(maHD);
        String maKH = chiTietHD.isEmpty() ? "" : chiTietHD.get(0).getMaKH();
        String tenKH = customerManager.getDsKhachHang().stream()
                .filter(kh -> kh.getMaKH().equals(maKH))
                .findFirst()
                .map(KhachHang::getTenKH)
                .orElse(maKH);

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chi tiết hóa đơn", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(Color.decode("#F8EAD9"));

        JPanel infoPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        infoPanel.setBackground(Color.decode("#F8EAD9"));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        infoPanel.add(new JLabel("Mã HD:"));
        infoPanel.add(new JLabel(maHD));
        infoPanel.add(new JLabel("Mã KH:"));
        infoPanel.add(new JLabel(maKH));
        infoPanel.add(new JLabel("Tên KH:"));
        infoPanel.add(new JLabel(tenKH));
        infoPanel.add(new JLabel("Ngày lập:"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String ngayLap = chiTietHD.isEmpty() ? "" : chiTietHD.get(0).getNgayLap().format(formatter);
        infoPanel.add(new JLabel(ngayLap));

        JPanel spPanel = new JPanel(new BorderLayout());
        spPanel.setBackground(Color.decode("#F8EAD9"));
        spPanel.setBorder(BorderFactory.createTitledBorder("Danh sách sản phẩm"));

        String[] columns = {"Tên SP", "Số lượng", "Giá", "Thành tiền"};
        List<Object[]> dataList = new ArrayList<>();
        double tongTien = 0;
        for (HoaDon hd : chiTietHD) {
            String maSP = hd.getTenSP();
            SanPham sp = productManager.getDsSanPham().stream()
                    .filter(p -> p.getMaSP().equals(maSP))
                    .findFirst()
                    .orElse(null);
            String tenSP = sp != null ? sp.getTen() : maSP;
            double gia = sp != null ? sp.getGia() : hd.getTongTien() / hd.getSoLuong();
            tongTien += hd.getTongTien();
            dataList.add(new Object[]{tenSP, hd.getSoLuong(), formatPrice(gia), formatPrice(hd.getTongTien())});
        }
        Object[][] data = dataList.toArray(new Object[0][]);
        JTable spTable = new JTable(data, columns);
        spTable.setFillsViewportHeight(true);
        JScrollPane spScrollPane = new JScrollPane(spTable);
        spPanel.add(spScrollPane, BorderLayout.CENTER);

        JLabel lblTongTien = new JLabel("Tổng tiền: " + formatPrice(tongTien));
        lblTongTien.setHorizontalAlignment(SwingConstants.RIGHT);
        spPanel.add(lblTongTien, BorderLayout.SOUTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.decode("#F8EAD9"));
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(spPanel, BorderLayout.CENTER);
        dialog.add(mainPanel, BorderLayout.CENTER);

        JButton btnDong = new JButton("Đóng");
        btnDong.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#F8EAD9"));
        buttonPanel.add(btnDong);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void hienThiHoaDonTong(String maHD, String maKH, List<HoaDon> hds, double tongTien) {
        String tenKH = customerManager.getDsKhachHang().stream()
                .filter(kh -> kh.getMaKH().equals(maKH))
                .findFirst()
                .map(KhachHang::getTenKH)
                .orElse(maKH);

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chi tiết hóa đơn", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(Color.decode("#F8EAD9"));

        JPanel infoPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        infoPanel.setBackground(Color.decode("#F8EAD9"));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        infoPanel.add(new JLabel("Mã HD:"));
        infoPanel.add(new JLabel(maHD));
        infoPanel.add(new JLabel("Mã KH:"));
        infoPanel.add(new JLabel(maKH));
        infoPanel.add(new JLabel("Tên KH:"));
        infoPanel.add(new JLabel(tenKH));
        infoPanel.add(new JLabel("Ngày lập:"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        infoPanel.add(new JLabel(hds.get(0).getNgayLap().format(formatter)));

        JPanel spPanel = new JPanel(new BorderLayout());
        spPanel.setBackground(Color.decode("#F8EAD9"));
        spPanel.setBorder(BorderFactory.createTitledBorder("Danh sách sản phẩm"));

        String[] columns = {"Tên SP", "Số lượng", "Giá", "Thành tiền"};
        List<Object[]> dataList = new ArrayList<>();
        for (HoaDon hd : hds) {
            String maSP = hd.getTenSP();
            SanPham sp = productManager.getDsSanPham().stream()
                    .filter(p -> p.getMaSP().equals(maSP))
                    .findFirst()
                    .orElse(null);
            String tenSP = sp != null ? sp.getTen() : maSP;
            double gia = sp != null ? sp.getGia() : hd.getTongTien() / hd.getSoLuong();
            dataList.add(new Object[]{tenSP, hd.getSoLuong(), formatPrice(gia), formatPrice(hd.getTongTien())});
        }
        Object[][] data = dataList.toArray(new Object[0][]);
        JTable spTable = new JTable(data, columns);
        spTable.setFillsViewportHeight(true);
        JScrollPane spScrollPane = new JScrollPane(spTable);
        spPanel.add(spScrollPane, BorderLayout.CENTER);

        JLabel lblTongTien = new JLabel("Tổng tiền: " + formatPrice(tongTien));
        lblTongTien.setHorizontalAlignment(SwingConstants.RIGHT);
        spPanel.add(lblTongTien, BorderLayout.SOUTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.decode("#F8EAD9"));
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(spPanel, BorderLayout.CENTER);
        dialog.add(mainPanel, BorderLayout.CENTER);

        JButton btnDong = new JButton("Đóng");
        btnDong.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#F8EAD9"));
        buttonPanel.add(btnDong);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void refreshTable() {
        List<String> uniqueMaHDs = invoiceManager.getDsHoaDon().stream()
                .map(HoaDon::getMaHD)
                .distinct()
                .collect(Collectors.toList());
        Object[][] data = new Object[uniqueMaHDs.size()][4];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (int i = 0; i < uniqueMaHDs.size(); i++) {
            String maHD = uniqueMaHDs.get(i);
            List<HoaDon> hds = invoiceManager.getHoaDonByMaHD(maHD);
            double tongTien = hds.stream().mapToDouble(HoaDon::getTongTien).sum();
            String maKH = hds.get(0).getMaKH();
            String tenKH = customerManager.getDsKhachHang().stream()
                    .filter(kh -> kh.getMaKH().equals(maKH))
                    .findFirst()
                    .map(KhachHang::getTenKH)
                    .orElse(maKH);
            data[i] = new Object[]{maHD, tenKH, formatPrice(tongTien), hds.get(0).getNgayLap().format(formatter)};
        }
        String[] columns = {"Mã HD", "Tên KH", "Tổng tiền", "Ngày lập"};
        table.setModel(new javax.swing.table.DefaultTableModel(data, columns));
        table.revalidate();
        table.repaint();
    }

    private void refreshTempTable() {
        Object[][] data = tempSanPham.toArray(new Object[0][]);
        String[] columns = {"Mã SP", "Tên SP", "Số lượng", "Giá", "Thành tiền"};
        tempTable.setModel(new javax.swing.table.DefaultTableModel(data, columns));
        tempTable.revalidate();
        tempTable.repaint();
    }
}