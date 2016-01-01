package czopekonieszczuk.scalatomato.activities

import android.util.Log
import czopekonieszczuk.scalatomato.R
import czopekonieszczuk.scalatomato.databases._
import org.scaloid.common._

class DataActivity extends SActivity {
  onCreate {
    contentView = new SVerticalLayout {
      var userId: Long = -1
      Log.d("UserActivity", "Started userId: " + userId.toString)
      val extras = getIntent.getExtras
      Log.d("UserActivity", "Get extras from LoginActivity")
      if (extras != null) {
        userId = extras.getLong("userId")
        Log.d("UserActivity", "userId from LoginActivity: " + userId.toString)
      }
      STextView("User name").textSize(30 dip).<<.marginBottom(30 dip).>>
      STextView(getUsername(userId)).textSize(20 dip).<<.marginBottom(20 dip).>>
      STextView("Tomatoes").textSize(30 dip).<<.marginBottom(30 dip).>>
      STextView(getTomatoes(userId)).textSize(20 dip).<<.marginBottom(20 dip).>>
      STextView("Last Tomato").textSize(30 dip).<<.marginBottom(30 dip).>>
      STextView(getLastTomato(userId)).textSize(20 dip).<<.marginBottom(20 dip).>>
      Log.d("DataActivity.onCreate", "Created TextViews")
    }.padding(20.dip)
  }

  def getUsername (userId : Long) : String = {
    val udb = new UserDatabaseHelper(this)
    udb.getUserById(userId).login
  }

  def getTomatoes(userId: Long): String = {
    val tdb = new TomatoDatabaseHelper(this)
    tdb.getAmountOfUserTomatoes(userId).toString()
  }

  def getLastTomato(userId: Long) : String = {
    val tdb = new TomatoDatabaseHelper(this)
    tdb.getUserLastTomato(userId)
  }
}