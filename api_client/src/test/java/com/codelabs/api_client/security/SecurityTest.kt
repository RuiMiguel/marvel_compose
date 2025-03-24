package com.codelabs.api_client.security

import kotlinx.coroutines.delay
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlinx.coroutines.test.runTest


internal class SecurityTest {
    private lateinit var security: Security

    @BeforeEach
    fun setUp() {
        security = Security(privateKey = "testPrivateKey", publicKey = "testPublicKey")
    }

    @Test
    fun `hashTimestamp returns a pair of timestamps and hashes`() {
        val result = security.hashTimestamp()

        assertNotNull(result)
        assertNotNull(result.first)
        assertNotNull(result.second)
        assertEquals(32, result.second.length)
    }

    @Test
     fun `hashTimestamp returns different values for each call`() = runTest {
        val firstResult = security.hashTimestamp()
        delay(1000)
        val secondResult = security.hashTimestamp()

        assertNotEquals(
            firstResult.first,
            secondResult.first
        )
        assertNotEquals(
            firstResult.second,
            secondResult.second
        )
    }
}