package com.example.best

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val submit: Button = findViewById(R.id.submission)
        val input: TextView = findViewById(R.id.expression)
        val passIntent = Intent(this, CompletedActivity::class.java)

        submit.setOnClickListener {
            val passed: String = input.text.toString()
            inputHandler(passed, passIntent)
        }
    }

    /**
     * Backend to Handle User Input
     * Function Will Be Completed Later
     *
     */
    private fun inputHandler(input: String, passIntent: Intent) {
        /***
         * DO SOMETHING WITH input HERE
         */
        passIntent.putExtra("output", input)
        startActivityForResult(passIntent, 0)
    }


}
