package com.example.uber

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hbb20.CountryCodePicker


class LoginPageActivity : AppCompatActivity() {
    lateinit var buttonSendOtp: Button
    lateinit var phoneNumber: String
    private lateinit var auth: FirebaseAuth
    private var requestcode = 1234
    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

//        for google auth (FirebaseApp.initializeApp, FirebaseAuth.getInstance() inilise)
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()

//        for phone auth (auth inilise)
        auth = Firebase.auth
        val phone = findViewById<EditText>(R.id.phone_number)
        val ccp = findViewById<CountryCodePicker>(R.id.ccp)
         buttonSendOtp = findViewById(R.id.sendOtp)

        buttonSendOtp.setOnClickListener {
            ccp.registerCarrierNumberEditText(phone)
            phoneNumber = ccp.fullNumberWithPlus.replace(" ", "")
            val intent=Intent(this,VerifyOtpActivity::class.java)
            intent.putExtra("phone",phoneNumber)
            startActivity(intent)

             }



        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        val signInButton = findViewById<SignInButton>(R.id.sign_in_button)

        signInButton.setOnClickListener {
            auth.signOut()
            val googleIntent = googleSignInClient.signInIntent
            startActivityForResult(googleIntent, requestcode)
        }
    }



    override fun onActivityResult(ActivityRequestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(ActivityRequestCode, resultCode, data)

        if (ActivityRequestCode == requestcode) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            task.addOnSuccessListener { it ->
                val credencial = GoogleAuthProvider.getCredential(it.idToken, null)
                auth.signInWithCredential(credencial)
                    .addOnSuccessListener {
                        Toast.makeText(this, "" + it.user?.displayName+ it.user?.email+" Succesfull", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(Intent(this,DashboardActivity::class.java))
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "" + it.message, Toast.LENGTH_SHORT).show()
                    }
            }
                .addOnFailureListener {
                    Toast.makeText(this, "" + it.message, Toast.LENGTH_SHORT).show()
                }
        }


    }
}