package czopekonieszczuk.scalatomato.activities

import android.util.Log
import czopekonieszczuk.scalatomato.databases.UserDatabaseHelper
import org.scaloid.common._

class UserActivity extends SActivity {


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
      STextView("Hello,  " + getUsername(userId)).textSize(30 dip).<<.marginBottom(50 dip).>>
      val Button1 = SButton("User Data").onClick(userData(userId))
      val Button2 = SButton("User Tomatoes").onClick(userTomatoes(userId))
      val Button3 = SButton("Tomato Activity").onClick(startTomatoActivity(userId))
    }.padding(20.dip)
  }

  def getUsername (userId : Long) : String = {
    val udb = new UserDatabaseHelper(this)
    udb.getUserById(userId).login
  }

  def userData (userId : Long) {
    val intent = SIntent[DataActivity]
    intent.putExtra("userId", userId)
    Log.d("UserActivity.userTomatoes", "Put to UserActivity intent userId: " + userId.toString)
    startActivity(intent)
    Log.d("UserActivity.userTomatoes", "Started DataActivity")
  }

  def userTomatoes (userId : Long) {
    val intent = SIntent[UserTomatoesActivity]
    intent.putExtra("userId", userId)
    Log.d("UserActivity.userTomatoes", "Put to UserActivity intent userId: " + userId.toString)
    startActivity(intent)
    Log.d("UserActivity.userTomatoes", "Started UserTomatoesActivity")
  }

  def startTomatoActivity (userId : Long) {
    val intent = SIntent[TomatoActivity]
    intent.putExtra("userId", userId)
    Log.d("UserActivity.startTomatoActivity", "Put to UserActivity intent userId: " + userId.toString)
    startActivity(intent)
    Log.d("UserActivity.startTomatoActivity", "Started TomatoActivity")
  }

}