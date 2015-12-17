package czopekonieszczuk.scalatomato.activities

import android.content.Intent
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
      this += new SLinearLayout {
        SButton(R.string.login_text).<<.Weight(1.0f).>>.onClick(testStartWithUser())
        SButton(R.string.register_text).<<.Weight(1.0f).>>.onClick(startRegisterActivity())
      }
    }.padding(20.dip)

    def myToast() {
      longToast("Ssijcie")
    }

    def loginUserToApp(login: String, password: String) {
      val userId: Long = UserRepository.logIn(getApplicationContext, login, password)
      if(userId == 0) {
        new AlertDialogBuilder(R.string.failed, R.string.login_failed_text) {
          neutralButton()
        }.show()
      } else {
        val intent = SIntent[UserActivity]
        intent.putExtra("userId", userId)
        startActivity(intent)
      }
    }

    def testStartWithUser() {
      val user = new User("Ala", "123")
      //UserRepository.addUser(getApplicationContext(), user)
      val intent = SIntent[UserActivity]
      intent.putExtra("ID", user.id)
      startActivity(intent)
    }


    def startRegisterActivity() {
      val intent = SIntent[RegisterActivity]
      startActivity(intent)
    }
  }
}