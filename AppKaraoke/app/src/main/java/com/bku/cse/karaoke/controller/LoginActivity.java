package com.bku.cse.karaoke.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bku.cse.karaoke.R;
import com.bku.cse.karaoke.helper.SQLiteHandler;
import com.bku.cse.karaoke.helper.SessionManager;
import com.bku.cse.karaoke.model.MSGAuth;
import com.bku.cse.karaoke.rest.ApiClient;
import com.bku.cse.karaoke.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bku.cse.karaoke.util.Utils.checkTheme;

public class LoginActivity extends AppCompatActivity {
    Button btn_loginButton;
    TextView tv_signupLink;
    EditText et_email, et_password;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();

        if (session.isLoggedIn()) {
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
            finish();
        }

        // get Email From SIGN UP activity
        Intent gIntent = getIntent();
        if ( gIntent.getStringExtra("email") != null ) {
            et_email.setText(gIntent.getStringExtra("email"));
            et_password.requestFocus();
        }

        //Set OnClick Login button
        btn_loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }
    public void initUI() {
        btn_loginButton = (Button) findViewById(R.id.btn_login);
        tv_signupLink = (TextView) findViewById(R.id.link_signup);
        et_email = (EditText) findViewById(R.id.input_email);
        et_password = (EditText) findViewById(R.id.input_password);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());
        // Session manager
        session = new SessionManager(getApplicationContext());

        //Change signupLink color: RED
        SpannableString str_link_signup =
                new SpannableString( "No account yet? " /*index 0 - 15*/ + "Create one" /*index 16 - 26*/ );

        //Modify ClickableSpan
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                TypedValue typedValue = new TypedValue();
                getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
                int color = typedValue.data;
                ds.setUnderlineText(false);
                ds.setColor(color);
            }
        };
        //set Clickable to text "Create one"
        str_link_signup.setSpan(clickableSpan, 16, 26, 0);

        // this step is mandated for the url and clickable styles.
        tv_signupLink.setMovementMethod(LinkMovementMethod.getInstance());
        tv_signupLink.setText(str_link_signup);
    }

    private void login() {
        //Validate input Form
        if (!validate()) {
            onLoginFailed();
            return;
        }

        final ProgressDialog progressDialog =
                new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = et_email.getText().toString();
        String password = et_password.getText().toString();

        // TODO: Implement your own authentication logic here
        ApiInterface apiServer = ApiClient.getClient().create(ApiInterface.class);
        Call<MSGAuth> call = apiServer.postLogin(email, password);
        call.enqueue(new Callback<MSGAuth>() {
            @Override
            public void onResponse(Call<MSGAuth> call, Response<MSGAuth> response) {
                Log.d("sss", response.isSuccessful() ? "1" : "0");
                if (response.body().isSuccess()) {
                    // user successfully logged in
                    // Create login session = true
                    session.setLogin(true);

                    Toast.makeText(getApplicationContext(), "Login Successfully!", Toast.LENGTH_LONG).show();
                    //Close Login Activity
                    finish();
                }
                else {
                    Log.e("Error Login", response.body().getMessage());
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
                //Close processDialog
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MSGAuth> call, Throwable t) {
                Log.e("Network Err", t.toString());
                //Close processDialog
                progressDialog.dismiss();
            }
        });
    }

    private void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError("enter a valid email address");
            valid = false;
        } else {
            et_email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            et_password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            et_password.setError(null);
        }

        return valid;
    }

    /**
     * Back button handler
     */
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
