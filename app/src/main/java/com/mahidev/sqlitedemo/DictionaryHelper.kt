package com.mahidev.sqlitedemo

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.mahidev.sqlitedemo.models.Meaning
import java.io.File
import java.io.FileOutputStream


const val dbNameDict  = "db.sqlite"
const val dbVersionNumberDict = 1


class DictionaryHelper(private val context: Context) : SQLiteOpenHelper(context, dbNameDict, null, dbVersionNumberDict)
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
        val dbFile = File(context.getDatabasePath(dbNameDict).path)
        return dbFile.exists()
    }

    //copy the database
    private fun copyDatabase(){
        val inputStream = context.assets.open("$dbNameDict")

        val outputFile = File(context.getDatabasePath(dbNameDict).path)
        val outputStream = FileOutputStream(outputFile)

        val bytesCopied = inputStream.copyTo(outputStream)
        Log.e("Mahi", "$bytesCopied")

        inputStream.close()

        outputStream.flush()
        outputStream.close()
    }

    //Open the database with read and write access mode.
    private fun openDatabase(){
        database = SQLiteDatabase.openDatabase(context.getDatabasePath(dbNameDict).path, null, SQLiteDatabase.OPEN_READWRITE)
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

    @SuppressLint("Range")
    fun getMeaning(word: String) : Meaning
    {
        // before you make a query you need to open the database
        openDatabase()

      val cursor =  database?.rawQuery("select word,typ  from eng_w_ml where mid in (select eid from eng_w_e_ml where word like '$word')",null);

        if (cursor != null && cursor.moveToFirst()) {

            val meaning1 = cursor?.getString(0) ?: ""
            //code may be int
        //    val meaning2 = cursor?.getString(1) ?: ""
//            val meaning3 = cursor?.getString(2) ?: ""

            return Meaning(meaning1 = meaning1, meaning2 = "meaning2", meaning3 = "meaning3")

            // Process the data
        } else {
            // Handle case when no data is found
            return Meaning(meaning1 = "no data found", meaning2 =  "no data found", meaning3 =  "no data found")
        }


        // After using a cursor you need to close it, so the system can
        // release all its resources.
      cursor?.close()

        // After using the database you need to close it.
      close()





//        val columns = arrayOf("eid")
//        val selection = "word = ?"
//        val selectionArgs = arrayOf(word)
//
//        System.out.println("hi coumns"+ columns);
//        System.out.println("hi"+ selection);
//        System.out.println("hi"+ selectionArgs);
//




//        select word,typ  from eng_w_ml where mid in (select   eid from eng_w_e_ml where word like
//        SQLiteDatabase sQLiteDatabase = k.getReadableDatabase();
//        StringBuilder stringBuilder1 = new StringBuilder();
//        stringBuilder1.append("select word,typ  from eng_w_ml where mid in (select   eid from eng_w_e_ml where word like '");
//        stringBuilder1.append(paramString.toString().trim().replace("'", ""));
//        stringBuilder1.append("')");
//        Cursor cursor = sQLiteDatabase.rawQuery(stringBuilder1.toString(), null);
//        int j = cursor.getCount();
//        String str4 = "\"</strong></span><br/>";
//        String str3 = "em; text-decoration: none;\"><strong>\"";
//        if (j != 0) {
//            StringBuilder stringBuilder2 = new StringBuilder();
//            stringBuilder2.append(str1);
//            stringBuilder2.append("<span style=\"color:");
//            stringBuilder2.append(str2);
//            stringBuilder2.append("; font-size:");
//            try {
//                stringBuilder2.append(this.i);
//                stringBuilder2.append("em; text-decoration: none;\"><strong>\"");
//                stringBuilder2.append(paramString.trim().toString());
//                stringBuilder2.append("\"</strong></span><br/>");
//                String str = stringBuilder2.toString();
//                str1 = str;
//            } catch (Exception exception) {}
//        } else {
//            String str = str1;
//            str1 = str;
//        }
//    } catch (Exception exception) {}
//    paramString = str1;
//    try {
//    k.close();
//    return paramString;
//} catch (Exception exception) {
//    return paramString;
//}

//    val cursore =
//            database?.query("eng_w_e_ml", columns, selection, selectionArgs, null, null, null)
//        var eid = -1
//
//        if (cursore != null) {
//            if (cursore.moveToFirst()) {
//                eid = cursore.getInt(cursore.getColumnIndex("eid"))
//                System.out.println("hi"+ eid);
//            }
//        }
//
//        System.out.println("eid"+ eid);
//
//        val columns2 = arrayOf("mid")
//        val selection2 = "mid = ?"
//        val selectionArgs2 = arrayOf(eid.toString())
//
//        val cursore2 =
//            database?.query("eng_w_ml", columns2, selection2, selectionArgs2, null, null, null)
        // move to first will move the cursor to the first row.
        cursor?.moveToFirst()


        // now you can get the data from that row
        // all my columns is strings so i'm using "getString(index_of_column)"
        // but you can also use "getInt(index_of_column)" or any other
        // supported type.


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