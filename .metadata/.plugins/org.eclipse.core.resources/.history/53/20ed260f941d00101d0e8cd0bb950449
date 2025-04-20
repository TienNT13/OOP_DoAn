package do_an_1;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProductManager {
    private List<SanPham> dsSanPham;
    private DataManager<SanPham> dataManager;

    public ProductManager() {
        dataManager = new FileDataManager<>();
        dsSanPham = new ArrayList<>();
        try {
            dsSanPham = dataManager.loadFromFile("sanpham.dat");
        } catch (IOException | ClassNotFoundException e) {
            // Xử lý lỗi khi đọc file
        }
    }

    public List<SanPham> getDsSanPham() {
        return dsSanPham;
    }

    public String generateMaSP() {
        int maxId = 0;
        for (SanPham sp : dsSanPham) {
            String maSP = sp.getMaSP();
            if (maSP.startsWith("M")) {
                try {
                    int id = Integer.parseInt(maSP.substring(1));
                    maxId = Math.max(maxId, id);
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return "M" + (maxId + 1);
    }

    public void addSanPham(SanPham sp) throws IOException {
        SanPham existingProduct = dsSanPham.stream()
                .filter(p -> p.getTen().equalsIgnoreCase(sp.getTen()))
                .findFirst()
                .orElse(null);
        if (existingProduct != null) {
            existingProduct.setSoLuong(existingProduct.getSoLuong() + sp.getSoLuong());
        } else {
            dsSanPham.add(sp);
        }
        dataManager.saveToFile(dsSanPham, "sanpham.dat");
    }

    public void updateSanPham(SanPham updatedSp) throws IOException {
        for (int i = 0; i < dsSanPham.size(); i++) {
            if (dsSanPham.get(i).getMaSP().equals(updatedSp.getMaSP())) {
                dsSanPham.set(i, updatedSp);
                break;
            }
        }
        dataManager.saveToFile(dsSanPham, "sanpham.dat");
    }

    public void deleteSanPham(String maSP) throws IOException {
        dsSanPham.removeIf(sp -> sp.getMaSP().equals(maSP));
        dataManager.saveToFile(dsSanPham, "sanpham.dat");
    }

    public void sortByHang() {
        dsSanPham.sort(Comparator.comparing(SanPham::getHang, String.CASE_INSENSITIVE_ORDER));
    }

    public String formatPrice(double price) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(price) + " VND";
    }
}