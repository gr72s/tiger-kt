package com.gr72s

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.io.File
import java.util.stream.Stream

class TigerParserTest {

    companion object {
        @JvmStatic
        fun provideTestFiles(): Stream<Array<Any>> {
            val testcasesDir = File("src/main/resources/testcases")
            if (!testcasesDir.exists() || !testcasesDir.isDirectory) {
                fail<Unit>("Testcases directory not found: ${testcasesDir.absolutePath}")
            }

            val excludedFiles = setOf("merge.tig", "queens.tig", "std.tig")

            return testcasesDir.listFiles { _, name ->
                name.endsWith(".tig") && !excludedFiles.contains(name)
            }?.map { file ->
                val content = file.readText()
                val expectError = content.contains("syntax error", ignoreCase = true)
                arrayOf<Any>(file, expectError)
            }?.stream() ?: Stream.empty()
        }
    }

    @ParameterizedTest
    @MethodSource("provideTestFiles")
    fun testTigerFile(file: File, expectError: Boolean) {
        println("Testing file: ${file.name}, Expect Error: $expectError")
        try {
            val input = CharStreams.fromPath(file.toPath())
            val lexer = TigerLexer(input)
            val tokens = CommonTokenStream(lexer)
            val parser = TigerParser(tokens)

            parser.program()

            val errors = parser.numberOfSyntaxErrors

            if (expectError) {
                assertTrue(errors > 0, "Expected syntax errors in ${file.name} but found none.")
            } else {
                assertEquals(0, errors, "Expected 0 syntax errors in ${file.name} but found $errors.")
            }

        } catch (e: Exception) {
            fail("Exception processing ${file.name}: ${e.message}")
        }
    }
}
