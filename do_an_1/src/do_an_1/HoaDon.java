package do_an_1;

import java.io.Serializable;
import java.time.LocalDate;

public class HoaDon implements Serializable {
    private String maHD;
    private String maKH;
    private String maSP; // Sử dụng maSP thay vì tenSP
    private int soLuong;
    private double tongTien;
    private LocalDate ngayLap; // Ngày lập hóa đơn

    public HoaDon(String maHD, String maKH, String maSP, int soLuong, double tongTien, LocalDate ngayLap) {
        // Khởi tạo đối tượng HoaDon với các thuộc tính
        this.maHD = maHD;
        this.maKH = maKH;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
        this.ngayLap = ngayLap;
    }

    // Getter cho mã hóa đơn
    public String getMaHD() { return maHD; }
    // Getter cho mã khách hàng
    public String getMaKH() { return maKH; }
    // Getter cho mã sản phẩm (tương thích với tên cũ)
    public String getTenSP() { return maSP; }
    // Getter cho số lượng
    public int getSoLuong() { return soLuong; }
    // Getter cho tổng tiền
    public double getTongTien() { return tongTien; }
    // Getter cho ngày lập
    public LocalDate getNgayLap() { return ngayLap; }
}