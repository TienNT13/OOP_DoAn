package do_an_1;

import java.io.Serializable;
import java.time.LocalDate;

public class HoaDon implements Serializable {
    private String maHD;
    private String maKH;
    private String maSP; // Sử dụng maSP thay vì tenSP
    private int soLuong;
    private double tongTien;
    private LocalDate ngayLap; // Thêm ngày lập hóa đơn

    public HoaDon(String maHD, String maKH, String maSP, int soLuong, double tongTien, LocalDate ngayLap) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
        this.ngayLap = ngayLap;
    }

    public String getMaHD() { return maHD; }
    public String getMaKH() { return maKH; }
    public String getTenSP() { return maSP; } // Giữ nguyên để tương thích với code cũ, nhưng thực chất là maSP
    public int getSoLuong() { return soLuong; }
    public double getTongTien() { return tongTien; }
    public LocalDate getNgayLap() { return ngayLap; }
}