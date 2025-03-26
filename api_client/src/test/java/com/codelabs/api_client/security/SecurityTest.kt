package com.codelabs.api_client.security

import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SecurityTest {
    private lateinit var security: Security

    @BeforeEach
    fun setUp() {
        security = Security()
    }

    @Test
    fun `hashTimestamp returns a pair of timestamps and hashes`() {
        val result =
            security.hashTimestamp(privateKey = "testPrivateKey", publicKey = "testPublicKey")

        assertNotNull(result)
        assertNotNull(result.first)
        assertNotNull(result.second)
        assertEquals(32, result.second.length)
    }

    @Test
    fun `hashTimestamp returns different values for each call`() = runTest {
        val firstResult =
            security.hashTimestamp(privateKey = "testPrivateKey", publicKey = "testPublicKey")
        delay(1000)
        val secondResult =
            security.hashTimestamp(privateKey = "testPrivateKey", publicKey = "testPublicKey")

        assertNotEquals(
            firstResult.first,
            secondResult.first
        )
        assertNotEquals(
            firstResult.second,
            secondResult.second
        )
    }

    @Test
    fun `generateMd5 returns a valid MD5 hash`() {
        val timestamp = "1"
        val privateKey = "abcd"
        val publicKey = "1234"

        val result =
            security.generateMd5("$timestamp$privateKey$publicKey")

        assertEquals("ffd275c5130566a2916217b101f26150", result)
    }
}