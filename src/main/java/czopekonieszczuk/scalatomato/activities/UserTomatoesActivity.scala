package czopekonieszczuk.scalatomato.activities

import android.util.Log
import czopekonieszczuk.scalatomato.R
import czopekonieszczuk.scalatomato.databases._
import org.scaloid.common._

import scala.collection.JavaConversions._

class UserTomatoesActivity extends SActivity {

  onCreate {

    var userId: Long = -1
    val extras = getIntent.getExtras
    if(extras != null) {
      userId = extras.getLong("userId")
      Log.d("UserTomatoesActivity.onCreate", "userId: " +userId.toString)
    } else {
      Log.d("UserTomatoesActivity.onCreate", "userId: not found")
      finish
      Log.d("UserTomatoesActivity.onCreate", "Finished activity")
    }

    val udb = new UserDatabaseHelper(this)
    val user: User = udb.getUserById(userId)
    Log.d("UserTomatoesActivity.onCreate", "Got user from db, id: " + user.id.toString + ", name: " + user.login.toString)
    val amountOfUserTomatoes = amountOfTomatoes(userId)
    Log.d("UserTomatoesActivity.onCreate", "Amount of user " + user.login + " tomatoes: " + amountOfUserTomatoes.toString)
    val listOfUserTomatoes: java.util.List[Tomato] = userTomatoes(userId)
    Log.d("UserTomatoesActivity.onCreate", "Size of list user " + user.login + " tomatoes list: " + listOfUserTomatoes.size.toString)
    var nr = 1

    contentView = new SScrollView {
      this += new SVerticalLayout {
        val tomatoesTextView = STextView("Hello, " + user.login.toString).textSize(45 dip).<<.marginBottom(30 dip).>>
        val amountOfUserTomatoesTextView = STextView(R.string.amount_of_tomatoes).textSize(35 dip).<<.marginBottom(20 dip).>>
        amountOfUserTomatoesTextView.setText(amountOfUserTomatoesTextView.getText + " " + amountOfUserTomatoes.toString + " tomatoes:")
        Log.d("UserTomatoesActivity.onCreate", "Created two first textviews")
        val userTomatoes = STextView("").textSize(30 dip)
        for (tomato <- listOfUserTomatoes) {
          Log.d("UserTomatoesActivity.onCreate", "Tomato id: " + tomato.id.toString + ", userId: " + tomato.userId.toString + ", userTomato: " + nr.toString + ", date: " + tomato.date.toString)
          userTomatoes.setText(userTomatoes.getText + "\n" + nr.toString + " : " + tomato.date.toString)
          nr += 1
        }
        Log.d("UserTomatoesActivity.onCreate", "Created list of " + (nr - 1).toString + " tomatoes")
      }.padding(20 dip)
    }

  }

  def amountOfTomatoes(userId: Long): Long = {
    val tdb = new TomatoDatabaseHelper(this)
    val amount = tdb.getAmountOfUserTomatoes(userId)
    Log.d("UserTomatoesActivity.amountOfTomatoes", "Amount: " + amount.toString)
    amount
  }

  def userTomatoes(userId: Long): java.util.List[Tomato] = {
    val tdb = new TomatoDatabaseHelper(this)
    val list = tdb.getUserTomatoes(userId)
    Log.d("UserTomatoesActivity.userTomatoes", "Size: " + list.size.toString)
    list
  }
}