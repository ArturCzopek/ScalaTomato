package czopekonieszczuk.scalatomato.activities

import android.util.Log
import czopekonieszczuk.scalatomato.R
import czopekonieszczuk.scalatomato.databases._
import org.scaloid.common._

class LoginActivity extends SActivity {

  onCreate {
    contentView = new SVerticalLayout {
      val AppNameTextView = STextView(R.string.app_name).textSize(30 dip).<<.marginBottom(50 dip).>>
      val LoginTextView = STextView(R.string.login_text).<<.marginBottom(20 dip).>>
      val LoginEditText = SEditText().<<.marginBottom(20 dip).>>
      val PasswordTextView = STextView(R.string.password_text).<<.marginBottom(20 dip).>>
      val PasswordEditText = SEditText().<<.marginBottom(20 dip).>> inputType TEXT_PASSWORD
      Log.d("LoginActivity", "Created TextViews and EditTexts")
      this += new SLinearLayout {
        SButton(R.string.login_text).<<.Weight(1.0f).>>.onClick(loginUserToApp(LoginEditText.getText.toString, PasswordEditText.getText.toString))
        SButton(R.string.register_text).<<.Weight(1.0f).>>.onClick(startRegisterActivity())
      }
      //TEST BUTTONS
      val testButton1 = SButton("Add test/123").onClick(testAddUser)
      val testButton2 = SButton("Delete test/123").onClick(testDeleteUser)
      val testButton3 = SButton("Tomato activity").onClick(testTomatoUser)
      Log.d("LoginActivity", "Created buttons")
    }.padding(20.dip)

    def testAddUser {
      val user = new User("test", "123")
      val udb = new UserDatabaseHelper(this)
      try {
        udb.addUser(user)
      } catch {
        case exist: android.database.sqlite.SQLiteConstraintException => toast("Uzytkownik istnieje!")
      }
      longToast("Dodano uzytkownika")
    }

    def testDeleteUser() {
      val udb = new UserDatabaseHelper(this)
      udb.deleteUser(1)
      longToast("Usunieto uzytkownika")
    }

    def testTomatoUser() {
      val userId: Long = 1
      val intent = SIntent[TomatoActivity]
      intent.putExtra("userId", userId)
      startActivity(intent)
    }

    def loginUserToApp(login: String, password: String) {
      val udb = new UserDatabaseHelper(this)
      val userId: Long = udb.loginUser(login, password)
      if(userId == -1) {
        new AlertDialogBuilder(R.string.failed, R.string.login_failed_text) {
          neutralButton()
        }.show()
        Log.d("LoginActivity", "showed Alert, not found user")
      } else {
        Log.d("LoginActivity", "Logged userId: "+userId.toString)
        val intent = SIntent[UserActivity]
        intent.putExtra("userId", userId)
        Log.d("LoginActivity", "Put to intent userId: " +userId.toString )
        startActivity(intent)
        Log.d("LoginActivity", "start UserActivity")
      }
    }

    def startRegisterActivity() {
      val intent = SIntent[RegisterActivity]
      startActivity(intent)
      Log.d("LoginActivity", "start RegisterActivity")
    }
  }
}