package czopekonieszczuk.scalatomato.activities

import android.util.Log
import czopekonieszczuk.scalatomato.R
import czopekonieszczuk.scalatomato.databases._
import org.scaloid.common._

class TomatoActivity extends SActivity {
  onCreate {

    var userId: Long = -1
    val extras = getIntent.getExtras
    if (extras != null) {
      userId = extras.getLong("userId")
      Log.d("TomatoActivity", "userId: " + userId.toString)
    } else {
      Log.d("TomatoActivity", "userId: not found")
      finish
      Log.d("TomatoActivity", "finish activity")
    }
  }

}