package czopekonieszczuk.scalatomato.activities

import android.util.Log
import org.scaloid.common._

class UserActivity extends SActivity {


  onCreate {

    contentView = new SVerticalLayout {
      var userId: Long = -1
      Log.d("UserActivity", "Started userId: " + userId.toString)
      val extras = getIntent.getExtras
      Log.d("UserActivity", "Get extras from LoginActivity")
      if(extras != null) {
        userId = extras.getLong("userId")
        Log.d("UserActivity", "userId from LoginActivity: " + userId.toString)
      }
      //tu ponizej zrobisz pozyskiwanie uzytkownika, buttony id, nie zapomnij o logach
      STextView("Hello,  " + userId)
    }
  }
}