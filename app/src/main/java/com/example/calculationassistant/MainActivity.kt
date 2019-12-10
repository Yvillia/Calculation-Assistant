package com.example.calculationassistant

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.calculationassistant.ui.main.AlgebraSolver
import com.example.calculationassistant.ui.main.CompletedActivity
import com.example.calculationassistant.ui.main.WolframParser
import okhttp3.*
import java.io.IOException
import org.json.JSONObject
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val algebraBtn = findViewById<Button>(R.id.algebra)
        val derivativeBtn = findViewById<Button>(R.id.derivative)
        val integralBtn = findViewById<Button>(R.id.integral)
        val submit: Button = findViewById(R.id.confirm)
        val input: TextView = findViewById(R.id.input)
        val promptStr = findViewById<TextView>(R.id.prompt)
        val dePrompt = resources.getString(R.string.please_input_the_expression_you_wish_to_differentiate)
        val intPrompt = resources.getString(R.string.please_input_the_expression_you_wish_to_integrate)
        val algePrompt = resources.getString(R.string.please_input_your_algebraic_expression)
        val passIntent = Intent(this, CompletedActivity::class.java)
        var operat = 0

        algebraBtn.setOnClickListener {
            promptStr.text = algePrompt
            operat = 0
        }

        derivativeBtn.setOnClickListener {
            promptStr.text = dePrompt
            operat = 1
        }

        integralBtn.setOnClickListener {
            promptStr.text = intPrompt
            operat = 2
        }

        submit.setOnClickListener {
            inputHandler(operat, input.text.toString(), passIntent)
        }
    }

    private fun handleAPI(wolframAPI: String, passIntent: Intent) {
        val request = Request.Builder().url(wolframAPI).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                passIntent.putExtra("output", e.toString())
                startActivityForResult(passIntent, 0)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val strResponse = response.body!!.string()
                    val json = JSONObject(strResponse).getJSONObject("queryresult")
                    val jsonArr: JSONArray = json.getJSONArray("pods")
                    val answerContainer: JSONObject = jsonArr.getJSONObject(0)
                    val subAnswerContainer: JSONArray = answerContainer.getJSONArray("subpods")
                    val subSubAnswerContainer: JSONObject = subAnswerContainer.getJSONObject(0)
                    val answer: String = subSubAnswerContainer.getString("plaintext")
                    passIntent.putExtra("output", answer)
                    startActivityForResult(passIntent, 0)
                } catch(e: Exception) {
                    passIntent.putExtra("output", e.toString())
                    startActivityForResult(passIntent,0)
                }
            }
        })
    }

    /**
     * Backend to Handle User Input.
     * Function Will Be Completed Later
     */
    private fun inputHandler(oper: Int, input: String, passIntent: Intent) {
        var output: String
        var parse = WolframParser(input)

        try {
            if (oper == 1) {
                handleAPI("https://api.wolframalpha.com/v2/query?input=" +
                "differentiate+".plus(parse.adjustedString).plus(
                "&format=plaintext&output=JSON&appid=QY4H4K-K4Y2WTGGA7"), passIntent)
                passIntent.putExtra("type", 1)
            }
            if (oper == 2) {
                handleAPI("https://api.wolframalpha.com/v2/query?input=" +
                "integrate+".plus(parse.adjustedString).plus(
           "&format=plaintext&output=JSON&appid=QY4H4K-K4Y2WTGGA7"), passIntent)
                passIntent.putExtra("type", 2)

            }

            if (oper == 0) {
                val inputSolve = AlgebraSolver(input)
                output = ""
                for (entry in inputSolve.solutionSet) {
                    output = output.plus("\n".plus(entry).plus("\n"))
                }
                passIntent.putExtra("type", 0)
                passIntent.putExtra("output", output)
                startActivityForResult(passIntent, 0)
            }
        } catch(e : Exception) {
            passIntent.putExtra("output", e.toString()
                .plus(" Oops, Looks Like Your Input was Invalid!\n" +
                        "Please Try to Input it in a Form Similar to Below:" +
                        "\nFor Derivatives and Integrals: 'C1 + C2x + ... + Cnx^n'" +
                        "\nFor Algebra: a + b * c ^ d / e * (f - g) No Unary Operators For Algebra"))
        }
    }

}
