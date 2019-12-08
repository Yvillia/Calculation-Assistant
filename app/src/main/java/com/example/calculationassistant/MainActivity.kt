package com.example.calculationassistant

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.calculationassistant.ui.main.CompletedActivity
import okhttp3.*
import java.io.IOException
import javacalculus.evaluator.CalcINT
import javacalculus.evaluator.CalcDIFF
import javacalculus.core.CalcParser
import javacalculus.struct.CalcFunction
import javacalculus.struct.CalcObject
import javacalculus.struct.CalcSymbol
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val dropDownSpin = findViewById<Spinner>(R.id.dropDown)
        val optionalStr = findViewById<TextView>(R.id.optional)
        val algebraBtn = findViewById<Button>(R.id.algebra)
        val derivativeBtn = findViewById<Button>(R.id.derivative)
        val integralBtn = findViewById<Button>(R.id.integral)
        val submit: Button = findViewById(R.id.confirm)
        val input: TextView = findViewById(R.id.input)
        val promptStr = findViewById<TextView>(R.id.prompt)
        val deStr = resources.getString(R.string.derivate_with_respect_to)
        val intStr = resources.getString(R.string.integrate_with_respect_to)
        val dePrompt = resources.getString(R.string.please_input_the_expression_you_wish_to_differentiate)
        val intPrompt = resources.getString(R.string.please_input_the_expression_you_wish_to_integrate)
        val algePrompt = resources.getString(R.string.please_input_your_algebraic_expression)
        val passIntent = Intent(this, CompletedActivity::class.java)
        val letters = resources.getStringArray(R.array.letter_array)
        var mathSymAsStr = "Algebra"
        var chosenRand = "x"

        ArrayAdapter.createFromResource(
            this,
            R.array.letter_array,
            android.R.layout.simple_spinner_item
        ).also { build ->
            build.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            dropDownSpin.adapter = build
        }

        dropDownSpin.visibility = View.GONE

        dropDownSpin.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int,
                                        id: Long) {
                chosenRand = letters[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        //val outputted = handleAPI("http://api.wolframalpha.com/v1/")

        algebraBtn.setOnClickListener {
            dropDownSpin.visibility = View.GONE
            optionalStr.visibility = View.GONE
            promptStr.text = algePrompt
            mathSymAsStr = "Algebra"
        }

        derivativeBtn.setOnClickListener {
            dropDownSpin.visibility = View.VISIBLE
            optionalStr.text = deStr
            optionalStr.visibility = View.VISIBLE
            promptStr.text = dePrompt
            mathSymAsStr = "DIFF"
        }

        integralBtn.setOnClickListener {
            dropDownSpin.visibility = View.VISIBLE
            optionalStr.text = intStr
            optionalStr.visibility = View.VISIBLE
            promptStr.text = intPrompt
            mathSymAsStr = "INT"
        }

        submit.setOnClickListener {
            try {
                if (mathSymAsStr == "DIFF") {
                    val passed: String = input.text.toString()
                    val pars = CalcParser(passed)
                    val sym = CalcSymbol("x")
                    val f = CalcFunction(sym, pars.parse())
                    val derive = CalcDIFF()
                    inputHandler(
                        derive.differentiate(derive.evaluate(f), sym).toString(),
                        passIntent
                    )
                }
            } catch(e : Exception) {
                inputHandler(e.toString()
                    .plus(" Oopsies, Looks Like Your Input was Invalid! " +
                            "Please Try to Input it in a Form " +
                            "Similar to 'C1 + C2x + C3x^2 + ... + Cnx^n'")
                            , passIntent)
            }
        }
    }
//
//    private fun handleAPI(wolframAPI: String) : String? {
//        val request = Request.Builder().url(wolframAPI).build()
//        var returnString: String? = ""
//
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                returnString = e.toString()
//            }
//            override fun onResponse(call: Call, response: Response) {
//                returnString = response.body()?.toString()
//            }
//        })
//        return returnString
//    }

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
