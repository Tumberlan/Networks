import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class FileLister {
    public static List<Path> listFiles(Path path) throws IOException {
        return Files.walk(path).filter(Files::isRegularFile).collect(Collectors.toList());
    }
}
