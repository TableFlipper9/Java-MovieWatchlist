package model.repository;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileSaver {

    private static final FileSaver instance = new FileSaver();

    private FileSaver() {}

    public static FileSaver getInstance() {
        return instance;
    }

    public void saveToFile(String content, String extension) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(extension.toUpperCase() + " files", "*." + extension)
        );

        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}