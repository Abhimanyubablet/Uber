package com.example.uber

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class VerifyOtpActivity : AppCompatActivity() {

    lateinit var phoneNumber: String
    private lateinit var auth: FirebaseAuth
    private lateinit var vId: String
    private var requestcode = 1234

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_otp)

        // verify otp


//      for phone auth (auth inilise)
        auth = Firebase.auth

        val enterOtp=findViewById<EditText>(R.id.otp)
        val verifyButton=findViewById<Button>(R.id.verifyOtp)

        phoneNumber=intent.getStringExtra("phone").toString()
        initiateOtp()


        verifyButton.setOnClickListener {

            val credential = PhoneAuthProvider.getCredential(vId, enterOtp.text.toString())
            auth.signInWithCredential(credential)
                .addOnSuccessListener {
                    Toast.makeText(this, "verified", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "Registation successfull", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,DashboardActivity::class.java))

                }
                .addOnFailureListener {
                    Toast.makeText(this, "not verify", Toast.LENGTH_SHORT).show()
                }

        }

    }

    private fun initiateOtp() {

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(30L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onCodeSent(
                    verificationId: String,
                    forceResendingToken: PhoneAuthProvider.ForceResendingToken
                ) {
                    vId = verificationId
                    Toast.makeText(this@VerifyOtpActivity, "send $verificationId", Toast.LENGTH_SHORT).show()
                }

                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {

                }

                override fun onVerificationFailed(e: FirebaseException) {

                }

            })

            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)


    }




}