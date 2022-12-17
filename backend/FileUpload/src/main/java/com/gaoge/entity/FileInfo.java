package com.gaoge.entity;

public class FileInfo {
    private String originalName;
    private String currentName;
    private String type;
    private String accessAddress;

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccessAddress() {
        return accessAddress;
    }

    public void setAccessAddress(String accessAddress) {
        this.accessAddress = accessAddress;
    }
}
