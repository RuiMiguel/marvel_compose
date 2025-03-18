package com.codelabs.secure_storage.exception

sealed class StorageException(msg: String?) : RuntimeException(msg) {
    class ReadException(message: String?) : StorageException(message)

    class WriteException(message: String?) : StorageException(message)
}