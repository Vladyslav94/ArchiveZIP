package com.javarush.task.task31.task3110;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private Path rootPath;
    private List<Path> fileList;

    public FileManager(Path path) throws IOException {
        rootPath = path;
        fileList = new ArrayList<>();
        collectFileList(rootPath);
    }

    public List<Path> getFileList() {
        return fileList;
    }

    private void collectFileList(Path path) throws IOException {
        if (Files.isRegularFile(path)) {
            Path p = rootPath.relativize(path);
            fileList.add(p);
        } else if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> d = Files.newDirectoryStream(path)) {
                for (Path entry : d) {
                    collectFileList(entry);
                }
            }

        }
    }
}
