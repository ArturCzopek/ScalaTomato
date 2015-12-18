package czopekonieszczuk.scalatomato.activities

import android.util.Log
import org.scaloid.common._

class UserActivity extends SActivity {


  onCreate {
    contentView = new SVerticalLayout {

      var userId: Long = -1
      Log.d("userId in UserActivity", userId.toString)
      val extras = getIntent.getExtras
      Log.d("getExtras", "from LoginActivity")
      if(extras != null) {
        userId = extras.getLong("userId")
        Log.d("pozyskano longa", "lol")
      }
      //tu ponizej zrobisz pozyskiwanie uzytkownika, buttony id
      STextView("Hello,  " + userId)
    }
  }
}