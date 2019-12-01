package com.example.best

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val submit: Button = findViewById(R.id.submission)
        val input: TextView = findViewById(R.id.expression)

        submit.setOnClickListener {
            val passed: String = input.text.toString()
            inputHandler(passed)
        }
    }

    /**
     * Backend to Handle User Input
     * Function Will Be Completed Later
     *
     */
    fun inputHandler(input: String) : String {
        return input
    }

    /**
     * Once again a Backend Function
     * Will Return an Output and Place it Inside TextView in activity_return.xml
     */
    fun toReturn(output: String) {

    }

    /**
     * Create a Return Button to Allow Users to Start Over Again
     */

}
