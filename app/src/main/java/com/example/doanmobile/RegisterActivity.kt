package com.example.doanmobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val edtUsername = findViewById<EditText>(R.id.edtUsername)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val username = edtUsername.text.toString().trim()
            val password = edtPassword.text.toString().trim()
            val db = DatabaseHelper(this)

            btnRegister.setOnClickListener {
                val username = edtUsername.text.toString()
                val password = edtPassword.text.toString()

                if (db.register(username, password)) {
                    Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "User đã tồn tại", Toast.LENGTH_SHORT).show()
                }
            }

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val pref = getSharedPreferences("USER_PREF", MODE_PRIVATE)
            pref.edit()
                .putBoolean("isRegistered", true)
                .putBoolean("isLoggedIn", true)
                .putString("username", username)
                .putString("password", password)
                .apply()

            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}