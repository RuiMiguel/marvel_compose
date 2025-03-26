package com.codelabs.api_client.security

import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

class Security {
    fun hashTimestamp(privateKey: String, publicKey: String): Pair<String, String> {
        val timestamp = System.currentTimeMillis().toString()
        val hash = generateMd5("$timestamp$privateKey$publicKey")
        return Pair(hash, timestamp)
    }

    fun generateMd5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        val magnitude = md.digest(input.toByteArray(StandardCharsets.UTF_8))
        return BigInteger(1, magnitude).toString(16).padStart(32, '0')
    }
}