package com.example.doanmobile

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

class MainActivity : AppCompatActivity() {

    lateinit var listView: ListView
    lateinit var db: DatabaseHelper
    lateinit var users: MutableList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listUser)
        db = DatabaseHelper(this)

        loadData()
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }

    fun loadData() {
        users = db.getAllUsers().toMutableList()

        val adapter = object : ArrayAdapter<User>(
            this,
            android.R.layout.simple_list_item_1,
            users
        ) {
            override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
                val view = layoutInflater.inflate(R.layout.item_user, null)

                val tv = view.findViewById<TextView>(R.id.tvUsername)
                val btnDelete = view.findViewById<Button>(R.id.btnDelete)

                val user = users[position]
                tv.text = user.username

                btnDelete.setOnClickListener {
                    db.deleteUser(user.id)
                    Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show()
                    loadData()
                }

                return view
            }
        }

        listView.adapter = adapter
    }

}