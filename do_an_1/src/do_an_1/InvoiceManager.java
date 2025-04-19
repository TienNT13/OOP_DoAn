package do_an_1;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InvoiceManager {
    private List<HoaDon> dsHoaDon;
    private DataManager<HoaDon> dataManager;

    public InvoiceManager() {
        dataManager = new FileDataManager<>();
        dsHoaDon = new ArrayList<>();
        try {
            dsHoaDon = dataManager.loadFromFile("hoadon.dat");
        } catch (IOException | ClassNotFoundException e) {
            // Xử lý lỗi khi đọc file
        }
    }

    public List<HoaDon> getDsHoaDon() {
        return dsHoaDon;
    }

    public String generateMaHD() {
        int maxCount = dsHoaDon.stream()
                .map(HoaDon::getMaHD)
                .filter(maHD -> maHD != null && maHD.startsWith("HD"))
                .map(maHD -> {
                    try {
                        return Integer.parseInt(maHD.substring(2));
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .max(Integer::compare)
                .orElse(0);
        return String.format("HD%03d", maxCount + 1);
    }

    public void addHoaDon(List<HoaDon> hds) throws IOException {
        dsHoaDon.addAll(hds);
        dataManager.saveToFile(dsHoaDon, "hoadon.dat");
    }

    public void updateHoaDon(String maHD, List<HoaDon> updatedHds) throws IOException {
        dsHoaDon.removeIf(hd -> hd.getMaHD().equals(maHD));
        dsHoaDon.addAll(updatedHds);
        dataManager.saveToFile(dsHoaDon, "hoadon.dat");
    }

    public void deleteHoaDon(String maHD) throws IOException {
        dsHoaDon.removeIf(hd -> hd.getMaHD().equals(maHD));
        dataManager.saveToFile(dsHoaDon, "hoadon.dat");
    }

    public List<HoaDon> getHoaDonByMaHD(String maHD) {
        return dsHoaDon.stream()
                .filter(hd -> hd.getMaHD().equals(maHD))
                .collect(Collectors.toList());
    }
}