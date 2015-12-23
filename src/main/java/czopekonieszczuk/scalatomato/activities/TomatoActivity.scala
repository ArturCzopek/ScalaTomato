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
  var amountOfTodayUserTomatoes: Long = 0
  Log.d("TomatoActivity", "Initialized null variables")

  onCreate {
    val extras = getIntent.getExtras
    if (extras != null) {
      userId = extras.getLong("userId")
      Log.d("TomatoActivity.onCreate", "userId from intent: " + userId.toString)
    } else {
      Log.d("TomatoActivity.onCreate", "userId: not found")
      finish
      Log.d("TomatoActivity.onCreate", "finish activity")
    }

    contentView = new SVerticalLayout {
      amountOfTodayUserTomatoes = amountOfTodayTomatoes(userId)
      todayTomatoesTextView = STextView(R.string.amount_of_today_tomatoes).textSize(30 dip).<<.marginBottom(20 dip).>>
      todayTomatoesTextView.setText(todayTomatoesTextView.getText+ ": " + amountOfTodayUserTomatoes.toString)
      Log.d("TomatoActivity.onCreate", "Count amount of today tomatoes for userId: " + userId.toString)

      minutes = (TOMATO_TIME/60).toString
      seconds = if (TOMATO_TIME % 60 < 10) "0"+(TOMATO_TIME % 60).toString else (TOMATO_TIME % 60).toString
      timer = STextView(minutes+":"+seconds).textSize(60 dip).padding(60 dip)
      button = SButton(R.string.start).onClick(startTomato(userId))
      Log.d("TomatoActivity.onCreate", "Created timerView and startButton")
      Log.d("TomatoActivity.onCreate", "Started value of timer: " +timer.getText.toString)
    }.padding(20 dip)

  }

    def amountOfTodayTomatoes(userId: Long): Long = {
      val tdb = new TomatoDatabaseHelper(this)
      val amount = tdb.getAmountOfUserTodayTomatoes(userId)
      Log.d("TomatoActivity.amountOfTodayTomatoes", "For userId: "+userId+" = "+amount)
      amount
    }

  def startTomato(userId: Long) = {
    val tomatoTimerFun = new TomatoStart()
    Log.d("TomatoActivity.startTomato", "Created AsyncTask")
    tomatoTimerFun.execute("")
    Log.d("TomatoActivity.startTomato", "Started AsyncTask")
  }

  private class TomatoStart extends AsyncTask[AnyRef, AnyRef, String] {

    protected override def onPreExecute {
      super.onPreExecute()
      button.setVisibility(View.GONE)
      Log.d("TomatoStart.onPreExecute", "Hide start button")
    }

    Log.d("TomatoActivity", "Start Executing TomatoTimer")

    protected override def doInBackground(anyrefs: AnyRef*): AnyRef = {
      anyrefs.head.asInstanceOf[String]
      var i = TOMATO_TIME
      Log.d("TomatoStart.doInBackground", "Start time: " + i.toString)

      while (i > 0) {
        minutes = (i/60).toString
        seconds = if (i % 60 < 10) "0"+(i % 60).toString else (i % 60).toString
        val timeView: String = minutes + ":" + seconds
        publishProgress(timeView)
        Log.d("TomatoStart.doInBackground", "Time to end: " +timeView)

        try {
          Thread.sleep(1000)
        } catch {
          case e: InterruptedException => e.printStackTrace()
            Log.d("TomatoStart.doInBackground", "Error in doInBackground")
        }
        i -=  1
      }
      Log.d("TomatoStart.doInBackground", "Timer is off, tomato can be added")
      "OK"
    }

    protected override def onProgressUpdate(values: AnyRef*) {
      timer.text(values.head.asInstanceOf[String])
      Log.d("TomatoStart.onProgressUpdate", "Change timer text")
    }

    protected override def onPostExecute(result: String) {
      super.onPostExecute(result)
      Log.d("TomatoStart.onPostExecute", "Full timer tomato: " + result.toString)
      button.setVisibility(View.VISIBLE)
      Log.d("TomatoStart.onPostExecute", "Button is visible again")
      timer.setText((TOMATO_TIME / 60).toString + ":" + (TOMATO_TIME % 60).toString)
      Log.d("TomatoStart.onPostExecute", "Set timer to started value: " +timer.getText.toString)
      if(result.equals("OK")) {
        val tomato = new Tomato(userId)
        Log.d("TomatoStart.onPostExecute", "Created tomato, userId: " + tomato.userId.toString + ", date: " + tomato.date)
        val tdb = new TomatoDatabaseHelper(getApplicationContext)
        tdb.addTomato(tomato)
        Log.d("TomatoStart.onPostExecute", "Inserted tomato into db")
      }
      todayTomatoesTextView.setText(R.string.amount_of_today_tomatoes)
      todayTomatoesTextView.setText(todayTomatoesTextView.getText+ ": " + amountOfTodayTomatoes(userId).toString)
      Log.d("TomatoStart.onPostExecute", "Refreshed textViews")
      Log.d("TomatoStart.onPostExecute", "Ended AsyncTask")
    }
  }
}
