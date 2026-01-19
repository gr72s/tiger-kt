package com.gr72s

import java.io.FileNotFoundException
import java.io.InputStream
import java.net.URL

private fun getFileURL(name: String): URL {
    return object {}.javaClass.classLoader.getResource(name)
        ?: throw FileNotFoundException(name)
}

private fun getFile(name: String): InputStream {
    return getFileURL(name).openStream()
}

fun getTest1(): InputStream {
    return getFile("testcases/test1.tig")
}

fun getTest2(): InputStream {
    return getFile("testcases/test2.tig")
}