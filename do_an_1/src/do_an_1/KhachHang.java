package do_an_1;

import java.io.Serializable;

public class KhachHang implements Serializable {
    private String maKH, tenKH, sdt;

    public KhachHang(String maKH, String tenKH, String sdt) {
        // Khởi tạo đối tượng KhachHang với các thuộc tính
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.sdt = sdt;
    }

    // Getter và setter cho mã khách hàng
    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }
    // Getter và setter cho tên khách hàng
    public String getTenKH() { return tenKH; }
    public void setTenKH(String tenKH) { this.tenKH = tenKH; }
    // Getter và setter cho số điện thoại
    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }
}