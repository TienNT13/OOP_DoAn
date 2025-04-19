package do_an_1;

import java.io.Serializable;

// Lớp SanPham biểu diễn thông tin sản phẩm, implements Serializable để hỗ trợ lưu trữ vào file
public class SanPham implements Serializable {
    private String maSP; // Mã sản phẩm duy nhất
    private String ten; // Tên sản phẩm
    private String hang; // Hãng sản xuất
    private double gia; // Giá bán sản phẩm
    private int soLuong; // Số lượng tồn kho
    private String chip; // Loại chip xử lý
    private int soCamera; // Số lượng camera
    private int dungLuongPin; // Dung lượng pin (mAh)
    private double kichThuocManHinh; // Kích thước màn hình (inch)
    private String hinhMinhHoa; // Đường dẫn đến hình ảnh minh họa

    // Constructor khởi tạo sản phẩm với tất cả thuộc tính, trừ hinhMinhHoa được đặt mặc định là chuỗi rỗng
    public SanPham(String maSP, String ten, String hang, double gia, int soLuong, String chip, int soCamera, int dungLuongPin, double kichThuocManHinh) {
        this.maSP = maSP;
        this.ten = ten;
        this.hang = hang;
        this.gia = gia;
        this.soLuong = soLuong;
        this.chip = chip;
        this.soCamera = soCamera;
        this.dungLuongPin = dungLuongPin;
        this.kichThuocManHinh = kichThuocManHinh;
        this.hinhMinhHoa = ""; // Đặt mặc định không có hình minh họa
    }

    // Getters và setters để truy cập và cập nhật các thuộc tính
    // Lấy mã sản phẩm
    public String getMaSP() { return maSP; }
    // Cập nhật mã sản phẩm
    public void setMaSP(String maSP) { this.maSP = maSP; }
    // Lấy tên sản phẩm
    public String getTen() { return ten; }
    // Cập nhật tên sản phẩm
    public void setTen(String ten) { this.ten = ten; }
    // Lấy hãng sản xuất
    public String getHang() { return hang; }
    // Cập nhật hãng sản xuất
    public void setHang(String hang) { this.hang = hang; }
    // Lấy giá sản phẩm
    public double getGia() { return gia; }
    // Cập nhật giá sản phẩm
    public void setGia(double gia) { this.gia = gia; }
    // Lấy số lượng tồn kho
    public int getSoLuong() { return soLuong; }
    // Cập nhật số lượng tồn kho
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    // Lấy loại chip
    public String getChip() { return chip; }
    // Cập nhật loại chip
    public void setChip(String chip) { this.chip = chip; }
    // Lấy số lượng camera
    public int getSoCamera() { return soCamera; }
    // Cập nhật số lượng camera
    public void setSoCamera(int soCamera) { this.soCamera = soCamera; }
    // Lấy dung lượng pin
    public int getDungLuongPin() { return dungLuongPin; }
    // Cập nhật dung lượng pin
    public void setDungLuongPin(int dungLuongPin) { this.dungLuongPin = dungLuongPin; }
    // Lấy kích thước màn hình
    public double getKichThuocManHinh() { return kichThuocManHinh; }
    // Cập nhật kích thước màn hình
    public void setKichThuocManHinh(double kichThuocManHinh) { this.kichThuocManHinh = kichThuocManHinh; }
    // Lấy đường dẫn hình minh họa
    public String getHinhMinhHoa() { return hinhMinhHoa; }
    // Cập nhật đường dẫn hình minh họa
    public void setHinhMinhHoa(String hinhMinhHoa) { this.hinhMinhHoa = hinhMinhHoa; }
}