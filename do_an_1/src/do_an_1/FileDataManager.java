package do_an_1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileDataManager<T> implements DataManager<T> {
    @Override
    public void saveToFile(List<T> list, String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(list);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> loadFromFile(String fileName) throws IOException, ClassNotFoundException {
        File file = new File(fileName);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<T>) ois.readObject();
        }
    }
}