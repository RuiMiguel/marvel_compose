package com.example.verygoodcore.secure_storage.exception

sealed class StorageException(val msg: String?) : RuntimeException() {
    class ReadException(message: String?) : StorageException(message)

    class WriteException(message: String?) : StorageException(message)
}