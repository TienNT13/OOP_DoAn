package do_an_1;

import java.io.Serializable;

public abstract class AbstractProduct implements Serializable {
    protected String maSP;
    protected String ten;
    protected double gia;

    public String getMaSP() { return maSP; }
    public void setMaSP(String maSP) { this.maSP = maSP; }
    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }
    public double getGia() { return gia; }
    public void setGia(double gia) { this.gia = gia; }
}