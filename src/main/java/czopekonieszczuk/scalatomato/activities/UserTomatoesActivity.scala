package czopekonieszczuk.scalatomato.activities

import android.util.Log
import czopekonieszczuk.scalatomato.R
import czopekonieszczuk.scalatomato.databases._
import org.scaloid.common._

class UserTomatoesActivity extends SActivity {

  onCreate {

    var userId: Long = -1
    val extras = getIntent.getExtras
    if(extras != null) {
      userId = extras.getLong("userId")
      Log.d("UserTomatoesActivity", "userId: " +userId.toString)
    } else {
      Log.d("UserTomatoesActivity", "userId: not found")
      finish
      Log.d("UserTomatoesActivity", "finish activity")
    }

    val udb = new UserDatabaseHelper(this)
    val user: User = udb.getUserById(userId)
    Log.d("UserTomatoesActivity", "getUser, id: " + user.id.toString + ", name: " + user.login.toString)
    val AmountOfUserTomatoes: Long = AmountOfTomatoes(userId)
    val AmountOfTomatoesText = R.string.amount_of_tomatoes + ": " + AmountOfUserTomatoes.toString()
    Log.d("UserTomatoesActivity", "Amount of user " + user.login + " tomatoes: " + AmountOfUserTomatoes.toString)
    val ListOfUserTomatoes: java.util.List[Tomato] = UserTomatoes(userId)
    Log.d("UserTomatoesActivity", "Size of list user + " + user.login + "tomatoes list: " + ListOfUserTomatoes.size.toString)
    var nr = 1

    contentView = new SVerticalLayout {
      val TomatoesTextView = STextView(R.string.tomatoes).textSize(30 dip).<<.marginBottom(30 dip).>>
      val AmountOfUserTomatoesTextView = STextView(AmountOfTomatoesText).textSize(20 dip).<<.marginBottom(20 dip).>>
      Log.d("UserTomatoesActivity", "Created two first textviews")
      val UserTomatoes = STextView("").textSize(15 dip)
      for(tomato: Tomato <- ListOfUserTomatoes) {
        Log.d("UserTomatoesActivity", "Tomato id: " + tomato.id.toString + ", userId: " + tomato.userId.toString +",userTomato: " + nr.toString+ ", date: " + tomato.date.toString)
        UserTomatoes.setText(UserTomatoes.getText +"\n" + nr.toString + " : " + tomato.date.toString)
        nr += 1
      }
      Log.d("UserTomatoesActivity", "Created " + (nr-1).toString + " tomatoes on the list")

    }.padding(20 dip)
  }

  def AmountOfTomatoes(userId: Long): Long = {
      val tdb = new TomatoDatabaseHelper(this)
      tdb.getAmountOfUserTomatoes(userId)
  }

  def UserTomatoes(userId: Long): java.util.List[Tomato] = {
    val tdb = new TomatoDatabaseHelper(this)
    tdb.getUserTomatoes(userId)
  }
}