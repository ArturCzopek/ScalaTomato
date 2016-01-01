package czopekonieszczuk.scalatomato.activities

import android.util.Log
import czopekonieszczuk.scalatomato.R
import czopekonieszczuk.scalatomato.databases._
import org.scaloid.common._

class RegisterActivity extends SActivity {

  onCreate {
    contentView = new SVerticalLayout {
      val loginTextView = STextView(R.string.login_text).<<.marginBottom(20 dip).>>
      var loginEditText = SEditText().<<.marginBottom(20 dip).>>
      val passwordTextView = STextView(R.string.password_text).<<.marginBottom(20 dip).>>
      var passwordEditText = SEditText().<<.marginBottom(20 dip).>> inputType TEXT_PASSWORD
      val password2TextView = STextView(R.string.password2_text).<<.marginBottom(20 dip).>>
      var password2EditText = SEditText().<<.marginBottom(20 dip).>> inputType TEXT_PASSWORD
      Log.d("RegisterActivity.onCreate", "Created TextViews and EditTexts")
      this += new SLinearLayout {
        SButton(R.string.done).<<.Weight(1.0f).>>.onClick(registerUser(loginEditText.getText.toString, passwordEditText.getText.toString, password2EditText.getText.toString))
        SButton(R.string.clear).<<.Weight(1.0f).>>.onClick(loginEditText.setText(""), passwordEditText.setText(""), password2EditText.setText(""), toast("Fields cleared"))
        Log.d("RegisterActibity.onCreate", "Created done and clear buttons")
      }
    }.padding(20.dip)

  }
  def registerUser(login: String, password: String, password2: String) {
    if (password != password2) {
      toast("Passwords do not match!")
      Log.d("RegisterActivity.registerUser", "Passwords do not match")
    } else if (login.isEmpty) {
      toast("Please, enter login!")
      Log.d("RegisterActivity.registerUser", "There is no login")
    } else if (password.isEmpty || password2.isEmpty) {
      toast("Please, enter password!")
      Log.d("RegisterActivity.registerUser", "There is no password")
    } else {
      val udb = new UserDatabaseHelper(this)
      val userId: Long = udb.loginUser(login, password)
      if (userId != -1) {
        new AlertDialogBuilder(R.string.register_failed_text) {
          neutralButton()
        }.show()
        Log.d("RegisterActivity.registerUserToApp", "Created Alert, user already exist")
      } else {
        val user = new User(login, password)
        try {
          udb.addUser(user)
          Log.d("RegisterActivity.registerUserToApp", "Adding user to database")
          toast("Register successfull")
          val intent = SIntent[LoginActivity]
          startActivity(intent)
          Log.d("RegisterActivity.registerUserToApp", "Back to home screen")
        } catch {
          case exist: android.database.sqlite.SQLiteConstraintException => toast("Error while adding to database")
        }
      }
    }
  }
}