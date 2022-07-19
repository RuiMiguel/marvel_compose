package com.example.verygoodcore.secure_storage

import org.junit.platform.suite.api.SelectPackages
import org.junit.platform.suite.api.Suite
import org.junit.platform.suite.api.SuiteDisplayName

@SuiteDisplayName("SecureStorage test suite")
@SelectPackages("com.example.verygoodcore.secure_storage", "com.example.verygoodcore.secure_storage.exception")
@Suite
class SecureStorageTestSuite {
}