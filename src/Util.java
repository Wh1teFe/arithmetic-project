import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Util {
    public static void writeFile(String path, List<String> lines) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        }
    }

    public static List<String> readFile(String path) throws IOException {
        return java.nio.file.Files.readAllLines(new File(path).toPath(), StandardCharsets.UTF_8);
    }
}


