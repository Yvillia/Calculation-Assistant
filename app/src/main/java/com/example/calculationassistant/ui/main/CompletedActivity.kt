package com.example.calculationassistant.ui.main

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.TypedValue
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.calculationassistant.R

class CompletedActivity : AppCompatActivity() {

    /** Called when the activity is first created. */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_return)
        val returnBtn: Button = findViewById(R.id.returnButton)
        val outputView: TextView = findViewById(R.id.returnText)
        outputView.movementMethod = ScrollingMovementMethod()
        val output: String? = intent.getStringExtra("output")
        val decideTextSize : Int = intent.getIntExtra("type", -1)
        if (decideTextSize != 0) {
            outputView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22.toFloat())
            outputView.gravity = Gravity.CENTER_HORIZONTAL
            val eqnHalves = output!!.split("=")
            val outputStr = eqnHalves[0]
                .plus("\n=\n")
                .plus(eqnHalves[1])
            outputView.text = outputStr
        } else {
            outputView.text = output
        }

        returnBtn.setOnClickListener {
            val returnIntent = Intent()
            setResult(RESULT_OK, returnIntent)
            finish()
        }
    }
}