package czopekonieszczuk.scalatomato.databases

import java.text.SimpleDateFormat
import java.util.Date

import android.content.Context
import com.j256.ormlite.stmt.{Where, PreparedQuery, QueryBuilder}
import scala.collection.JavaConversions._

object UserRepository {

  def getAllUser(context: Context): java.util.List[User] = {
    val dao = DatabaseHelper.getInstance(context).getUserDao()
    dao.queryForAll()
  }

  def getUserById(context: Context, id: Int): User = {
    val dao = DatabaseHelper.getInstance(context).getUserDao()
    dao.queryForId(id)
  }

  def addUser(context: Context, user: User) {
    val dao = DatabaseHelper.getInstance(context).getUserDao()
    dao.create(user)
  }

  def logIn(context: Context, login: String, password: String): Long = {
    val dao = DatabaseHelper.getInstance(context).getUserDao()
    val user: User = dao.queryForFirst(dao.queryBuilder().where().eq("login", login).and().eq("password",password).prepare())
    if(user != null) user.getId else 0
  }
}