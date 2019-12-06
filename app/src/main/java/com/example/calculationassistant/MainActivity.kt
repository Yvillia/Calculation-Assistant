package com.example.calculationassistant

import android.content.Intent
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.example.calculationassistant.ui.main.CompletedActivity
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tabs: TabLayout = findViewById(R.id.tabs)
        val submit: Button = findViewById(R.id.confirm)
        val input: TextView = findViewById(R.id.input)
        val passIntent = Intent(this, CompletedActivity::class.java)
        val outputted = handleAPI("http://api.wolframalpha.com/v1/")


        submit.setOnClickListener {
            //val passed: String = input.text.toString()
            inputHandler(outputted + "Here", passIntent)
        }
    }

    private fun handleAPI(wolframAPI: String) : String? {
        val request = Request.Builder().url(wolframAPI).build()
        var returnString: String? = ""

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                returnString = e.toString()
            }
            override fun onResponse(call: Call, response: Response) {
                returnString = response.body()?.string()
            }
        })
        return returnString
    }

    /**
     * Backend to Handle User Input.
     * Function Will Be Completed Later.
     *
     */
    private fun inputHandler(input: String?, passIntent: Intent) {
        /***
         * DO SOMETHING WITH input HERE
         */
        passIntent.putExtra("output", input)
        startActivityForResult(passIntent, 0)
    }

}
