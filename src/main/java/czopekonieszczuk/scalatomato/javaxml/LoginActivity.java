package czopekonieszczuk.scalatomato.javaxml;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import czopekonieszczuk.scalatomato.R;
import czopekonieszczuk.scalatomato.activities.RegisterActivity;
import czopekonieszczuk.scalatomato.activities.UserActivity;
import czopekonieszczuk.scalatomato.databases.UserDatabaseHelper;

/**
 * Created by czopo on 1/6/16.
 */
public class LoginActivity extends AppCompatActivity {

    EditText loginEditText;
    EditText passwordEditText;
    Button loginButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEditText = (EditText) findViewById(R.id.loginEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);

        Log.d("LoginActivity.onCreate", "Created TextViews and EditTexts");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDatabaseHelper udb = new UserDatabaseHelper();
                String login = loginEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                Long userId = udb.loginUser(login, password);
                if (userId == -1) {
                    new DialogFragment() {
                        @Override
                        public Dialog onCreateDialog(Bundle savedInstanceState) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle(R.string.failed);
                            builder.setMessage(R.string.login_failed_text);
                            return builder.create();
                        }
                    }.show(getFragmentManager(), null);
                    Log.d("LoginActivity.loginUserToApp", "Created Alert, not found user");
                } else {
                    Log.d("LoginActivity.loginUserToApp", "Logged userId: " + userId);
                    Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                    intent.putExtra("userId", userId);
                    Log.d("LoginActivity.loginUserToApp", "Put to intent userId: " + userId);
                    startActivity(intent);
                    Log.d("LoginActivity.loginUserToApp", "Started UserActivity");
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                Log.d("LoginActivity.startRegisterActivity", "Started RegisterActivity");
            }
        });

        Log.d("LoginActivity.onCreate", "Created login and register buttons");

    }
}
