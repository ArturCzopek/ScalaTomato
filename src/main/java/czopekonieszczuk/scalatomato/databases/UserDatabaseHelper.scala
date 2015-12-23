package czopekonieszczuk.scalatomato.databases

import android.content.{ContentValues, Context}
import android.database.Cursor
import android.database.sqlite.{SQLiteDatabase, SQLiteOpenHelper}
import android.util.Log

class UserDatabaseHelper(context: Context) extends SQLiteOpenHelper(context, "users.db", null, 1) {

  override def onCreate(db: SQLiteDatabase) {
    db.execSQL("create table users(" + "id integer primary key autoincrement," +
      "login text unique not null," +
      "password text not null);" +
      "")
    Log.d("UserDatabaseHelper.onCreate", "Executed")
  }

  override def onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int): Unit = {
    Log.d("UserDatabaseHelper.onUpgrade", "Executed")

  }

  def addUser(user: User) {
    val db = getWritableDatabase
    val values = new ContentValues()
    values.put("login", user.getLogin)
    values.put("password", user.getPassword)
    Log.d("UserDatabaseHelper.addUser", "Created ContentValues insert values")
    db.insertOrThrow("users", null, values)
    Log.d("UserDatabaseHelper.addUser", "insertOrThrow executed")
  }

  def deleteUser(id: Long) {
    val db = getWritableDatabase
    val values = Array("" + id)
    Log.d("UserDatabaseHelper.deleteUser", "Insert to delete userId: " + id.toString)
    db.delete("users", "id=?", values)
    Log.d("UserDatabaseHelper.deleteUser", "delete executed")
  }

  def getAllUsers: java.util.List[User] = {
    val users = new java.util.LinkedList[User]()
    val columns = Array("id", "login", "password")
    Log.d("UserDatabaseHelper.getAllUser", "Created columns with values to get")
    val db = getReadableDatabase
    val cursor = db.query("users", columns, null, null, null, null, null)
    Log.d("UserDatabaseHelper.getAllUsers", "Executed query and created cursor")
    if (cursor != null && cursor.moveToFirst) {
      Log.d("TomatoDatabaseHelper.getAllTomatoes", "Cursor IS NOT empty")
      do {
        val user = new User("", "")
        user.setId(cursor.getLong(0))
        user.setLogin(cursor.getString(1))
        user.setPassword(cursor.getString(2))
        users.add(user)
        Log.d("UserDatabaseHelper.getAllUsers", "Added user to the list. Id: " + user.id.toString + ", login: " + user.login)
      } while(cursor.moveToNext)
    } else {
      Log.d("UserDatabaseHelper.getAllUsers", "Cursor IS empty")
      val user = new User("Not Found", "")
      users.add(user)
      Log.d("UserDatabaseHelper.getAllUsers", "Added 'NOT FOUND' user to the list")
    }
    cursor.close
    users
  }

  def getUserById(id: Long): User = {
    Log.d("UserDatabaseHelper.getUserById", "id passed: "+ id.toString)
    val user = new User(-1, "Not found", "")
    val db = getReadableDatabase
    val cursor: Cursor = db.rawQuery("Select id, login FROM users WHERE id=" +id.toString, null)
    Log.d("UserDatabaseHelper.getUserById", "Executed query and created cursor")
    if(cursor != null && cursor.moveToFirst) {
      Log.d("UserDatabaseHelper.getUserById", "Cursor IS NOT empty")
      user.id = cursor.getLong(0)
      user.login = cursor.getString(1)
      Log.d("UserDatabaseHelper.getUserById", "User login from cursor: " + user.login)
    } else {
      Log.d("UserDatabaseHelper.getUserById", "Cursor IS empty")
    }
    cursor.close
    user
  }

  def loginUser(login: String, password: String): Long = {
    var id: Long = -1
    val db = getReadableDatabase
    val cursor = db.rawQuery("select id from users where login='" + login + "' and password ='" +
      password +
      "'", null)
    Log.d("UserDatabaseHelper.loginUser", "Executed query and created cursor")
    if (cursor != null && cursor.moveToFirst) {
      Log.d("UserDatabaseHelper.loginUser", "Cursor IS NOT empty")
      id = cursor.getLong(0)
    } else {
      Log.d("UserDatabaseHelper.loginUser", "Cursor IS empty")
    }
    Log.d("UserDatabaseHelper.loginUser",  "Id from cursor: " + id.toString)
    cursor.close
    id
  }
}
