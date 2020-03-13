package com.javarush.task.task31.task3110;

import com.javarush.task.task31.task3110.exception.PathIsNotFoundException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFileManager {
    private Path zipFile;


    public ZipFileManager(Path zipFile) {
        this.zipFile = zipFile;
    }

    public void createZip(Path source) throws Exception {
        if (!Files.isDirectory(zipFile.getParent())) Files.createDirectory(zipFile.getParent());
        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipFile))) {
            if (Files.isRegularFile(source)) {
                addNewZipEntry(zos, source.getParent(), source.getFileName());
            } else if (Files.isDirectory(source)) {
                FileManager fileManager = new FileManager(source);
                List<Path> fileNames = fileManager.getFileList();
                for (Path path : fileNames) {
                    addNewZipEntry(zos, source, path);
                }
            } else throw new PathIsNotFoundException();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addNewZipEntry(ZipOutputStream zipOutputStream, Path filePath, Path fileName) throws Exception {
        Path path = filePath.resolve(fileName);
        try (InputStream inputStream = Files.newInputStream(path)) {
            //ZipEntry zipEntry = new ZipEntry(fileName.getFileName().toString());
            ZipEntry zipEntry = new ZipEntry(fileName.toString());
            zipOutputStream.putNextEntry(zipEntry);
            copyData(inputStream, zipOutputStream);
            zipOutputStream.closeEntry();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void copyData(InputStream in, OutputStream out) throws Exception {
        int data;
        while ((data = in.read()) != -1) {
            out.write(data);
        }
    }
}
