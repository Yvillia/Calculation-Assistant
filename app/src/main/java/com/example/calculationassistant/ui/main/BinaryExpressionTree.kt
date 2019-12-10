package com.example.calculationassistant.ui.main

import java.util.*
import kotlin.math.pow
import kotlin.text.StringBuilder

class BinaryExpressionTree(input: LinkedList<String>) {
    private var root: Node? = null
    private var stack = Stack<Node>()
    private var step = 1
    internal var steps = LinkedList<String>()

    init {
        var current: Node
        for (value in input) {
            if (!isOperator(value)) {
                current = Node(value)
                stack.push(current)
            } else {
                current = Node(value)
                current.right = stack.pop()
                current.left = stack.pop()
                stack.push(current)
            }
        }
        root = stack.pop()
    }

    fun preFix(): String {
        val prefix = StringBuilder()
        preFix(root, prefix)
        return prefix.toString()
    }

    private fun preFix(current: Node?, prefix: StringBuilder?) {
        if (current != null) {
            prefix?.append(current.value + " ")
            preFix(current.left, prefix)
            preFix(current.right, prefix)
        }
    }

    fun inFix(): String {
        val infix = StringBuilder()
        inFix(root, infix)
        return infix.toString()
    }

    private fun inFix(current: Node?, infix: StringBuilder?) {
        if (current != null) {
            if (isOperator(current.value.toString())) {
                infix?.append("(")
            }
            inFix(current.left, infix)
            infix?.append(current.value)
            inFix(current.right, infix)
            if (!isOperator(current.value.toString()) && infix.toString().contains("(")) {
                infix?.append(")")
            }
        }
    }

    fun postFix(): String {
        val postfix = StringBuilder()
        postFix(root, postfix)
        return postfix.toString()
    }

    private fun postFix(current: Node?, postfix: StringBuilder?) {
        if (current != null) {
            postFix(current.left, postfix)
            postFix(current.right, postfix)
            postfix?.append(current.value + " ")
        }
    }

    fun evaluate(): Double? {
        //steps.add(inFix())
        return evaluate(root)
    }

    private fun evaluate(current: Node?): Double? {
        if (current != null && !isOperator(current.value)) {
            return current.value?.toDouble()
        } else {
            var result = 0.0
            val leftOperand = evaluate(current?.left)
            val rightOperand = evaluate(current?.right)

            if (current != null) {
                when(current.value) {
                    "+" -> {
                        if (leftOperand != null) {
                            result = leftOperand.plus(rightOperand!!)
                            steps.add("Step $step: $leftOperand + $rightOperand = $result")
                            current.value = result.toString()
                            current.left = null
                            current.right = null
                            steps.add(inFix())
                            step++
                            return result
                        }
                    }
                    "-" -> {
                        if (leftOperand != null) {
                            result = leftOperand.minus(rightOperand!!)
                            steps.add("Step $step: $leftOperand - $rightOperand = $result")
                            current.value = result.toString()
                            current.left = null
                            current.right = null
                            steps.add(inFix())
                            step++
                            return result
                        }
                    }
                    "*" -> {
                        if (leftOperand != null) {
                            result = leftOperand.times(rightOperand!!)
                            steps.add("Step $step: $leftOperand * $rightOperand = $result")
                            current.value = result.toString()
                            current.left = null
                            current.right = null
                            steps.add(inFix())
                            step++
                            return result
                        }
                    }
                    "/" -> {
                        if (leftOperand != null) {
                            result = leftOperand.div(rightOperand!!)
                            steps.add("Step $step: $leftOperand / $rightOperand = $result")
                            current.value = result.toString()
                            current.left = null
                            current.right = null
                            steps.add(inFix())
                            step++
                            return result
                        }
                    }
                    "^" -> {
                        if (leftOperand != null) {
                            result = leftOperand.pow(rightOperand!!)
                            steps.add("Step $step: $leftOperand ^ $rightOperand = $result")
                            current.value = result.toString()
                            current.left = null
                            current.right = null
                            steps.add(inFix())
                            step++
                            return result
                        }
                    }
                    else -> return 0.0
                }
            } else {
                return result
            }
            return result
        }
    }

    private fun isOperator(value: String?): Boolean {
        return value == "+" || value == "-" || value == "*" || value == "/" || value == "^"
    }
}