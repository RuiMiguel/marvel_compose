package com.example.verygoodcore.marvel_compose.secure_storage.exception

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class StorageExceptionTest {
    @Test
    fun `can be instantiated`() {
        assertThat(StorageException.ReadException("read exception")).isNotNull
        assertThat(StorageException.WriteException("write exception")).isNotNull
    }
}