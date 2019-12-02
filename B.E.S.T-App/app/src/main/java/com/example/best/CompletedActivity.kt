package com.example.best

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CompletedActivity : AppCompatActivity() {

    /** Called when the activity is first created. */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val returnBtn: Button = findViewById(R.id.returnButton)
        val outputView: TextView = findViewById(R.id.returnText)
        val output: String? = intent.getStringExtra("output")

        outputView.text = output

        returnBtn.setOnClickListener {
            val returnIntent = Intent()
            setResult(RESULT_OK, returnIntent)
            finish()
        }
    }
}