package com.example.uber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val navigationBtn=findViewById<BottomNavigationView>(R.id.student_navigation_view)
        loadFragment(HomeFragment())
        navigationBtn.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_home -> loadFragment(HomeFragment())
                R.id.bottom_services -> loadFragment(ServiceFragment())
                R.id.bottom_activity -> loadFragment(ActivityPageFragment())
                R.id.bottom_people-> loadFragment(AccountFragment())
            }
            true
        }
    }
    private fun loadFragment (fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.framelaout,fragment).commit()
    }
}