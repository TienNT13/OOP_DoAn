package do_an_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PanelSanPham extends JPanel {
    private ProductManager productManager;
    private List<SanPham> filteredSanPham;
    private JTextField txtMaSP, txtTen, txtHang, txtGia, txtSoLuong, txtChip, txtSoCamera, txtDungLuongPin, txtKichThuocManHinh;
    private JTextField txtHinhMinhHoa;
    private JPanel formPanel;
    private DataManager<HoaDon> hoaDonDataManager;
    private boolean isEditing = false;
    private JPanel productPanel;
    private JComboBox<String> hangComboBox;

    public PanelSanPham() {
        setBackground(Color.decode("#F8EAD9"));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        productManager = new ProductManager();
        hoaDonDataManager = new FileDataManager<>();
        filteredSanPham = new ArrayList<>(productManager.getDsSanPham());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.decode("#F8EAD9"));
        JButton btnShowForm = new JButton("Thêm sản phẩm");
        JButton btnSua = new JButton("Sửa sản phẩm");
        JButton btnXoa = new JButton("Xóa sản phẩm");
        JButton btnSortByHang = new JButton("Sắp xếp theo hãng");

        btnShowForm.setBackground(Color.decode("#659287"));
        btnShowForm.setForeground(Color.WHITE);
        btnShowForm.setFont(new Font("Arial", Font.BOLD, 12));
        btnShowForm.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        btnShowForm.addActionListener(e -> {
            formPanel.setVisible(true);
            clearForm();
            txtMaSP.setText(productManager.generateMaSP());
            isEditing = false;
        });

        btnSua.setBackground(Color.decode("#B1C29E"));
        btnSua.setForeground(Color.BLACK);
        btnSua.setFont(new Font("Arial", Font.BOLD, 12));
        btnSua.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        btnSua.addActionListener(e -> showProductSelectionDialog());

        btnXoa.setBackground(Color.decode("#A2D2DF"));
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setFont(new Font("Arial", Font.BOLD, 12));
        btnXoa.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        btnXoa.addActionListener(e -> showDeleteProductDialog());

        btnSortByHang.setBackground(Color.decode("#89A8B2"));
        btnSortByHang.setForeground(Color.WHITE);
        btnSortByHang.setFont(new Font("Arial", Font.BOLD, 12));
        btnSortByHang.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        btnSortByHang.addActionListener(e -> {
            productManager.sortByHang();
            filteredSanPham = new ArrayList<>(productManager.getDsSanPham());
            refreshProductGrid();
            updateHangComboBox();
            JOptionPane.showMessageDialog(this, "Đã sắp xếp sản phẩm theo hãng!");
        });

        JLabel lblLocHang = new JLabel("Lọc theo hãng: ");
        lblLocHang.setFont(new Font("Arial", Font.BOLD, 12));
        hangComboBox = new JComboBox<>();
        updateHangComboBox();
        hangComboBox.setPreferredSize(new Dimension(150, 25));
        hangComboBox.addActionListener(e -> {
            String selectedHang = (String) hangComboBox.getSelectedItem();
            filterByHang(selectedHang);
            refreshProductGrid();
        });

        buttonPanel.add(btnShowForm);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnSortByHang);
        buttonPanel.add(lblLocHang);
        buttonPanel.add(hangComboBox);
        add(buttonPanel, BorderLayout.NORTH);

        formPanel = new JPanel(new GridLayout(11, 2, 5, 5));
        formPanel.setBackground(Color.decode("#FFF3E0"));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.decode("#D3A875"), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        formPanel.add(new JLabel("Mã SP:"));
        txtMaSP = new JTextField();
        txtMaSP.setBorder(BorderFactory.createLineBorder(Color.decode("#D3A875")));
        txtMaSP.setEditable(false);
        formPanel.add(txtMaSP);

        formPanel.add(new JLabel("Tên:"));
        txtTen = new JTextField();
        txtTen.setBorder(BorderFactory.createLineBorder(Color.decode("#D3A875")));
        formPanel.add(txtTen);

        formPanel.add(new JLabel("Hãng:"));
        txtHang = new JTextField();
        txtHang.setBorder(BorderFactory.createLineBorder(Color.decode("#D3A875")));
        formPanel.add(txtHang);

        formPanel.add(new JLabel("Giá:"));
        txtGia = new JTextField();
        txtGia.setBorder(BorderFactory.createLineBorder(Color.decode("#D3A875")));
        formPanel.add(txtGia);

        formPanel.add(new JLabel("Số lượng:"));
        txtSoLuong = new JTextField();
        txtSoLuong.setBorder(BorderFactory.createLineBorder(Color.decode("#D3A875")));
        formPanel.add(txtSoLuong);

        formPanel.add(new JLabel("Chip:"));
        txtChip = new JTextField();
        txtChip.setBorder(BorderFactory.createLineBorder(Color.decode("#D3A875")));
        formPanel.add(txtChip);

        formPanel.add(new JLabel("Số camera:"));
        txtSoCamera = new JTextField();
        txtSoCamera.setBorder(BorderFactory.createLineBorder(Color.decode("#D3A875")));
        formPanel.add(txtSoCamera);

        formPanel.add(new JLabel("Dung lượng pin (mAh):"));
        txtDungLuongPin = new JTextField();
        txtDungLuongPin.setBorder(BorderFactory.createLineBorder(Color.decode("#D3A875")));
        formPanel.add(txtDungLuongPin);

        formPanel.add(new JLabel("Kích thước màn hình (inch):"));
        txtKichThuocManHinh = new JTextField();
        txtKichThuocManHinh.setBorder(BorderFactory.createLineBorder(Color.decode("#D3A875")));
        formPanel.add(txtKichThuocManHinh);

        formPanel.add(new JLabel("Hình minh họa:"));
        JPanel hinhPanel = new JPanel(new BorderLayout());
        hinhPanel.setBackground(Color.decode("#FFF3E0"));
        txtHinhMinhHoa = new JTextField();
        txtHinhMinhHoa.setEditable(false);
        txtHinhMinhHoa.setBorder(BorderFactory.createLineBorder(Color.decode("#D3A875")));
        JButton btnChonAnh = new JButton("Chọn ảnh");
        btnChonAnh.setBackground(Color.decode("#D3A875"));
        btnChonAnh.setForeground(Color.WHITE);
        btnChonAnh.addActionListener(e -> chonAnh());
        hinhPanel.add(txtHinhMinhHoa, BorderLayout.CENTER);
        hinhPanel.add(btnChonAnh, BorderLayout.EAST);
        formPanel.add(hinhPanel);

        JPanel formButtonPanel = new JPanel(new FlowLayout());
        formButtonPanel.setBackground(Color.decode("#FFF3E0"));
        JButton btnThem = new JButton("Xác nhận");
        JButton btnHuy = new JButton("Hủy");
        JButton btnThoat = new JButton("Thoát");

        btnThem.setBackground(Color.decode("#FFB4A2"));
        btnThem.setForeground(Color.WHITE);
        btnThem.addActionListener(e -> {
            if (isEditing) {
                suaSanPham();
            } else {
                themSanPham();
            }
        });

        btnHuy.setBackground(Color.decode("#E5989B"));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.addActionListener(e -> {
            formPanel.setVisible(false);
            clearForm();
            isEditing = false;
        });

        btnThoat.setBackground(Color.decode("#E5989B"));
        btnThoat.setForeground(Color.WHITE);
        btnThoat.addActionListener(e -> {
            formPanel.setVisible(false);
            clearForm();
            isEditing = false;
        });

        formButtonPanel.add(btnThem);
        formButtonPanel.add(btnHuy);
        formButtonPanel.add(btnThoat);
        formPanel.add(new JLabel(""));
        formPanel.add(formButtonPanel);

        add(formPanel, BorderLayout.WEST);
        formPanel.setVisible(false);

        productPanel = new JPanel(new GridLayout(0, 5, 15, 15));
        productPanel.setBackground(Color.decode("#F8EAD9"));
        JScrollPane productScrollPane = new JScrollPane(productPanel);
        productScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        productScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        productScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(productScrollPane, BorderLayout.CENTER);

        refreshProductGrid();
    }

    private void updateHangComboBox() {
        hangComboBox.removeAllItems();
        hangComboBox.addItem("Tất cả");
        Set<String> hangSet = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (SanPham sp : productManager.getDsSanPham()) {
            hangSet.add(sp.getHang());
        }
        for (String hang : hangSet) {
            hangComboBox.addItem(hang);
        }
    }

    private void filterByHang(String hang) {
        filteredSanPham.clear();
        if (hang == null || hang.equals("Tất cả")) {
            filteredSanPham.addAll(productManager.getDsSanPham());
        } else {
            for (SanPham sp : productManager.getDsSanPham()) {
                if (sp.getHang().equalsIgnoreCase(hang)) {
                    filteredSanPham.add(sp);
                }
            }
        }
    }

    private void showProductSelectionDialog() {
        if (productManager.getDsSanPham().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Danh sách sản phẩm trống!");
            return;
        }

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chọn sản phẩm để sửa", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(Color.decode("#F8EAD9"));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.decode("#F8EAD9"));

        JComboBox<String> productComboBox = new JComboBox<>();
        for (SanPham sp : productManager.getDsSanPham()) {
            productComboBox.addItem(sp.getTen() + " (" + sp.getMaSP() + ")");
        }
        mainPanel.add(productComboBox, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#F8EAD9"));

        JButton btnChon = new JButton("Chọn");
        btnChon.setBackground(Color.decode("#4CAF50"));
        btnChon.setForeground(Color.WHITE);
        btnChon.addActionListener(e -> {
            int selectedIndex = productComboBox.getSelectedIndex();
            if (selectedIndex != -1) {
                SanPham selectedProduct = productManager.getDsSanPham().get(selectedIndex);
                chuanBiSuaSanPham(selectedProduct);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Vui lòng chọn một sản phẩm!");
            }
        });

        JButton btnHuy = new JButton("Hủy");
        btnHuy.setBackground(Color.decode("#F44336"));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnChon);
        buttonPanel.add(btnHuy);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private void showDeleteProductDialog() {
        if (productManager.getDsSanPham().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Danh sách sản phẩm trống!");
            return;
        }

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chọn sản phẩm để xóa", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(Color.decode("#F8EAD9"));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.decode("#F8EAD9"));

        JComboBox<String> productComboBox = new JComboBox<>();
        for (SanPham sp : productManager.getDsSanPham()) {
            productComboBox.addItem(sp.getTen() + " (" + sp.getMaSP() + ")");
        }
        mainPanel.add(productComboBox, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#F8EAD9"));

        JButton btnXacNhan = new JButton("Xác nhận");
        btnXacNhan.setBackground(Color.decode("#4CAF50"));
        btnXacNhan.setForeground(Color.WHITE);
        btnXacNhan.addActionListener(e -> {
            int selectedIndex = productComboBox.getSelectedIndex();
            if (selectedIndex != -1) {
                int confirm = JOptionPane.showConfirmDialog(dialog, 
                    "Bạn có chắc muốn xóa sản phẩm này?", 
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        productManager.deleteSanPham(productManager.getDsSanPham().get(selectedIndex).getMaSP());
                        filteredSanPham = new ArrayList<>(productManager.getDsSanPham());
                        updateHangComboBox();
                        refreshProductGrid();
                        JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!");
                        dialog.dispose();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi lưu file: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(dialog, "Vui lòng chọn một sản phẩm!");
            }
        });

        JButton btnHuy = new JButton("Hủy");
        btnHuy.setBackground(Color.decode("#F44336"));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnXacNhan);
        buttonPanel.add(btnHuy);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private void chuanBiSuaSanPham(SanPham sp) {
        txtMaSP.setText(sp.getMaSP());
        txtTen.setText(sp.getTen());
        txtHang.setText(sp.getHang());
        txtGia.setText(String.valueOf(sp.getGia()));
        txtSoLuong.setText(String.valueOf(sp.getSoLuong()));
        txtChip.setText(sp.getChip());
        txtSoCamera.setText(String.valueOf(sp.getSoCamera()));
        txtDungLuongPin.setText(String.valueOf(sp.getDungLuongPin()));
        txtKichThuocManHinh.setText(String.valueOf(sp.getKichThuocManHinh()));
        txtHinhMinhHoa.setText(sp.getHinhMinhHoa() != null ? sp.getHinhMinhHoa() : "");
        formPanel.setVisible(true);
        isEditing = true;
    }

    private void refreshProductGrid() {
        productPanel.removeAll();
        int soHang = Math.max(1, (int) Math.ceil(filteredSanPham.size() / 5.0));
        int panelWidth = 150 * 5 + 15 * 4;
        int panelHeight = soHang * (200 + 15);
        productPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        productPanel.setMaximumSize(new Dimension(panelWidth, panelHeight));

        for (int i = 0; i < filteredSanPham.size(); i++) {
            SanPham sp = filteredSanPham.get(i);
            int index = productManager.getDsSanPham().indexOf(sp);

            JPanel itemPanel = new JPanel(new BorderLayout(5, 5));
            itemPanel.setBackground(Color.WHITE);
            itemPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            itemPanel.setPreferredSize(new Dimension(150, 200));
            itemPanel.setMaximumSize(new Dimension(150, 200));
            itemPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 2, Color.decode("#D3A875")),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));

            JLabel lblHinh = new JLabel();
            lblHinh.setHorizontalAlignment(SwingConstants.CENTER);
            if (sp.getHinhMinhHoa() != null && !sp.getHinhMinhHoa().isEmpty()) {
                try {
                    ImageIcon icon = new ImageIcon(sp.getHinhMinhHoa());
                    Image scaledImage = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                    lblHinh.setIcon(new ImageIcon(scaledImage));
                } catch (Exception e) {
                    lblHinh.setText("Không có ảnh");
                }
            } else {
                lblHinh.setText("Không có ảnh");
            }
            itemPanel.add(lblHinh, BorderLayout.NORTH);

            JPanel infoPanel = new JPanel();
            infoPanel.setBackground(Color.WHITE);
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

            JLabel lblTen = new JLabel(sp.getTen());
            lblTen.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblTen.setFont(new Font("Arial", Font.BOLD, 12));
            infoPanel.add(lblTen);

            JLabel lblGia = new JLabel(productManager.formatPrice(sp.getGia()));
            lblGia.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblGia.setForeground(Color.BLACK);
            lblGia.setFont(new Font("Arial", Font.BOLD, 12));
            infoPanel.add(lblGia);

            itemPanel.add(infoPanel, BorderLayout.CENTER);

            itemPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    itemPanel.setBackground(Color.decode("#F5F5F5"));
                    itemPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.decode("#4CAF50"), 2),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                    ));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (itemPanel.getBorder() != BorderFactory.createLineBorder(Color.BLUE)) {
                        itemPanel.setBackground(Color.WHITE);
                        itemPanel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0, 0, 2, 2, Color.decode("#D3A875")),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)
                        ));
                    }
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    clearSelection();
                    itemPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                    hienThiChiTietSanPham(index);
                }
            });

            productPanel.add(itemPanel);
        }
        productPanel.revalidate();
        productPanel.repaint();
    }

    private void hienThiChiTietSanPham(int row) {
        SanPham sp = productManager.getDsSanPham().get(row);

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chi tiết sản phẩm", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(Color.decode("#F8EAD9"));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.decode("#F8EAD9"));

        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.decode("#F8EAD9"));
        JLabel lblHinhMinhHoa = new JLabel("");
        if (sp.getHinhMinhHoa() != null && !sp.getHinhMinhHoa().isEmpty()) {
            try {
                ImageIcon icon = new ImageIcon(sp.getHinhMinhHoa());
                int maxSize = 200;
                Image image = icon.getImage();
                int width = image.getWidth(null);
                int height = image.getHeight(null);
                double ratio = Math.min((double) maxSize / width, (double) maxSize / height);
                int newWidth = (int) (width * ratio);
                int newHeight = (int) (height * ratio);
                Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                lblHinhMinhHoa.setIcon(new ImageIcon(scaledImage));
            } catch (Exception ex) {
                lblHinhMinhHoa.setText("Lỗi tải hình");
            }
        } else {
            lblHinhMinhHoa.setText("Không có hình");
        }
        imagePanel.add(lblHinhMinhHoa);
        mainPanel.add(imagePanel, BorderLayout.WEST);

        JPanel detailPanel = new JPanel(new GridLayout(10, 2, 5, 5));
        detailPanel.setBackground(Color.decode("#F8EAD9"));

        detailPanel.add(new JLabel("Mã SP:"));
        detailPanel.add(new JLabel(sp.getMaSP()));

        detailPanel.add(new JLabel("Tên:"));
        detailPanel.add(new JLabel(sp.getTen()));

        detailPanel.add(new JLabel("Hãng:"));
        detailPanel.add(new JLabel(sp.getHang()));

        detailPanel.add(new JLabel("Giá:"));
        detailPanel.add(new JLabel(productManager.formatPrice(sp.getGia())));

        detailPanel.add(new JLabel("Số lượng:"));
        detailPanel.add(new JLabel(String.valueOf(sp.getSoLuong())));

        detailPanel.add(new JLabel("Số lượng đã bán:"));
        detailPanel.add(new JLabel(String.valueOf(tinhSoLuongDaBan(sp.getMaSP()))));

        detailPanel.add(new JLabel("Chip:"));
        detailPanel.add(new JLabel(sp.getChip()));

        detailPanel.add(new JLabel("Số camera:"));
        detailPanel.add(new JLabel(String.valueOf(sp.getSoCamera())));

        detailPanel.add(new JLabel("Dung lượng pin (mAh):"));
        detailPanel.add(new JLabel(String.valueOf(sp.getDungLuongPin())));

        detailPanel.add(new JLabel("Kích thước màn hình (inch):"));
        detailPanel.add(new JLabel(String.valueOf(sp.getKichThuocManHinh())));

        mainPanel.add(detailPanel, BorderLayout.CENTER);

        dialog.add(mainPanel, BorderLayout.CENTER);

        JButton btnDong = new JButton("Đóng");
        btnDong.setBackground(Color.decode("#D3A875"));
        btnDong.setForeground(Color.WHITE);
        btnDong.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#F8EAD9"));
        buttonPanel.add(btnDong);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void chonAnh() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "png", "jpeg"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            txtHinhMinhHoa.setText(selectedFile.getAbsolutePath());
        }
    }

    private void themSanPham() {
        try {
            String maSP = txtMaSP.getText().trim();
            String ten = txtTen.getText().trim();
            String hang = txtHang.getText().trim();
            String giaStr = txtGia.getText().trim();
            String soLuongStr = txtSoLuong.getText().trim();
            String chip = txtChip.getText().trim();
            String soCameraStr = txtSoCamera.getText().trim();
            String dungLuongPinStr = txtDungLuongPin.getText().trim();
            String kichThuocManHinhStr = txtKichThuocManHinh.getText().trim();
            String hinhMinhHoa = txtHinhMinhHoa.getText().trim();

            if (ten.isEmpty() || hang.isEmpty() || chip.isEmpty()) {
                throw new IllegalArgumentException("Vui lòng điền đầy đủ thông tin bắt buộc (Tên, Hãng, Chip)!");
            }

            int gia = Integer.parseInt(giaStr);
            int soLuong = Integer.parseInt(soLuongStr);
            int soCamera = Integer.parseInt(soCameraStr);
            int dungLuongPin = Integer.parseInt(dungLuongPinStr);
            double kichThuocManHinh = Double.parseDouble(kichThuocManHinhStr);

            if (gia <= 0 || soLuong <= 0 || soCamera <= 0 || dungLuongPin <= 0) {
                throw new IllegalArgumentException("Giá, số lượng, số camera, dung lượng pin phải là số nguyên dương!");
            }

            if (kichThuocManHinh <= 0) {
                throw new IllegalArgumentException("Kích thước màn hình phải là số dương!");
            }

            SanPham sp = new SanPham(maSP, ten, hang, gia, soLuong, chip, soCamera, dungLuongPin, kichThuocManHinh);
            sp.setHinhMinhHoa(hinhMinhHoa);
            productManager.addSanPham(sp);
            filteredSanPham = new ArrayList<>(productManager.getDsSanPham());
            updateHangComboBox();
            refreshProductGrid();
            formPanel.setVisible(false);
            clearForm();
            JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá, số lượng, số camera, dung lượng pin phải là số nguyên, kích thước màn hình phải là số!");
        } catch (IllegalArgumentException | IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void suaSanPham() {
        try {
            String maSP = txtMaSP.getText().trim();
            String ten = txtTen.getText().trim();
            String hang = txtHang.getText().trim();
            String giaStr = txtGia.getText().trim();
            String soLuongStr = txtSoLuong.getText().trim();
            String chip = txtChip.getText().trim();
            String soCameraStr = txtSoCamera.getText().trim();
            String dungLuongPinStr = txtDungLuongPin.getText().trim();
            String kichThuocManHinhStr = txtKichThuocManHinh.getText().trim();
            String hinhMinhHoa = txtHinhMinhHoa.getText().trim();

            if (maSP.isEmpty() || ten.isEmpty() || hang.isEmpty() || chip.isEmpty()) {
                throw new IllegalArgumentException("Vui lòng điền đầy đủ thông tin bắt buộc (Mã SP, Tên, Hãng, Chip)!");
            }

            int gia = Integer.parseInt(giaStr);
            int soLuong = Integer.parseInt(soLuongStr);
            int soCamera = Integer.parseInt(soCameraStr);
            int dungLuongPin = Integer.parseInt(dungLuongPinStr);
            double kichThuocManHinh = Double.parseDouble(kichThuocManHinhStr);

            if (gia <= 0 || soLuong <= 0 || soCamera <= 0 || dungLuongPin <= 0) {
                throw new IllegalArgumentException("Giá, số lượng, số camera, dung lượng pin phải là số nguyên dương!");
            }

            if (kichThuocManHinh <= 0) {
                throw new IllegalArgumentException("Kích thước màn hình phải là số dương!");
            }

            SanPham sp = new SanPham(maSP, ten, hang, gia, soLuong, chip, soCamera, dungLuongPin, kichThuocManHinh);
            sp.setHinhMinhHoa(hinhMinhHoa);
            productManager.updateSanPham(sp);
            filteredSanPham = new ArrayList<>(productManager.getDsSanPham());
            updateHangComboBox();
            refreshProductGrid();
            formPanel.setVisible(false);
            clearForm();
            isEditing = false;
            JOptionPane.showMessageDialog(this, "Sửa sản phẩm thành công!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá, số lượng, số camera, dung lượng pin phải là số nguyên, kích thước màn hình phải là số!");
        } catch (IllegalArgumentException | IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private SanPham getSelectedSanPham() {
        for (Component comp : productPanel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel itemPanel = (JPanel) comp;
                if (itemPanel.getBorder() == BorderFactory.createLineBorder(Color.BLUE)) {
                    JLabel lblTen = (JLabel) ((JPanel) itemPanel.getComponent(1)).getComponent(0);
                    return productManager.getDsSanPham().stream()
                            .filter(sp -> sp.getTen().equals(lblTen.getText()))
                            .findFirst()
                            .orElse(null);
                }
            }
        }
        return null;
    }

    private void clearSelection() {
        for (Component comp : productPanel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel itemPanel = (JPanel) comp;
                itemPanel.setBackground(Color.WHITE);
                itemPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 2, Color.decode("#D3A875")),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
            }
        }
    }

    private int tinhSoLuongDaBan(String maSP) {
        try {
            List<HoaDon> dsHoaDon = hoaDonDataManager.loadFromFile("hoadon.dat");
            return dsHoaDon.stream()
                    .filter(hd -> hd.getTenSP().equals(maSP))
                    .mapToInt(HoaDon::getSoLuong)
                    .sum();
        } catch (IOException | ClassNotFoundException e) {
            return 0;
        }
    }

    private void clearForm() {
        txtMaSP.setText("");
        txtTen.setText("");
        txtHang.setText("");
        txtGia.setText("");
        txtSoLuong.setText("");
        txtChip.setText("");
        txtSoCamera.setText("");
        txtDungLuongPin.setText("");
        txtKichThuocManHinh.setText("");
        txtHinhMinhHoa.setText("");
    }
}