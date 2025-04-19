package do_an_1;

import java.time.LocalDate;

public interface Invoice {
    String getMaHD();
    String getMaKH();
    String getTenSP();
    int getSoLuong();
    double getTongTien();
    LocalDate getNgayLap();
}