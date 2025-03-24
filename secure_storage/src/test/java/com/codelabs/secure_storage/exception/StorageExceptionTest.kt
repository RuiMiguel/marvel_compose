package com.codelabs.secure_storage.exception

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull

internal class StorageExceptionTest {
    @Test
    fun `can be instantiated`() {
        assertNotNull(StorageException.ReadException("read exception"))
        assertNotNull(StorageException.WriteException("write exception"))
    }
}