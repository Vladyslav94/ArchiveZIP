package com.javarush.task.task31.task3110;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Archiver {
    public static void main(String[] args) {
        System.out.println("Please, put path to archive");

        String pathName = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            pathName = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert pathName != null;
        Path pathsToArchive = Paths.get(pathName);
        ZipFileManager zipFileManager = new ZipFileManager(pathsToArchive);

        System.out.println("Please, put file name to archive");

        String fileName = null;
        try {
            fileName = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert fileName != null;
        Path pathsToFile = Paths.get(fileName);

        try {
            zipFileManager.createZip(pathsToFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
