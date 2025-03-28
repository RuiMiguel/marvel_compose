package com.codelabs.authentication_repository.model

@JvmInline
value class PrivateKey(val key: String)

@JvmInline
value class PublicKey(val key: String)

data class User(val privateKey: PrivateKey, val publicKey: PublicKey) {
    companion object {
        fun anonymous() = User(
            privateKey = PrivateKey(""),
            publicKey = PublicKey("")
        )
    }

    fun isAnonymous() = privateKey.key.isEmpty() && publicKey.key.isEmpty()
}