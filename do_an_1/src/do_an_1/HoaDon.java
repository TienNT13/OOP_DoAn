package do_an_1;

import java.io.Serializable;
import java.time.LocalDate;

public class HoaDon implements Invoice, Serializable {
    private String maHD;
    private String maKH;
    private String maSP;
    private int soLuong;
    private double tongTien;
    private LocalDate ngayLap;

    public HoaDon(String maHD, String maKH, String maSP, int soLuong, double tongTien, LocalDate ngayLap) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
        this.ngayLap = ngayLap;
    }

    @Override
    public String getMaHD() { return maHD; }
    @Override
    public String getMaKH() { return maKH; }
    @Override
    public String getTenSP() { return maSP; }
    @Override
    public int getSoLuong() { return soLuong; }
    @Override
    public double getTongTien() { return tongTien; }
    @Override
    public LocalDate getNgayLap() { return ngayLap; }
}