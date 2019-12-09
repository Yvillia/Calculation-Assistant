import java.util.*
import kotlin.collections.ArrayList

fun main() {
    print("Enter input: ")
    var exp = readLine()
    var expList = inputParse(exp)
    var postFix = infixToPostfix(expList)
    println(expList)
    println(postFix)
    val tree = BinaryExpressionTree(postFix)
    println("Prefix: " + tree.preFix())
    println("Infix: " + tree.inFix())
    println("Postfix: " + tree.postFix())
    println("Tree evaluated to: " + tree.evaluate())
    for (entry in tree.steps) {
        println(entry)
    }

}

fun inputParse(input: String?): ArrayList<String> {
    val expList = ArrayList<String>()
    if (input != null) {
        var expArray = input.replace("\\s".toRegex(), "").toCharArray()
        println("Array: " + expArray.contentToString())
        var toAdd = "";
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

fun infixToPostfix(infix: ArrayList<String>): LinkedList<String>{
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
            operators.push(value);
        } else if (value == "(") {
            operators.push(value)
        } else if (value == ")") {
            while (operators.isNotEmpty() && operators.peek() != "(") {
                output.add(operators.pop())
            }
            if (operators.isNotEmpty() && operators.peek() == "(") {
                operators.pop();
            }
        }
    }
    while (operators.isNotEmpty()) {
        output.add(operators.pop())
    }
    return output
}

fun isOperator(value: String?): Boolean {
    return value == "+" || value == "-" || value == "*" || value == "/" || value == "^"
}

fun precedence(value: String): Int {
    return when (value) {
        "+", "-" -> 1
        "*", "/" -> 2
        "^" -> 3
        else -> -1
    }
}