package czopekonieszczuk.scalatomato.activities

import org.scaloid.common._

class UserActivity extends SActivity {

  val userId = getIntent().getExtras.getInt("ID")
  onCreate {
    contentView = new SVerticalLayout {
     STextView("UserActivity " + userId.toString())
    }
  }
}