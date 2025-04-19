package do_an_1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileDataManager<T> implements DataManager<T> {
    @Override
    public void saveToFile(List<T> list, String fileName) throws IOException {
        // Sử dụng ObjectOutputStream để ghi danh sách vào file nhị phân
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(list);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> loadFromFile(String fileName) throws IOException, ClassNotFoundException {
        // Kiểm tra file tồn tại, nếu không trả về danh sách rỗng
        File file = new File(fileName);
        if (!file.exists()) return new ArrayList<>();
        
        // Sử dụng ObjectInputStream để đọc danh sách từ file nhị phân
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<T>) ois.readObject();
        }
    }
}