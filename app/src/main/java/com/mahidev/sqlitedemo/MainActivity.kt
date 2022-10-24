package com.mahidev.sqlitedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {

        lateinit var  dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        dbHelper = DbHelper(this)

        val question = dbHelper.getQuestion((0..1280).random())

        Log.e("Mahi", question.fullDesc)






    }

    private fun oneLaw(view: View)
    {
        val question = dbHelper.getQuestion((0..1280).random())

        Log.e("Mahi", question.fullDesc)

    }
}