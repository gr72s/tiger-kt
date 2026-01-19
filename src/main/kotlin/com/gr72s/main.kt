package com.gr72s

import org.antlr.v4.gui.TreeViewer
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.io.InputStream

fun main() {
    val result = parse(getTest1())
    println(result)
}

fun parse(filePath: InputStream): Any? {
    val input = CharStreams.fromStream(filePath)
    val lexer = TigerLexer(input)
    val tokens = CommonTokenStream(lexer)
    val parser = TigerParser(tokens).apply {
        isTrace = true
    }

    val tree = parser.program()
    val stringTree = tree.toStringTree(parser)
    val viewer = TreeViewer(parser.ruleNames.toList(), tree)
    viewer.open()
    println(stringTree)
    if (parser.numberOfSyntaxErrors > 0) {
        System.err.println("Syntax errors found! Check console.")
        return null
    }

    val visitor = TigerAstVisitor()
    val any = visitor.visit(tree)
    return any
}