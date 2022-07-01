package com.huziyun.store.controller.ex;

public class FileUploadIoException extends FileUploadException{
    public FileUploadIoException() {
    }

    public FileUploadIoException(String message) {
        super(message);
    }

    public FileUploadIoException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileUploadIoException(Throwable cause) {
        super(cause);
    }

    public FileUploadIoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
