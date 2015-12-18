package czopekonieszczuk.scalatomato.databases

import android.content.{ContentValues, Context}
import android.database.sqlite.{SQLiteDatabase, SQLiteOpenHelper}

class TomatoDatabaseHelper(context: Context) extends SQLiteOpenHelper(context, "tomatoes.db", null, 1) {

  override def onCreate(db: SQLiteDatabase) {
    db.execSQL("create table tomatoes(" + "id integer primary key autoincrement," +
      "userId integer not null," +
      "date text not null);" +
      "")
  }

  override def onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
  }

  def addTomato(tomato: Tomato) {
    val db = getWritableDatabase
    val values = new ContentValues()
    values.put("userId", tomato.getUserId.toString)
    values.put("date", tomato.getDate)
    db.insertOrThrow("tomatoes", null, values)
  }

  def deleteTomato(id: Long) {
    val db = getWritableDatabase
    val values = Array("" + id)
    db.delete("tomatoes", "id=?", values)
  }

  def getAllTomatoes: java.util.List[Tomato] = {
    val tomatoes = new java.util.LinkedList[Tomato]()
    val columns = Array("id", "userId", "date")
    val db = getReadableDatabase
    val cursor = db.query("tomatoes", columns, null, null, null, null, null)
    while (cursor.moveToNext()) {
      val tomato = new Tomato(-1,-1)
      tomato.setId(cursor.getLong(0))
      tomato.setUserId(cursor.getLong(1))
      tomato.setDate(cursor.getString(2))
      tomatoes.add(tomato)
    }
    tomatoes
  }

  def getTomato(id: Long): Tomato = {
    val tomato = new Tomato(-1,-1)
    val db = getReadableDatabase
    val columns = Array("id", "userId", "date")
    val args = Array(id + "")
    val cursor = db.query("tomatoes", columns, " id=?", args, null, null, null, null)
    if (cursor != null) {
      cursor.moveToFirst()
      tomato.setId(cursor.getLong(0))
      tomato.setUserId(cursor.getLong(1))
      tomato.setDate(cursor.getString(2))
    }
    tomato
  }

  def getUserTomatoes(userId: Long): java.util.List[Tomato] = {
    val tomatoes = new java.util.LinkedList[Tomato]()
   //val columns = Array("id", "userId", "date")
    val db = getReadableDatabase
    val cursor = db.rawQuery("select * from tomatoes where userId=" + userId, null)
    while (cursor.moveToNext()) {
      val tomato = new Tomato(-1,-1)
      tomato.setId(cursor.getLong(0))
      tomato.setUserId(cursor.getLong(1)) //tomato.setUserId(userId)
      tomato.setDate(cursor.getString(2))
      tomatoes.add(tomato)
    }
    tomatoes

  }

  def loginUser(login: String, password: String): Long = {
    var id = -1
    val db = getReadableDatabase
    val cursor = db.rawQuery("select id from users where login='" + login + "' and password ='" +
      password +
      "'", null)
    while (cursor.moveToNext()) {
      id = cursor.getInt(0)
    }
    id
  }
}
