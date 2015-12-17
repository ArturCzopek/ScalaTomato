package czopekonieszczuk.scalatomato.activities

import org.scaloid.common._

class RegisterActivity extends SActivity {

  onCreate {
    contentView = new SVerticalLayout {
      STextView("RegisterActivity")
    }
  }
}