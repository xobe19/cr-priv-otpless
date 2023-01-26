package com.openplaytech.crpro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.otpless.main.OnOtplessResult
import com.otpless.main.Otpless;
import com.otpless.main.OtplessIntentRequest;
import com.otpless.main.OtplessProvider;
import com.otpless.main.OtplessTokenData;


class MainActivity : AppCompatActivity() {

    lateinit var otpless: Otpless;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        otpless = OtplessProvider.getInstance(this).init(this as OnOtplessResult);
    }

    fun onOtplessResult(tokenData: OtplessTokenData?) {
if(tokenData == null) return;

    }
}