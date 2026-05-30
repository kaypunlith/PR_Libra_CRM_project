package com.ut.nlSystemAPi.helper;

public class FileUploadResult {
    private String savedFilename;
    private String originalFilename;

    public FileUploadResult(String savedFilename, String originalFilename) {
        this.savedFilename = savedFilename;
        this.originalFilename = originalFilename;
    }

    public String getSavedFilename() {
        return savedFilename;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }
}
