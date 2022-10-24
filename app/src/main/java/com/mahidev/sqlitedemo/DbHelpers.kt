package com.mahidev.sqlitedemo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.File
import java.io.FileOutputStream


const val dbName  = "laws.db"
const val dbVersionNumber = 1


class DbHelper(private val context: Context) : SQLiteOpenHelper(context, dbName, null, dbVersionNumber)
{
    private var database: SQLiteDatabase? = null

    init{
        //check if the database already copied to the device.
        val dbExist = checkDatabase()

        if(dbExist)
        {
            //if already copied then don't do anything
            Log.e("-----", "Database exist")
        }
        else
        {
            //else copy the database to the device.
            Log.e("-----", "Database doesn't exist")
            createDatabase()
        }
    }

    override fun onCreate(p0: SQLiteDatabase?)
    {
        // if you want to do anything after the database created
        // like inserting default values you can do it here.
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        // if you want to do anything after the database upgraded
        // like migrating values you can do it here.
    }

    //copy the database
    private fun createDatabase(){
        copyDatabase()
    }

    //check if the database already copied to the device.
    private fun checkDatabase(): Boolean{
        val dbFile = File(context.getDatabasePath(dbName).path)
        return dbFile.exists()
    }

    //copy the database
    private fun copyDatabase(){
        val inputStream = context.assets.open("$dbName")

        val outputFile = File(context.getDatabasePath(dbName).path)
        val outputStream = FileOutputStream(outputFile)

        val bytesCopied = inputStream.copyTo(outputStream)
        Log.e("Mahi", "$bytesCopied")

        inputStream.close()

        outputStream.flush()
        outputStream.close()
    }

    //Open the database with read and write access mode.
    private fun openDatabase(){
        database = SQLiteDatabase.openDatabase(context.getDatabasePath(dbName).path, null, SQLiteDatabase.OPEN_READWRITE)
    }

    override fun close() {
        database?.close()
        super.close()
    }
// An example of how you can make a query to get one item.
    // I have a database with one table called "sections"
    // I'm selecting one item at an index I'm passing to the
    // method.
    // My table has 6 columns: Type, Code, Subcode, Short_Desc, Full_Desc, Path

    fun getQuestion(index: Int) : Question
    {
        // before you make a query you need to open the database
        openDatabase()

        // Now you can make a query.
        val cursor = database?.rawQuery("select * from sections limit 1 offset $index", null)

        // move to first will move the cursor to the first row.
        cursor?.moveToFirst()


        // now you can get the data from that row
        // all my columns is strings so i'm using "getString(index_of_column)"
        // but you can also use "getInt(index_of_column)" or any other
        // supported type.
        val type = cursor?.getString(0) ?: ""
        //code may be int
        val code = cursor?.getString(1) ?: ""
        val subcode = cursor?.getString(2) ?: ""
        val shortdesc = cursor?.getString(3) ?: ""
        val fulldesc = cursor?.getString(4) ?: ""
        val path = cursor?.getString(5) ?: ""


        // After using a cursor you need to close it, so the system can
        // release all its resources.
        cursor?.close()

        // After using the database you need to close it.
        close()

        return Question(type, code, subcode, shortdesc, fulldesc, path)

    }

        fun getAllQuestions(): MutableList<Question>{
            //before you make a query you need to open the database
            openDatabase()

            //Now you can make a query
            val cursor = database?.rawQuery("Select * from sections", null)

            //move to first will move the cursor to the first row.
            cursor?.moveToFirst()

            val array = mutableListOf<Question>()

            do
            {
                // now you can get the data from that row
                // all my columns is strings so i'm using "getString(index_of_column)"
                // but you can also use "getInt(index_of_column)" or any other
                // supported type.
                val type = cursor?.getString(0) ?: ""
                //code may be int
                val code = cursor?.getString(1) ?: ""
                val subcode = cursor?.getString(2) ?: ""
                val shortdesc = cursor?.getString(3) ?: ""
                val fulldesc = cursor?.getString(4) ?: ""
                val path = cursor?.getString(5) ?: ""

                array.add(Question(type, code, subcode, shortdesc, fulldesc, path))


            }
                while(cursor?.moveToNext() == true)

                //after using cursor you need to close it, so the system can
                //release all its resources

                cursor?.close()

            //after using the database you need to close it
            close()

            return array

        }
}