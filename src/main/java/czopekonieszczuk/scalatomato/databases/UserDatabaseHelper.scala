package czopekonieszczuk.scalatomato.databases

import android.content.{ContentValues, Context}
import android.database.sqlite.{SQLiteDatabase, SQLiteOpenHelper}

class UserDatabaseHelper(context: Context) extends SQLiteOpenHelper(context, "users.db", null, 1) {

  override def onCreate(db: SQLiteDatabase) {
    db.execSQL("create table users(" + "id integer primary key autoincrement," +
      "login text unique not null," +
      "password text not null);" +
      "")
  }

  override def onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
  }

  def addUser(user: User) {
    val db = getWritableDatabase
    val values = new ContentValues()
    values.put("login", user.getLogin)
    values.put("password", user.getPassword)
    db.insertOrThrow("users", null, values)
  }

  def deleteUser(id: Long) {
    val db = getWritableDatabase
    val values = Array("" + id)
    db.delete("users", "id=?", values)
  }

  def getAllUsers: java.util.List[User] = {
    val users = new java.util.LinkedList[User]()
    val columns = Array("id", "login", "password")
    val db = getReadableDatabase
    val cursor = db.query("users", columns, null, null, null, null, null)
    while (cursor.moveToNext()) {
      val user = new User("","")
      user.setId(cursor.getLong(0))
      user.setLogin(cursor.getString(1))
      user.setPassword(cursor.getString(2))
      users.add(user)
    }
    users
  }

  def getUserById(id: Long): User = {
    val user = new User("","")
    val db = getReadableDatabase
    val columns = Array("id", "login", "password")
    val args = Array(id + "")
    val cursor = db.query("users", columns, " id=?", args, null, null, null, null)
    if (cursor != null) {
      cursor.moveToFirst()
      user.setId(cursor.getLong(0))
      user.setLogin(cursor.getString(1))
      user.setPassword(cursor.getString(2))
    }
    user
  }

  def loginUser(login: String, password: String): Long = {
    var id: Long = -1
    val db = getReadableDatabase
    val cursor = db.rawQuery("select id from users where login='" + login + "' and password ='" +
      password +
      "'", null)
    while (cursor.moveToNext()) {
      id = cursor.getLong(0)
    }
    id
  }
}
