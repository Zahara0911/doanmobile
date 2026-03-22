package com.example.doanmobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val pref = getSharedPreferences("USER_PREF", MODE_PRIVATE)
        val username = pref.getString("username", "Người dùng")

        val tvUsername = findViewById<TextView>(R.id.tvUsername)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        tvUsername.text = "Xin chào, $username"

        btnLogout.setOnClickListener {
            pref.edit().putBoolean("isLoggedIn", false).apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}