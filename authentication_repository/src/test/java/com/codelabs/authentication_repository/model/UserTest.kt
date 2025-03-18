package com.codelabs.authentication_repository.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class UserTest {
 @Test
 fun `can be instantiated`() {
  assertThat(User(privateKey = PrivateKey("privateKey"), publicKey = PublicKey("publicKey"))).isNotNull
  assertThat(User.anonymous()).isNotNull
 }
}