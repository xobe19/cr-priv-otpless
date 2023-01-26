package com.openplaytech.crpro

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

/*

Flow of OTPless

-> user clicks on "login with whatsapp button"
-> browser is opened at  url "https://openplaytech.authlink.me" by creating Android.VIEW intent
-> the website has a deeplink for whatsapp & therefore opens whatsapp chat
-> user sends message on whatsapp chat
-> the whatsapp bot replies with authentication url
-> user clicks on that url
-> this url redirects to android deeplink url "classicrummy://..."
-> that particular deeplink is handled by our application by using getIntent() function
-> by using getIntent() we retrieve the unique authentication token that can be used to get details such as Whatsapp Number, Whatsapp Name, etc
-> After getting token, retrofit sends request to OTPless API so that we can use our token and get user details ( Whatsapp Number, etc )
-> After getting Phone Number, all process / flow should be similar to the CR app



 */


data class RequestModel(val waId: String);

interface OTPlessService {
    @Headers("Content-Type: application/json", "clientId: 6h9nb5ck", "clientSecret: 32lm25rnduqla9ac")
    @POST("/")
    suspend fun sendDetailsRequest(@Body requestModel: RequestModel): Response<OtplessResponse>
}




class MainActivity : AppCompatActivity() {


    lateinit var button: Button;
    lateinit var textView: TextView;
    lateinit var retrofit: Retrofit;
    lateinit var otPlessService: OTPlessService;
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrofit = Retrofit.Builder().baseUrl("https://openplaytech.authlink.me").addConverterFactory(
            GsonConverterFactory.create()).build()
        otPlessService = retrofit.create(OTPlessService::class.java)

        button = findViewById(R.id.button);
        button.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://openplaytech.authlink.me"));
            startActivity(browserIntent);
        }
    textView = findViewById(R.id.mainTv);

        val intent: Intent = intent
        val action: String? = intent.action
        val data: Uri? = intent.data

        if(data != null) {
          val waid: String =  data.toString().split('=')[1];
            textView.text = "getting phone number..";


           CoroutineScope(Dispatchers.IO).launch {
              val data =  otPlessService.sendDetailsRequest(RequestModel(waid));
              val body = data.body()
               withContext(Dispatchers.Main) {
                   textView.text = "details are: ${body.toString()}"
               }
           }

        }












    }





}