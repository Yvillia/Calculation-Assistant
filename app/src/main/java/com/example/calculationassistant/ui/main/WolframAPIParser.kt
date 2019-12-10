package com.example.calculationassistant.ui.main

import java.util.*


class WolframParser(userInput: String) {

    var adjustedString = ""
    companion object {
        const val ADD = "%2B"
        const val DIVIDE = "%2F"
        const val EXPONENT = "%5E"
        const val SQUAREBRACKETL = "%5B"
        const val SQUAREBRACKETR = "%5D"
        const val CURLYBRACKETL = "%7B"
        const val CURLYBRACKETR = "%7D"
        private val TEMPCHAR = arrayOf(
            '!',
            '(',
            ')',
            '*',
            '+',
            '-',
            '.',
            '/',
            '[',
            ']',
            '{',
            '}',
            '^',
            '0',
            '1',
            '2',
            '3',
            '4',
            '5',
            '6',
            '7',
            '8',
            '9',
            'A',
            'B',
            'C',
            'D',
            'E',
            'F',
            'G',
            'H',
            'I',
            'J',
            'K',
            'L',
            'M',
            'N',
            'O',
            'P',
            'Q',
            'R',
            'S',
            'T',
            'U',
            'V',
            'W',
            'X',
            'Y',
            'Z',
            'a',
            'b',
            'c',
            'd',
            'e',
            'f',
            'g',
            'h',
            'i',
            'j',
            'k',
            'l',
            'm',
            'n',
            'o',
            'p',
            'q',
            'r',
            's',
            't',
            'u',
            'v',
            'w',
            'x',
            'y',
            'z',
            ' '
        )
        private val VALIDCHARS = ArrayList<Char>()
    }

    init {
        VALIDCHARS.addAll(listOf(*TEMPCHAR))
        adjustedString = userInput.trim { it <= ' ' }
        for (element in adjustedString) {
            require(VALIDCHARS.contains(element))
        }
        adjustedString = adjustedString.replace("+", ADD)
        adjustedString = adjustedString.replace("/", DIVIDE)
        adjustedString = adjustedString.replace("^", EXPONENT)
        adjustedString = adjustedString.replace("[", SQUAREBRACKETL)
        adjustedString = adjustedString.replace("]", SQUAREBRACKETR)
        adjustedString = adjustedString.replace("{", CURLYBRACKETL)
        adjustedString = adjustedString.replace("}", CURLYBRACKETR)
        adjustedString = adjustedString.replace(" ", "+")
    }
}
