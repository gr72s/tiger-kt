package com.gr72s

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import java.io.File

class TigerParserTest {

    @Test
    fun testAllTestcases() {
        val testcasesDir = File("src/main/resources/testcases")
        if (!testcasesDir.exists() || !testcasesDir.isDirectory) {
            fail<Unit>("Testcases directory not found: \${testcasesDir.absolutePath}")
        }

        val testFiles = testcasesDir.listFiles { _, name ->
            name.startsWith("test") && name.endsWith(".tig")
        }

        if (testFiles.isNullOrEmpty()) {
            fail<Unit>("No test files found in \${testcasesDir.absolutePath}")
        }

        val errors = mutableListOf<String>()

        for (file in testFiles) {
            println("Testing file: ${file.name}")
            try {
                val input = CharStreams.fromPath(file.toPath())
                val lexer = TigerLexer(input)
                val tokens = CommonTokenStream(lexer)
                val parser = TigerParser(tokens)
                
                // Disable error printing to console to keep output clean, 
                // but we might want to see them if debugging. 
                // For now, let's keep default listeners but adding a custom one could be better?
                // The requirement says "check rules are correct", looking at numberOfSyntaxErrors is what main.kt does.

                parser.program()

                if (parser.numberOfSyntaxErrors > 0) {
                    errors.add("File: ${file.name}, Syntax Errors: ${parser.numberOfSyntaxErrors}")
                }

            } catch (e: Exception) {
                errors.add("File: ${file.name}, Exception: ${e.message}")
            }
        }

        if (errors.isNotEmpty()) {
            fail<Unit>("Found syntax errors in the following files:\n" + errors.joinToString("\n"))
        }
    }
}
