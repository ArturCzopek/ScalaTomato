package czopekonieszczuk.scalatomato.activities

import android.os.AsyncTask
import android.util.Log
import android.view.View
import czopekonieszczuk.scalatomato.R
import czopekonieszczuk.scalatomato.databases._
import org.scaloid.common._

class TomatoActivity extends SActivity {

  var userId: Long = -1
  var TOMATO_TIME: Int = 25 * 60
  var timer: STextView = null
//  var todayTomatoesTextView: STextView =  null
  var button: SButton = null
  var minutes: String = null
  var seconds: String = null

  onCreate {
    val extras = getIntent.getExtras
    if (extras != null) {
      userId = extras.getLong("userId")
      Log.d("TomatoActivity", "userId: " + userId.toString)
    } else {
      Log.d("TomatoActivity", "userId: not found")
      finish
      Log.d("TomatoActivity", "finish activity")
    }

//    val AmountOfTodayUserTomatoes = R.string.amount_of_today_tomatoes + ": " + AmountOfTodayTomatoes(userId).toString

    contentView = new SVerticalLayout {
//      todayTomatoesTextView = STextView(AmountOfTodayUserTomatoes).textSize(20 dip).<<.marginBottom(20 dip).>>
      Log.d("TomatoActivity", "Created and count today tomatoes for userId: " + userId.toString)
      minutes = (TOMATO_TIME/60).toString
      seconds = if (TOMATO_TIME % 60 < 10) "0"+(TOMATO_TIME % 60).toString else (TOMATO_TIME % 60).toString
      timer = STextView(minutes+":"+seconds).textSize(30 dip).padding(30 dip)
      button = SButton(R.string.start).onClick(StartTomato(userId))
      Log.d("TomatoActivity", "Created timerView and startButton")
    }.padding(20 dip)
  }

//    def AmountOfTodayTomatoes(userId: Long): Long = {
//      val tdb = new TomatoDatabaseHelper(this)
//      tdb.getAmountOfUserTodayTomatoes(userId)
//      Log.d("TomatoActivity", "AmountOfTodayTomatoes("+userId+") = "+tdb.getAmountOfUserTodayTomatoes(userId))
//    }

  def StartTomato(userId: Long) = {
    val startAsync = new StartAsync()
    Log.d("TomatoActivity", "Created startAsync")
    startAsync.execute(TOMATO_TIME)
    Log.d("TomatoActivity", "startAsync.execute")
  }

  class StartAsync extends AsyncTask[Int, String, Void] {
       protected override def onPreExecute() {
         super.onPreExecute()
         button.setVisibility(View.GONE)
         Log.d("TomatoActivity", "Hide start button")
       }

       Log.d("TomatoActivity", "Start Executing TomatoTimer")

       override protected def doInBackground(time : Int*): String = {
         var i = time(0)
         Log.d("TomatoActivity", "Start time: " + i.toString)
         while (i > 0) {
           minutes = (i/60).toString
           seconds = if (i % 60 < 10) "0"+(i % 60).toString else (i % 60).toString
           val timeView: String = minutes + ":" + seconds
           publishProgress(timeView)
           Log.d("TomatoActivity", "Time to end: " +timeView)
           try {
             Thread.sleep(1000)
           } catch {
             case e: InterruptedException => e.printStackTrace()
               Log.d("TomatoActivity", "Error in doInBackground")
           }
           i = i - 1
         }
         return null
       }


       protected override def onProgressUpdate(values: String*) {
         timer.setText(values(0))
         Log.d("TomatoActivity", "Change timer")
       }

       protected override def onPostExecute(result: Void) {
         super.onPostExecute(result)
         button.setVisibility(View.VISIBLE)
         Log.d("TomatoActivity", "Now, button is visible")
         timer.setText((TOMATO_TIME / 60).toString + ":" + (TOMATO_TIME % 60).toString)
         Log.d("TomatoActivity", "Set starting timer")
         val tomato = new Tomato(userId)
         Log.d("TomatoAcitivty", "Insert created tomato, userId: " +tomato.userId.toString + ", date: " +tomato.date)
         val tdb = new TomatoDatabaseHelper(getApplicationContext)
         tdb.addTomato(tomato)
         Log.d("TomatoActivity", "Insert tomato into db and end Async Task")
       }
     }
}
