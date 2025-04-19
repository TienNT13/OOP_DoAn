package do_an_1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CustomerManager {
    private List<KhachHang> dsKhachHang;
    private DataManager<KhachHang> dataManager;

    public CustomerManager() {
        dataManager = new FileDataManager<>();
        dsKhachHang = new ArrayList<>();
        try {
            dsKhachHang = dataManager.loadFromFile("khachhang.dat");
        } catch (IOException | ClassNotFoundException e) {
            // Xử lý lỗi khi đọc file
        }
    }

    public List<KhachHang> getDsKhachHang() {
        return dsKhachHang;
    }

    public String generateMaKH() {
        int count = dsKhachHang.size() + 1;
        return "NT" + count;
    }

    public void addKhachHang(KhachHang kh) throws IOException {
        dsKhachHang.add(kh);
        dataManager.saveToFile(dsKhachHang, "khachhang.dat");
    }

    public void updateKhachHang(KhachHang kh) throws IOException {
        for (int i = 0; i < dsKhachHang.size(); i++) {
            if (dsKhachHang.get(i).getMaKH().equals(kh.getMaKH())) {
                dsKhachHang.set(i, kh);
                break;
            }
        }
        dataManager.saveToFile(dsKhachHang, "khachhang.dat");
    }

    public void deleteKhachHang(String maKH) throws IOException {
        dsKhachHang.removeIf(kh -> kh.getMaKH().equals(maKH));
        dataManager.saveToFile(dsKhachHang, "khachhang.dat");
    }

    public void sortByMaKH(boolean ascending) {
        dsKhachHang.sort(Comparator.comparing(KhachHang::getMaKH, ascending ? String::compareTo : Comparator.reverseOrder()));
    }
}