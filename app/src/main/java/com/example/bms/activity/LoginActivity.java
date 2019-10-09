package com.example.bms.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bms.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_singin2;
    private EditText edt_Username, edt_Password;

    public static String filename = "Valustoringfile";
    SharedPreferences SP;

    //token
    private String sendToken;

    //private static final String EMAIL_ADDRESS = "@mncgroup.com"
    //private static final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_Username = (EditText) findViewById(R.id.edt_username);
        edt_Password = (EditText) findViewById(R.id.edt_password);
        btn_singin2 = (Button) findViewById(R.id.btn_singin2);

        SP = getSharedPreferences(filename, 0);
        String getname = SP.getString("key1","");
        edt_Username.setText(getname);

        btn_singin2.setOnClickListener(LoginActivity.this);
    }

    //method login
    private void userLogin() {

        final String username = edt_Username.getText().toString().trim();
        final String password = edt_Password.getText().toString().trim();

        if (username.isEmpty()) {
            edt_Username.setError("Username is required");
            edt_Username.requestFocus();
            return;
        }

        /*
        //email
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches)
        {
            edt_Username.setError("Enter a valid email");
            edt_Username.requestFocus();
        }


        //username
        if (!Pattern.compile(USERNAME_PATTERN).matcher(username).matches()) {
            edt_Username.setError("Enter a valid username");
            edt_Username.requestFocus();
        }
        */

        //password
        if (password.isEmpty()) {
            edt_Password.setError("Password is required");
            edt_Password.requestFocus();
        }

        /*
        //validasi
        if (password.length() > 6){
            edt_Password.setError("Password should be atleast 6 character long");
            edt_Password.requestFocus();
        }
        */

        SharedPreferences.Editor editit = SP.edit();
        editit.putString("key1", username);
        editit.commit();

        if (InternetConnection.checkConnection(getApplicationContext())) {

            checkLogin checklogin = new checkLogin(this);
            checklogin.Check(username, password);

        } else {
            // Not Available...
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("No Internet Connection")
                    .setMessage("You need to have Mobile Data or wifi to access this...")
                    .setCancelable(false)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }


        /*
        //progressDialog
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        //end progressDialog
        */




        /*
        String BASE_URL = "http://portal-bams.mncgroup.com:8008/";

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ClientLdap clientLdap = retrofit.create(ClientLdap.class);

        LoginLdap loginLdap = new LoginLdap();
        loginLdap.setUsername(username);
        loginLdap.setPassword(password);

        //call class interface and class Apiretrofit
        Call<LoginLdap> call = clientLdap.loginLdapClient(loginLdap);

        call.enqueue(new Callback<LoginLdap>() {
            @Override
            public void onResponse(Call<LoginLdap> call, Response<LoginLdap> response) {
                String token = response.headers().get("Token");
                String resp ="ok";
                Log.e("TOKEN", "onResponse: "+resp );

                if (response.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("token", response.body().toString());

                    SharedPreferences.Editor sp
                            = getSharedPreferences("TOKEN",
                            MODE_PRIVATE).edit();

                    progressDialog.dismiss();
                    sp.putString("x", response.body().toString());
                    sp.apply();
                    startActivity(intent);
                    finish();
                }
                else {
                    Log.e("LOGINGAGAL", "x");
                    Log.e("tokenTAG", "Token : " + token);
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<LoginLdap> call, Throwable t) {

                if (haveNetwork()) {
                    //Log.e("kk", "onFailure: " + call.request().toString() );
                    //userLogin();
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                } else if (!haveNetwork()) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Network connection is not available!", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        */
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_singin2:
                userLogin();
                break;
//            case R.id.btn_singUp:
//                userSignUp();
//                break;
        }

    }
}
