package xin.xiuyuan.file.storage.exception;

import lombok.Getter;

/**
 * 文件存储异常
 *
 * @author xiuyuan
 */
@Getter
public class FileStorageException extends RuntimeException {

    private final String errorCode;

    public FileStorageException(String message) {
        super(message);
        this.errorCode = "FILE_STORAGE_ERROR";
    }

    public FileStorageException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "FILE_STORAGE_ERROR";
    }

    public FileStorageException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
