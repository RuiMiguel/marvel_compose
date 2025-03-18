package com.codelabs.authentication_repository

import org.junit.platform.suite.api.SelectPackages
import org.junit.platform.suite.api.Suite
import org.junit.platform.suite.api.SuiteDisplayName

@SuiteDisplayName("AuthenticationRepository test suite")
@SelectPackages("com.codelabs.authentication_repository", "com.codelabs.authentication_repository.model")
@Suite
class AuthenticationRepositoryTestSuite {
}