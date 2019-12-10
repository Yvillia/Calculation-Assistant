package com.example.calculationassistant.ui.main

import java.util.*
import kotlin.collections.ArrayList

class AlgebraSolver () {
    var input: String = ""
    var expList = ArrayList<String>()
    var postFix = LinkedList<String>()
    var solutionSet = LinkedList<String>()

    constructor(setInput: String) : this() {
        input = setInput
        expList = inputParse(input)
        postFix = infixToPostfix(expList)
        val tree = BinaryExpressionTree(postFix)
        solutionSet.add("Entered Expression: $input")
        solutionSet.add("Prefix Notation: " + tree.preFix())
        solutionSet.add("Infix Notation: " + tree.inFix())
        solutionSet.add("Postfix Notation: " + tree.postFix())
        solutionSet.add("Expression Evaluated To: " + tree.evaluate())
        solutionSet.add("Solution to Steps (With Simplified Forms):")
        solutionSet.addAll(tree.steps)
    }

    private fun inputParse(input: String?): ArrayList<String> {
        val expList = ArrayList<String>()
        if (input != null) {
            var expArray = input.replace("\\s".toRegex(), "").toCharArray()
            var toAdd = ""
            for (item in expArray) {
                if (item.isDigit()) {
                    toAdd += "" + item
                } else {
                    if (toAdd.isNotEmpty()) {
                        expList.add(toAdd)
                    }
                    toAdd = "" + item
                    expList.add(toAdd)
                    toAdd = ""
                }
            }
            expList.add(toAdd)
        } else {
            println("Null expression!")
        }
        return expList
    }

    private fun infixToPostfix(infix: ArrayList<String>): LinkedList<String> {
        val output = LinkedList<String>()
        val operators = Stack<String>()
        for (value in infix) {
            if (value.toIntOrNull() != null) {
                output.add(value)
            } else if (isOperator(value)) {
                while ((!operators.empty())
                    && ((precedence(operators.peek()) > precedence(value)
                            || (precedence(operators.peek()) == precedence(value)))
                            && operators.peek() != "(")) {
                    output.add(operators.pop())
                }
                operators.push(value)
            } else if (value == "(") {
                operators.push(value)
            } else if (value == ")") {
                while (operators.isNotEmpty() && operators.peek() != "(") {
                    output.add(operators.pop())
                }
                if (operators.isNotEmpty() && operators.peek() == "(") {
                    operators.pop()
                }
            }
        }
        while (operators.isNotEmpty()) {
            output.add(operators.pop())
        }
        return output
    }

    private fun isOperator(value: String?): Boolean {
        return value == "+" || value == "-" || value == "*" || value == "/" || value == "^"
    }

    private fun precedence(value: String): Int {
        return when (value) {
            "+", "-" -> 1
            "*", "/" -> 2
            "^" -> 3
            else -> -1
        }
    }
}