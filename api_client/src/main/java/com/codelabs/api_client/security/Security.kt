package com.codelabs.api_client.security

import java.math.BigInteger
import java.security.MessageDigest

class Security(private var privateKey: String, var publicKey: String) {
    fun hashTimestamp(): Pair<String, String> {
        val timestamp = System.currentTimeMillis().toString()
        val hash = generateMd5("$timestamp$privateKey$publicKey")
        return Pair(timestamp, hash)
    }

    private fun generateMd5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}