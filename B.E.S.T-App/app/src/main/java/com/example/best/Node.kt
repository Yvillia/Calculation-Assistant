package com.example.best

open class Node (setNext: Node?, setValue: Any?, setLast: Node?) {

    // Preliminary Task for Kotlin Practice
    var next: Node? = setNext
    var value: Any? = setValue
    var previous: Node? = setLast

    fun equals(passed: Node): Boolean {
        if (passed.value != null || this.value != null) {
            return passed.value!! == value
        }
        return false
    }

}