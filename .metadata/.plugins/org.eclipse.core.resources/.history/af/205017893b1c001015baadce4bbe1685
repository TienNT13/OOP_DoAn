package do_an_1;

import java.io.IOException;
import java.util.List;

public interface DataManager<T> {
    void saveToFile(List<T> list, String fileName) throws IOException;
    List<T> loadFromFile(String fileName) throws IOException, ClassNotFoundException;
}