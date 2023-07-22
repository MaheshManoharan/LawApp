package com.mahidev.sqlitedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity()
{

        lateinit var  dbHelper: DbHelper



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        dbHelper = DbHelper(this)

        val question = dbHelper.getQuestion((0..1280).random())

        Log.e("Mahi", question.fullDesc)





    }

     fun oneLaw(view: View)
    {
        val random = Random(System.currentTimeMillis())
        val randomNumber = random.nextInt(1281) // Generates a random number between 0 and 1280 (inclusive)

        val question = dbHelper.getQuestion(randomNumber)

        val textView = findViewById<TextView>(R.id.textView)

        textView.text = question.toString()



        Log.e("Mahi", question.fullDesc)

    }
}