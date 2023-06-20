package com.example.uber

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LandingPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)

       var getStartedBtn=findViewById<Button>(R.id.get_started)
        getStartedBtn.setOnClickListener {
            startActivity(Intent(this,LoginPageActivity::class.java))
        }

    }
}