package czopekonieszczuk.scalatomato.activities

import android.os.AsyncTask
import android.util.Log
import android.view.View
import czopekonieszczuk.scalatomato.R
import czopekonieszczuk.scalatomato.databases._
import org.scaloid.common._

class TomatoActivity extends SActivity {

  var userId: Long = -1
  var TOMATO_TIME: Int = 10 //25 * 60 domyslnie, testowo 10 sec
  var timer: STextView = null
  var todayTomatoesTextView: STextView =  null
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



    contentView = new SVerticalLayout {
      todayTomatoesTextView = STextView(R.string.amount_of_today_tomatoes).textSize(30 dip).<<.marginBottom(20 dip).>>
      todayTomatoesTextView.setText(todayTomatoesTextView.getText+ ": " + AmountOfTodayTomatoes(userId).toString)
      Log.d("TomatoActivity", "Created and count today tomatoes for userId: " + userId.toString)
      minutes = (TOMATO_TIME/60).toString
      seconds = if (TOMATO_TIME % 60 < 10) "0"+(TOMATO_TIME % 60).toString else (TOMATO_TIME % 60).toString
      timer = STextView(minutes+":"+seconds).textSize(60 dip).padding(60 dip)
      button = SButton(R.string.start).onClick(startTomato(userId))
      Log.d("TomatoActivity", "Created timerView and startButton")
    }.padding(20 dip)
  }

    def AmountOfTodayTomatoes(userId: Long): Long = {
      val tdb = new TomatoDatabaseHelper(this)
      val amount = tdb.getAmountOfUserTodayTomatoes(userId)
      Log.d("TomatoActivity", "AmountOfTodayTomatoes("+userId+") = "+amount)
      amount
    }

  def startTomato(userId: Long) = {
    val tomatoTimerFun = new TomatoStart()
    Log.d("TomatoActivity", "Created startAsync")
    tomatoTimerFun.execute("")
    Log.d("TomatoActivity", "startAsync.execute")
  }

  private class TomatoStart extends AsyncTask[AnyRef, AnyRef, String] {

    protected override def onPreExecute {
      super.onPreExecute()
      button.setVisibility(View.GONE)
      Log.d("TomatoActivity", "Hide start button")
    }

    Log.d("TomatoActivity", "Start Executing TomatoTimer")

    protected override def doInBackground(anyrefs: AnyRef*): AnyRef = {
      anyrefs.head.asInstanceOf[String]
      var i = TOMATO_TIME
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
        i -=  1
      }
      "End time"
    }

    protected override def onProgressUpdate(values: AnyRef*) {
      timer.text(values.head.asInstanceOf[String])
      Log.d("TomatoActivity", "Change timer")
    }

    protected override def onPostExecute(result: String) {
      super.onPostExecute(result)
      Log.d("TomatoActivity", "Result: " + result)
      button.setVisibility(View.VISIBLE)
      Log.d("TomatoActivity", "Now, button is visible")
      timer.setText((TOMATO_TIME / 60).toString + ":" + (TOMATO_TIME % 60).toString)
      Log.d("TomatoActivity", "Set starting timer")
      val tomato = new Tomato(userId)
      Log.d("TomatoAcitivty", "Insert created tomato, userId: " +tomato.userId.toString + ", date: " +tomato.date)
      val tdb = new TomatoDatabaseHelper(getApplicationContext)
      tdb.addTomato(tomato)
      Log.d("TomatoActivity", "Insert tomato into db and end Async Task")
      todayTomatoesTextView.setText(R.string.amount_of_today_tomatoes)
      todayTomatoesTextView.setText(todayTomatoesTextView.getText+ ": " + AmountOfTodayTomatoes(userId).toString)
      Log.d("TomatoActivity", "Synchronized amount of todayTomatoes")
    }
  }
}
