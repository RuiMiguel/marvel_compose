package com.example.verygoodcore.authentication_repository

import org.junit.platform.suite.api.SelectPackages
import org.junit.platform.suite.api.Suite
import org.junit.platform.suite.api.SuiteDisplayName

@SuiteDisplayName("AuthenticationRepository test suite")
@SelectPackages("com.example.verygoodcore.authentication_repository", "com.example.verygoodcore.authentication_repository.model")
@Suite
class AuthenticationRepositoryTestSuite {
}