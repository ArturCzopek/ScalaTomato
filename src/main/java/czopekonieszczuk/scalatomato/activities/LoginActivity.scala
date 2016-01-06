package czopekonieszczuk.scalatomato.activities

import android.util.Log
import czopekonieszczuk.scalatomato.R
import czopekonieszczuk.scalatomato.databases._
import org.scaloid.common._

class LoginActivity extends SActivity {

  onCreate {

    contentView = new SScrollView {
      this += new SVerticalLayout {
        val appNameTextView = STextView(R.string.app_name).textSize(30 dip).<<.marginBottom(50 dip).>>
        val loginTextView = STextView(R.string.login_text).<<.marginBottom(20 dip).>>
        val loginEditText = SEditText().<<.marginBottom(20 dip).>>
        val passwordTextView = STextView(R.string.password_text).<<.marginBottom(20 dip).>>
        val passwordEditText = SEditText().<<.marginBottom(20 dip).>> inputType TEXT_PASSWORD
        Log.d("LoginActivity.onCreate", "Created TextViews and EditTexts")

        this += new SLinearLayout {
          SButton(R.string.login_text).<<.Weight(1.0f).>>.onClick(loginUserToApp(loginEditText.getText.toString, passwordEditText.getText.toString))
          SButton(R.string.register_text).<<.Weight(1.0f).>>.onClick(startRegisterActivity)
          Log.d("LoginActivity.onCreate", "Created login and register buttons")
        }

        //TEST BUTTONS
//        val testButton1 = SButton("Add test/123").onClick(testAddUser)
//        val testButton2 = SButton("Delete test/123").onClick(testDeleteUser)
//        val testButton3 = SButton("Tomato activity").onClick(testTomatoUser)
//        val testButton4 = SButton("List tomatoes").onClick(testUserTomatoes)
//        Log.d("LoginActivity.onCreate", "Created test buttons")
      }.padding(20.dip)
    }

  }


  def testAddUser {
    val user = new User("test", "123")
    val udb = new UserDatabaseHelper(this)
    try {
      udb.addUser(user)
      Log.d("LoginActivity.testAddUser", "Added test user")
    } catch {
      case exist: android.database.sqlite.SQLiteConstraintException => toast("Uzytkownik istnieje!")
    }
  }

  def testDeleteUser() {
    val udb = new UserDatabaseHelper(this)
    udb.deleteUser(4)
    udb.deleteUser(3)
    udb.deleteUser(2)
    udb.deleteUser(1)
    longToast("Usunieto uzytkownikow")
    Log.d("LoginActivity.testDeleteUser", "Deleted test users")
  }

  def testTomatoUser {
    val userId: Long = 5
    val intent = SIntent[TomatoActivity]
    intent.putExtra("userId", userId)
    Log.d("LoginActivity.testTomatoUser", "Put to TomatoActivity intent userId: " +userId.toString)
    startActivity(intent)
    Log.d("LoginActivity.testTomatoUser", "Started TomatoActivity")
  }

  def testUserTomatoes {
    val userId: Long = 5
    val intent = SIntent[UserTomatoesActivity]
    intent.putExtra("userId", userId)
    Log.d("LoginActivity.testUserTomatoes", "Put to TomatoActivity intent userId: " +userId.toString)
    startActivity(intent)
    Log.d("LoginActivity.testUserTomatoes", "Started UserTomatoesActivity")
  }

  def loginUserToApp(login: String, password: String) {
    val udb = new UserDatabaseHelper(this)
    val userId: Long = udb.loginUser(login, password)
    if(userId == -1) {
      new AlertDialogBuilder(R.string.failed, R.string.login_failed_text) {
        neutralButton()
      }.show()
      Log.d("LoginActivity.loginUserToApp", "Created Alert, not found user")
    } else {
      Log.d("LoginActivity.loginUserToApp", "Logged userId: "+userId.toString)
      val intent = SIntent[UserActivity]
      intent.putExtra("userId", userId)
      Log.d("LoginActivity.loginUserToApp", "Put to intent userId: " +userId.toString)
      startActivity(intent)
      Log.d("LoginActivity.loginUserToApp", "Started UserActivity")
    }
  }

  def startRegisterActivity {
    val intent = SIntent[RegisterActivity]
    startActivity(intent)
    Log.d("LoginActivity.startRegisterActivity", "Started RegisterActivity")
  }
}