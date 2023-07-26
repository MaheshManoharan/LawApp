package com.mahidev.sqlitedemo

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


class MainActivity : AppCompatActivity()
{

    lateinit var  dbHelper: DbHelper
    lateinit var  dictionaryHelper: DictionaryHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DbHelper(this)
        dictionaryHelper = DictionaryHelper(this)

        val question = dbHelper.getQuestion((0..1280).random())

        val textViewLongDesc = findViewById<TextView>(R.id.textView_long_desc)


        Log.e("Mahi", question.fullDesc)

        createNotificationChannel()
        startAlarm(this)

    }


    fun searchMeaning(view: View)
    {
        val editText = findViewById<EditText>(R.id.editText)
        val meaning = dictionaryHelper.getMeaning(editText.text.toString())

        System.out.println("meaning def: "+meaning.meaning1)
        System.out.println("meaning def2: "+meaning.meaning2)
        System.out.println("meaning def3: "+meaning.meaning3)

        if(meaning.meaning1 != null)
        {
            // Create and show the AlertDialog
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage(meaning.meaning1)
                .setPositiveButton("OK") { _, _ ->
                    // Handle the positive button click here (if needed)
                    // This block is optional if you only want to display a message
                }
                .setNegativeButton("Cancel") { _, _ ->
                    // Handle the negative button click here (if needed)
                    // This block is optional if you only want to display a message
                }

            val alertDialog = dialogBuilder.create()
            alertDialog.show()
        }

    }

     fun oneLaw(view: View)
    {
        val random = Random(System.currentTimeMillis())
        val randomNumber = random.nextInt(1281) // Generates a random number between 0 and 1280 (inclusive)

        val question = dbHelper.getQuestion(randomNumber)

        val textView = findViewById<TextView>(R.id.textView_short_desc)
        val textViewLongDesc = findViewById<TextView>(R.id.textView_long_desc)
        val textViewType = findViewById<TextView>(R.id.textView_type)


        textView.text = question.shortDesc
        textViewLongDesc.text = question.fullDesc
        textViewType.text = question.type

        Log.e("Mahi", question.fullDesc)

    }


    // Create a notification channel in your app's Application or main Activity class.
    // Call this method before creating any notifications.
    // Create a notification channel in your app's Application or main Activity class.
// Call this method before creating any notifications.
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "MyNotificationChannel"
            val descriptionText = "Notification Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun startAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val intervalMillis = 5 * 60 * 1000 // 5 minutes in milliseconds

        // Set the alarm to start at the current time and repeat every 5 minutes
        alarmManager.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime(),
            intervalMillis.toLong(),
            pendingIntent
        )
    }



}