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
import org.apache.commons.math3.analysis.differentiation.*
import org.apache.commons.math3.analysis.integration.*

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
        var chosenDiff = "x"

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
                chosenDiff = letters[position]
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
            inputHandler(mathSymAsStr, chosenDiff, input.text.toString(), passIntent)
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
     * Function Will Be Completed Later
     */
    private fun inputHandler(mathSymAsStr: String, differential: String, input: String?, passIntent: Intent) {
        var output: String = "Failure"

        try {
            if (mathSymAsStr == "DIFF") {
                val pars = CalcParser(input)
                val sym = CalcSymbol("x")
                val func = CalcFunction(sym, pars.parse())
                val derive = CalcDIFF()
                output = "Differentiated"
            }

            if (mathSymAsStr == "INT") {
                output = "Integrated"
            }

            else {
                //MATS TREE LOGIC GOES HERE
            }

            passIntent.putExtra("output", output)
        } catch(e : Exception) {
            passIntent.putExtra("output", e.toString()
                .plus(" Oopsies, Looks Like Your Input was Invalid! " +
                        "Please Try to Input it in a Form " +
                        "Similar to 'C1 + C2x + C3x^2 + ... + Cnx^n'"))
        }
        startActivityForResult(passIntent, 0)
    }

}
