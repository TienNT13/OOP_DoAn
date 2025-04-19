package do_an_1;

import java.io.IOException;
import java.util.List;

public interface DataManager<T> {
    // Lưu danh sách đối tượng vào file
    void saveToFile(List<T> list, String fileName) throws IOException;

    // Đọc danh sách đối tượng từ file
    List<T> loadFromFile(String fileName) throws IOException, ClassNotFoundException;
}