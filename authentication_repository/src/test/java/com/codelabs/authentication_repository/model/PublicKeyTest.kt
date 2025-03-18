package com.codelabs.authentication_repository.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PublicKeyTest {
 @Test
 fun `can be instantiated`() {
  assertThat(PublicKey("publicKey")).isNotNull
  assertThat(PublicKey("")).isNotNull
 }
}