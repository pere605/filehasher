package com.pere.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.Map;

class FileHasher {
    private FileHasher() {}

    static void hashFiles(Map<File, FileInfo> files) throws Exception {
        final MessageDigest messageDigest = MessageDigest.getInstance("SHA1");

        for (Map.Entry<File, FileInfo> entry : files.entrySet()) {
            if (!entry.getKey().isFile()) {
                continue;
            }

            try (InputStream is = new BufferedInputStream(new FileInputStream(entry.getKey()))) {
                final byte[] buffer = new byte[1024];
                for (int read = 0; (read = is.read(buffer)) != -1;) {
                    messageDigest.update(buffer, 0, read);
                }
            }

            try (Formatter formatter = new Formatter()) {
                for (final byte b : messageDigest.digest()) {
                    formatter.format("%02x", b);
                }
                entry.getValue().setFileHash(formatter.toString());
            }
        }
    }

    static void hashFiles(Map<File, FileInfo> files, String outputDirectory) throws Exception {
        hashFiles(files);

        files.forEach((file, fileInfo) -> {
            Path directory = Paths.get(outputDirectory);
            if (!directory.toFile().exists()) {
                try {
                    Files.createDirectory(directory);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Path copy = Paths.get(String.format(
                    "%s%s%s_%s.%s",
                    directory.toString(),
                    File.separatorChar,
                    fileInfo.getFileName(),
                    fileInfo.getFileHash(),
                    fileInfo.getExtension()
            ));

            try {
                Files.copy(file.toPath(), copy, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
