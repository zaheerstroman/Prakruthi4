package com.prakruthi.ui;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.PatternMatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.goodiebag.pinview.Pinview;
import com.google.firebase.messaging.Constants;
import com.prakruthi.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ForgetPassword extends AppCompatActivity {
    AppCompatButton update_btn_new_crediantials,nextbtn_forget_password , forget_password_backbtn, otp_verfication_submit_btn;
    Pinview pinview_4_digits_forgetPassword;
    EditText new_password,confirm_password,email_address;
    RelativeLayout forget_password_relativelayout,otp_verification_relativelayout,new_credential_relativelayout,password_updated_animation_relativelayout;

    TextView tv_resend_otp_timer_forgetpassword , txt_re_send , txt_enter_otp_sent_to,tv_resend_otp_timer;

    String PhoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        //ForgetPassword First Page
        forget_password_relativelayout = findViewById(R.id.forget_password_email_RelativeLayout);
        forget_password_backbtn = findViewById(R.id.forgetpassword_btn);
        nextbtn_forget_password = findViewById(R.id.next_forget_password_btn);
        email_address = findViewById(R.id.edittext_forgetpassword_email_adrs);

        //otp second page
        otp_verification_relativelayout = findViewById(R.id.Forget_Password_OTP_RelativeLayout);
        pinview_4_digits_forgetPassword = findViewById(R.id.pinview_4_digits_forgetPassword);
        otp_verfication_submit_btn = findViewById(R.id.btn_otp_submit);
        tv_resend_otp_timer_forgetpassword = findViewById(R.id.tv_resend_otp_timer_forgetpassword);
        txt_re_send = findViewById(R.id.txt_re_send);
        txt_enter_otp_sent_to = findViewById(R.id.txt_enter_otp_sent_to);

        //update password third page
        new_credential_relativelayout = findViewById(R.id.new_credentials_password_email);
        new_password = findViewById(R.id.edittext_new_password);
        confirm_password = findViewById(R.id.edittext_confirm_password);
        update_btn_new_crediantials = findViewById(R.id.btn_update_text);

        //otp animation fourth page
        password_updated_animation_relativelayout = findViewById(R.id.password_updated_animation);

        forget_password_backbtn.setOnClickListener(view -> {
            super.onBackPressed();
        });

        nextbtn_forget_password.setOnClickListener(view -> {
            if (email_address.getText().toString().length() > 9)
                Api(email_address.getText().toString());
            else
                Toast.makeText(this, "Enter a valid PhoneNumber", Toast.LENGTH_SHORT).show();

        });

        otp_verfication_submit_btn.setOnClickListener(view -> {
            verifyOtp(pinview_4_digits_forgetPassword.getValue());
        });

        update_btn_new_crediantials.setOnClickListener(v -> {
            if (new_password.getText().toString().isEmpty())
            {
                new_password.setError("Empty");
                return;
            }
            if (!new_password.getText().toString().isEmpty() && new_password.getText().toString().equals(confirm_password.getText().toString()))
            {
                newPassword(this.PhoneNumber,new_password.getText().toString());
            }
            else
                Toast.makeText(this, "Password Must Match", Toast.LENGTH_SHORT).show();
        });
        txt_re_send.setOnClickListener(v -> {
//        txt_re_send.setTextColor(Color.GRAY);
            resendOtp();
        });
//        startTimer(60000,1000);
    }

    public void Api(String Phonenumber) {
        nextbtn_forget_password.setVisibility(View.INVISIBLE);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Creating array for parameters
                String[] field = new String[1];
                field[0] = "mobile";
                //Creating array for data
                String[] data = new String[1];
                data[0] = Phonenumber;
                PutData putData = new PutData(Variables.BaseUrl + "forgetPassword", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        // result = Api Result
                        String result = putData.getResult();
                        try {
                            JSONObject json = new JSONObject(result);
                            boolean statusCode = json.getBoolean("status_code");
                            String message = json.getString("message");
                            if (statusCode)
                            {
                                Toast.makeText(ForgetPassword.this, message, Toast.LENGTH_SHORT).show();
                                forget_password_relativelayout.setVisibility(View.GONE);
                                otp_verification_relativelayout.setVisibility(View.VISIBLE);
                                ForgetPassword.this.PhoneNumber = Phonenumber;
                                txt_enter_otp_sent_to.append(Phonenumber);
                                startTimer(60000,1000);
                            }
                            else
                            {
                                Toast.makeText(ForgetPassword.this, message, Toast.LENGTH_SHORT).show();
                                email_address.setError("Invalid");
                                nextbtn_forget_password.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            nextbtn_forget_password.setVisibility(View.VISIBLE);
                            Toast.makeText(ForgetPassword.this, "Network Error", Toast.LENGTH_SHORT).show();
                            nextbtn_forget_password.setVisibility(View.VISIBLE);
                        }

                    }
                }
                else {
                    nextbtn_forget_password.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void verifyOtp(String otp) {
        //Start ProgressBar first (Set visibility VISIBLE)
        otp_verfication_submit_btn.setVisibility(View.GONE);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[2];
                field[0] = "mobile";
                field[1] = "otp";
                //Creating array for data
                String[] data = new String[2];
                data[0] = ForgetPassword.this.PhoneNumber;
                data[1] = otp;
                PutData putData = new PutData(Variables.BaseUrl+"verifyOtp", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        Log.e(Constants.TAG, result );
                        //End ProgressBar (Set visibility to GONE)
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            boolean statusCode = jsonObject.getBoolean("status_code");
                            if (statusCode) {
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                // start the activity with departmentId as an extra
                                otp_verification_relativelayout.setVisibility(View.GONE);
                                new_credential_relativelayout.setVisibility(View.VISIBLE);

                            } else {
                                // handle the case where status code is false
                                String message = jsonObject.getString("message");
                                Toast.makeText(ForgetPassword.this, message , Toast.LENGTH_SHORT).show();
                                otp_verfication_submit_btn.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
                //End Write and Read data with URL
            }
        });
    }

    public void newPassword(String Mobile,String passwordString)
    {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Creating array for parameters
                String[] field = new String[2];
                field[0] = "mobile";
                field[1] = "password";
                //Creating array for data
                String[] data = new String[2];
                data[0] = Mobile;
                data[1] = passwordString;
                PutData putData = new PutData(Variables.BaseUrl+"changePassword", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        // result = Api Result
                        String result = putData.getResult();
                        try {
                            JSONObject json = new JSONObject(result);
                            boolean statusCode = json.getBoolean("status_code");
                            String message = json.getString("message");
                            if (statusCode)
                            {
                                Toast.makeText(ForgetPassword.this, message, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else
                            {
                                Toast.makeText(ForgetPassword.this, message, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ForgetPassword.this, "Network Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(ForgetPassword.this, "Network Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void startTimer(final long finish, long tick) {
        txt_re_send.setEnabled(false);
        txt_re_send.setClickable(false);
        new CountDownTimer(finish, tick) {

            public void onTick(long millisUntilFinished) {
                long remainedSecs = millisUntilFinished / 1000;
                String stringTime = String.format("%02d:%02d", (remainedSecs / 60), (remainedSecs % 60));
                tv_resend_otp_timer_forgetpassword.setText(stringTime);// manage it according to you
            }

            public void onFinish() {
                tv_resend_otp_timer_forgetpassword.setText("00:00");
                txt_re_send.setEnabled(true);
                txt_re_send.setClickable(true);
                txt_re_send.setTextColor(getResources().getColor(R.color.Primary));
                cancel();
            }
        }.start();
    }
    private void resendOtp()
    {
        //Start ProgressBar first (Set visibility VISIBLE)
        txt_re_send.setVisibility(View.GONE);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[1];
                field[0] = "mobile";
                String[] data = new String[1];
                data[0] = ForgetPassword.this.PhoneNumber;
                PutData putData = new PutData(Variables.BaseUrl+"resendOtp", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        Log.e(Constants.TAG, result );
                        //End ProgressBar (Set visibility to GONE)
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            boolean statusCode = jsonObject.getBoolean("status_code");
                            if (statusCode) {
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                startTimer(60000,1000);
                                txt_re_send.setTextColor(Color.GRAY);
                            } else {
                                // handle the case where status code is false
                                String message = jsonObject.getString("message");
                                Toast.makeText(ForgetPassword.this, message , Toast.LENGTH_SHORT).show();
                                startTimer(60000,1000);
                                txt_re_send.setTextColor(Color.GRAY);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
                //End Write and Read data with URL
            }
        });
    }

}