package do_an_1;

import java.io.Serializable;

public class KhachHang implements Serializable {
    private String maKH, tenKH, sdt;

    public KhachHang(String maKH, String tenKH, String sdt) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.sdt = sdt;
    }

    public String getMaKH() { return maKH; }
    public String getTenKH() { return tenKH; }
    public String getSdt() { return sdt; }

    public void setMaKH(String maKH) { this.maKH = maKH; }
    public void setTenKH(String tenKH) { this.tenKH = tenKH; }
    public void setSdt(String sdt) { this.sdt = sdt; }
}