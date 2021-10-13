import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Cleaner {

    private String pathToTmpDirectory;

    public Cleaner(String pathToTDirectory) {
        this.pathToTmpDirectory = pathToTDirectory;
    }


    public void clean() throws IOException {

        Path path = Paths.get(pathToTmpDirectory);
        List<Path> paths = FileLister.listFiles(path);
        paths.forEach(x -> {
            File file = new File(pathToTmpDirectory, x.getFileName().toString());
            File newFile = new File(file.getAbsolutePath());
            newFile.delete();
        });
    }
}