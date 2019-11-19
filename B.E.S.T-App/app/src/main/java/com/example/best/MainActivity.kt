package com.example.best

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var items : Array<Any> = arrayOf(10, 20, 15, 30, 45)
        var list : LinkedList = object : LinkedList (items) {}
        Toast.makeText(this, list.toPrint(), Toast.LENGTH_LONG).show()
        Log.d(list.toPrint(), list.toPrint())
        var but = findViewById<Button>(R.id.button2).setText(list.toPrint())

    }

}
