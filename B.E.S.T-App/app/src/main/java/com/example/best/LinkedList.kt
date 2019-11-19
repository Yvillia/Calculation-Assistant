package com.example.best

open class LinkedList {

    private var head: Node? = null
    private var tail: Node? = null
    private var size: Int = 0

    constructor(givens: Array<Any>) {
        if (givens.isEmpty())
            head = object : Node(null, null, null) {}
        else {
            var counter = 0
            var holder: Node? = null
            for (item: Any in givens) {
                if (counter == 0) {
                    head = object : Node(null, item, null) {}
                    holder = head
                    size++
                    counter++
                } else {
                    size++
                    head?.next = object : Node(null, item, head) {}
                    head = head?.next
                }
            }
            tail = head
            head = holder
        }
    }

    fun toPrint(): String {
        var holder = head
        var toReturn = ""
        for (i in 0..size) {
            toReturn + " " + holder?.value
            holder = holder?.next
        }
        return toReturn
    }
}