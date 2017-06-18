package com.bku.cse.karaoke.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.bku.cse.karaoke.helper.DatabaseHelper;
import com.bku.cse.karaoke.helper.SessionManager;
import com.bku.cse.karaoke.model.MSGAuth;
import com.bku.cse.karaoke.rest.ApiClient;
import com.bku.cse.karaoke.rest.ApiInterface;
import com.bku.cse.karaoke.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button btn_loginButton;
    TextView tv_signupLink;
    EditText et_email, et_password;
    private SessionManager session;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.checkTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Init UI
        btn_loginButton = (Button) findViewById(R.id.btn_login);
        tv_signupLink = (TextView) findViewById(R.id.link_signup);
        et_email = (EditText) findViewById(R.id.input_email);
        et_password = (EditText) findViewById(R.id.input_password);

        //Save data init
        db = new DatabaseHelper(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        //set color Register Link
        setupSpanableRegisterLink();

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
    public void setupSpanableRegisterLink() {
        //Change signupLink color: RED
        SpannableString str_link_signup =
                new SpannableString( "No account yet? " /*index 0 - 15*/ + "Create one" /*index 16 - 26*/ );

        //Modify ClickableSpan
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                finish();
                Intent registerIntent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(registerIntent);
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
        ApiInterface apiServer = ApiClient.getClient(this).create(ApiInterface.class);
        Call<MSGAuth> call = apiServer.postLogin(email, password);
        call.enqueue(new Callback<MSGAuth>() {
            @Override
            public void onResponse(Call<MSGAuth> call, Response<MSGAuth> response) {
                Log.d("responseLogin", response.isSuccessful() ? "1" : "0");
                if (response.body().isSuccess()) {
                    Toast.makeText(getApplicationContext(), "Login Successfully!", Toast.LENGTH_LONG).show();

                    session.setLogin(true, response.body().getUser() );

                    //Close Login Activity
                    setResult(RESULT_OK);
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

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
