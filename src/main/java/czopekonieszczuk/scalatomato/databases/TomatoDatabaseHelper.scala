package czopekonieszczuk.scalatomato.databases

import java.text.SimpleDateFormat
import java.util.Date

import android.content.{ContentValues, Context}
import android.database.sqlite.{SQLiteDatabase, SQLiteOpenHelper}
import android.util.Log

class TomatoDatabaseHelper(context: Context) extends SQLiteOpenHelper(context, "tomatoes.db", null, 1) {

  override def onCreate(db: SQLiteDatabase) {
    db.execSQL("create table tomatoes(" + "id integer primary key autoincrement," +
      "userId integer not null," +
      "date text not null);" +
      "")
    Log.d("TomatoDatabaseHelper.onCreate", "Executed")
  }

  override def onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int): Unit = {
    Log.d("TomatoDatabaseHelper.onUpgrade", "Executed")
  }

  def addTomato(tomato: Tomato) {
    val db = getWritableDatabase
    val values = new ContentValues
    values.put("userId", tomato.getUserId.toString)
    values.put("date", tomato.getDate)
    Log.d("TomatoDatabaseHelper.addTomato", "Created ContentValues insert values")
    db.insertOrThrow("tomatoes", null, values)
    Log.d("TomatoDatabaseHelper.addTomato", "insertOrThrow executed")
  }

  def deleteTomato(id: Long) {
    val db = getWritableDatabase
    val values = Array("" + id)
    Log.d("TomatoDatabaseHelper.deleteTomato", "Insert to delete tomatoId: " + id.toString)
    db.delete("tomatoes", "id=?", values)
    Log.d("TomatoDatabaseHelper.deleteTomato", "delete executed")
  }

  def getAllTomatoes: java.util.List[Tomato] = {
    val tomatoes = new java.util.LinkedList[Tomato]()
    val columns = Array("id", "userId", "date")
    Log.d("TomatoDatabaseHelper.getAllTomatoes", "Created columns with values to get")
    val db = getReadableDatabase
    val cursor = db.query("tomatoes", columns, null, null, null, null, null)
    Log.d("TomatoDatabaseHelper.getAllTomatoes", "Executed query and created cursor")
    if (cursor != null && cursor.moveToFirst) {
      Log.d("TomatoDatabaseHelper.getAllTomatoes", "Cursor IS NOT empty")
      do {
        val tomato = new Tomato(-1, -1)
        tomato.setId(cursor.getLong(0))
        tomato.setUserId(cursor.getLong(1))
        tomato.setDate(cursor.getString(2))
        tomatoes.add(tomato)
        Log.d("TomatoDatabaseHelper.getAllTomatoes", "Added tomato to the list. Id: " + tomato.id.toString + ", userId: " + tomato.userId.toString + ", date: " + tomato.date.toString)
      } while (cursor.moveToNext())
    } else {
      Log.d("TomatoDatabaseHelper.getAllTomatoes", "Cursor IS empty")
      val tomato = new Tomato(-1, -1)
      tomato.setDate("Not found")
      tomatoes.add(tomato)
      Log.d("TomatoDatabaseHelper.getAllTomatoes", "Added 'NOT FOUND' tomato to the list")
    }
    cursor.close
    tomatoes
  }

  def getTomatoById(id: Long): Tomato = {
    Log.d("TomatoDatabaseHelper.getTomatoById", "id passed: " + id.toString)
    val tomato = new Tomato(-1,-1)
    val db = getReadableDatabase
    val columns = Array("id", "userId", "date")
    val args = Array(id + "")
    val cursor = db.query("tomatoes", columns, " id=?", args, null, null, null, null)
    Log.d("TomatoDatabaseHelper.getTomatoById", "Executed query and created cursor")
    if (cursor != null && cursor.moveToFirst) {
      Log.d("TomatoDatabaseHelper.getTomatoById", "Cursor IS NOT empty")
      tomato.setId(cursor.getLong(0))
      tomato.setUserId(cursor.getLong(1))
      tomato.setDate(cursor.getString(2))
      Log.d("TomatoDatabaseHelper.getTomatoById", "Tomato userId from cursor: " + tomato.userId.toString + ", date: " +tomato.date)
    } else {
      Log.d("TomatoDatabaseHelper.getTomatoById", "Cursor IS empty")
    }
    cursor.close
    tomato
  }


  def getUserTomatoes(userId: Long): java.util.List[Tomato] = {
    val tomatoes = new java.util.LinkedList[Tomato]()
    val db = getReadableDatabase
    val countQuery = "Select * FROM tomatoes where userId = " + userId.toString
    val cursor = db.rawQuery(countQuery , null)
    Log.d("TomatoDatabaseHelper.getUserTomatoes", "Executed query and created cursor")
    if (cursor != null && cursor.moveToFirst) {
      Log.d("TomatoDatabaseHelper.getUserTomatoes", "Cursor IS NOT empty")
       do {
         val tomato = new Tomato(-1,-1)
         tomato.id = cursor.getLong(0)
         tomato.userId = cursor.getLong(1) //tomato.setUserId(userId)
         tomato.date = cursor.getString(2)
         Log.d("TomatoDatabaseHelper.getUserTomatoes", "Tomato from cursor: id" + tomato.id.toString + ", userId: " + tomato.userId.toString + ", date:" + tomato.date)
         tomatoes.add(tomato)
         Log.d("TomatoDatabaseHelper.getUserTomatoes", "Added Tomato into the list")
      } while (cursor.moveToNext)
    } else {
      Log.d("TomatoDatabaseHelper.getUserTomatoes", "Cursor IS empty")
      val tomato = new Tomato(0,0)
      tomato.date = "Not found"
      tomatoes.add(tomato)
      Log.d("TomatoDatabaseHelper.getUserTomatoes", "Added 'Not Found' Tomato")
    }
    cursor.close
    tomatoes
  }

  def getUserLastTomato(userId: Long): String = {
    var lastTomato = "Not found"
    val db = getReadableDatabase
    val countQuery = "SELECT date FROM tomatoes WHERE userId =" + userId.toString + " ORDER BY date DESC LIMIT 1"
    val cursor = db.rawQuery(countQuery , null)
    Log.d("TomatoDatabaseHelper.getUserLastTomato", "Executed query and created cursor")
    if (cursor != null && cursor.moveToFirst) {
      Log.d("TomatoDatabaseHelper.getUserLastTomato", "Cursor IS NOT empty")
      lastTomato = cursor.getString(0)
    } else {
      Log.d("TomatoDatabaseHelper.getUserLastTomato", "Cursor IS empty")
    }
    Log.d("TomatoDatabaseHelper.getUserLastTomato", "Found last tomato: " + lastTomato)
    lastTomato
  }

  def getAmountOfUserTomatoes(userId: Long): Int = {
    val db = getReadableDatabase
    val countQuery = "Select * FROM tomatoes where userId = " + userId.toString
    val cursor = db.rawQuery(countQuery , null)
    Log.d("TomatoDatabaseHelper.getAmountOfUserTomatoes", "Executed query and created cursor")
    val amount: Int = cursor.getCount
    Log.d("TomatoDatabaseHelper.getAmountOfUserTomatoes", "amount:" + amount.toString)
    cursor.close
    amount
  }

  def getAmountOfUserTodayTomatoes(userId: Long): Int = {
    val db = getReadableDatabase
    val date: String = new SimpleDateFormat("yyyy/MM/dd").format(new Date())
    val countQuery = "Select * FROM tomatoes where userId = " +userId.toString+ " and date LIKE '" + date +"%'"
    val cursor = db.rawQuery(countQuery, null)
    Log.d("TomatoDatabaseHelper.getAmountOfUserTodayTomatoes", "Executed query and created cursor")
    val amount: Int = cursor.getCount
    Log.d("TomatoDatabaseHelper.getAmountOfUserTodayTomatoes", "amount:" + amount.toString)
    cursor.close
    amount
  }

}
