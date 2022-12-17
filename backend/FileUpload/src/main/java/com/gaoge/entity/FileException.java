package com.gaoge.entity;

public class FileException {
    private String printException;

    public FileException(String printException) {
        this.printException = printException;
    }

    public String getPrintException() {
        return printException;
    }

    public void setPrintException(String printException) {
        this.printException = printException;
    }
}
