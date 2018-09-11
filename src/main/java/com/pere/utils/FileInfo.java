package com.pere.utils;

class FileInfo {
    private String fileName;
    private String extension;
    private String fileHash;

    FileInfo(String fileName, String extension) {
        this.fileName = fileName;
        this.extension = extension;
    }

    FileInfo(String fileName) {
        this.fileName = fileName;
    }

    void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    String getFileHash() {
        return this.fileHash;
    }

    String getFileName() {
        return this.fileName;
    }

    String getExtension() {
        return this.extension;
    }
}
