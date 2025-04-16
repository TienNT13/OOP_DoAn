package do_an_1;

import java.io.Serializable;

public class SanPham implements Serializable {
    private String maSP;
    private String ten;
    private String hang;
    private double gia;
    private int soLuong;
    private String chip;
    private int soCamera;
    private int dungLuongPin;
    private double kichThuocManHinh;
    private String hinhMinhHoa;

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
        this.hinhMinhHoa = "";
    }

    // Getters và setters
    public String getMaSP() { return maSP; }
    public void setMaSP(String maSP) { this.maSP = maSP; }
    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }
    public String getHang() { return hang; }
    public void setHang(String hang) { this.hang = hang; }
    public double getGia() { return gia; }
    public void setGia(double gia) { this.gia = gia; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public String getChip() { return chip; }
    public void setChip(String chip) { this.chip = chip; }
    public int getSoCamera() { return soCamera; }
    public void setSoCamera(int soCamera) { this.soCamera = soCamera; }
    public int getDungLuongPin() { return dungLuongPin; }
    public void setDungLuongPin(int dungLuongPin) { this.dungLuongPin = dungLuongPin; }
    public double getKichThuocManHinh() { return kichThuocManHinh; }
    public void setKichThuocManHinh(double kichThuocManHinh) { this.kichThuocManHinh = kichThuocManHinh; }
    public String getHinhMinhHoa() { return hinhMinhHoa; }
    public void setHinhMinhHoa(String hinhMinhHoa) { this.hinhMinhHoa = hinhMinhHoa; }
}