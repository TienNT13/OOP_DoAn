package do_an_1;

import java.io.Serializable;

public class KhachHang implements Customer, Serializable {
    private String maKH, tenKH, sdt;

    public KhachHang(String maKH, String tenKH, String sdt) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.sdt = sdt;
    }

    @Override
    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }
    @Override
    public String getTenKH() { return tenKH; }
    public void setTenKH(String tenKH) { this.tenKH = tenKH; }
    @Override
    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }
}