package id.magga.yourgram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    TextView tvChangeMode;
    Button btnMode;

    boolean loginMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-3397828740583540~1164867233");

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvChangeMode = (TextView) findViewById(R.id.tvChangeMode);
        btnMode = (Button) findViewById(R.id.btnMode);

        ParseInit();

        if(ParseUser.getCurrentUser() != null){
            GoToHome();
        }
    }

    private void ParseInit() {
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("9c344d161a974bb20f0cbb5e869a455e2bacb9ef")
                .server("http://ec2-52-14-11-143.us-east-2.compute.amazonaws.com:80/parse")
                .build()
        );
    }

    public void BtnClicked(View view){

        if(loginMode){
            ParseUser.logInInBackground(etUsername.getText().toString(),
                    etPassword.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if(e == null){
                                Toast.makeText(MainActivity.this, "LOGIN BERHASIL SEBAGAI - " + user.getUsername(), Toast.LENGTH_SHORT).show();
                                GoToHome();
                            } else {
                                Toast.makeText(MainActivity.this, "LOGIN GAGAL - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            ParseUser user = new ParseUser();
            user.setUsername(etUsername.getText().toString());
            user.setPassword(etPassword.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null){
                        Toast.makeText(MainActivity.this, "BERHASIL", Toast.LENGTH_SHORT).show();
                        GoToHome();
                    } else {
                        Toast.makeText(MainActivity.this, "GAGAL - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void ChangeMode(View view){
        if(loginMode){
            loginMode = false;
            tvChangeMode.setText("Or, Login");
            btnMode.setText("Signup");
        } else {
            loginMode = true;
            tvChangeMode.setText("Or, Signup");
            btnMode.setText("Login");
        }
    }
    

    public void GoToHome(){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }
}
