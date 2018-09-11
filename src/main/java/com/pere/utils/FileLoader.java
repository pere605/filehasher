package com.pere.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;

class FileLoader {
    private FileLoader() {}

    static Map<File, FileInfo> loadFiles(String directory, String extension) {

        final File currentDirectory = new File(directory != null ? directory : System.getProperty("user.dir"));

        HashMap<File, FileInfo> output = new HashMap<>();

        File[] files = extension != null ? currentDirectory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return extension.equals(getFileInfo(pathname).getExtension());
            }
        }) : currentDirectory.listFiles();

        for (File file : files) {
            output.put(file, getFileInfo(file));
        }

        return output;
    }

    private static FileInfo getFileInfo(File file) {
        String fileName = file.getName();
        if (hasExtension(fileName)) {
            return new FileInfo(
                    fileName.substring(0, fileName.lastIndexOf(".")),
                    fileName.substring(fileName.lastIndexOf(".") + 1)
            );
        }

        return new FileInfo(fileName.substring(0, fileName.lastIndexOf(".")));
    }

    private static boolean hasExtension(String fileName) {
        return fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0;
    }
}
