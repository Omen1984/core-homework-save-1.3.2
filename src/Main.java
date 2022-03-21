import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        GameProgress saveProgressLvl2 = new GameProgress(100, 50, 2, 2.5);
        GameProgress saveProgressLvl10 = new GameProgress(80, 45, 10, 5.0);
        GameProgress saveProgressLvl85 = new GameProgress(1000, 43, 85, 99.0);

        List<String> filePathList = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            filePathList.add("E:/JavaProject/Games/savegames/save" + i + ".dat");
        }

        saveGame(saveProgressLvl2, filePathList.get(0));
        saveGame(saveProgressLvl10, filePathList.get(1));
        saveGame(saveProgressLvl85, filePathList.get(2));
        zipFiles("E:/JavaProject/Games/savegames/zip.zip", filePathList);
    }

    private static void saveGame(GameProgress gp, String thePathOfTheFile) {
        try (FileOutputStream fos = new FileOutputStream(thePathOfTheFile);
             ObjectOutputStream ous = new ObjectOutputStream(fos)) {
            ous.writeObject(gp);
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    private static void zipFiles(String thePathOfTheZip, List<String> filePathList) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(thePathOfTheZip))) {
            for (String thePathOfTheFile : filePathList) {
                FileInputStream fis = new FileInputStream(thePathOfTheFile);
                File file = new File(thePathOfTheFile);
                ZipEntry entry = new ZipEntry(file.getName());
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
                file.delete();
            }
        } catch (IOException ex) {
            ex.getMessage();
        }
    }
}
