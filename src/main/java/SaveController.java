import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SaveController {
    public static void saveGame(SaveObject save, String saveName) throws IOException {
        try (FileOutputStream file = new FileOutputStream(saveName + ".rg");
             ObjectOutputStream o = new ObjectOutputStream(file)) {
            if (Paths.get(saveName + ".rg").toFile().exists()) throw new IOException("Save exist");
            o.writeObject(save);
        }
    }

    public static SaveObject loadWorld(String saveName) throws IOException, ClassNotFoundException {
        try (FileInputStream file = new FileInputStream(saveName + ".rg");
             ObjectInputStream oi = new ObjectInputStream(file)) {
            return (SaveObject) oi.readObject();
        }
    }

    public static List<String> getListOfSaves() throws IOException {
        try (Stream<Path> walk = Files.walk(Paths.get(""), 1)) {
            return walk.filter(p -> !Files.isDirectory(p))
                    .map(p -> p.toString().toLowerCase())
                    .filter(f -> f.endsWith(".rg"))
                    .map(s -> s.substring(0, s.length() - 3))
                    .collect(Collectors.toList());
        }
    }
}
