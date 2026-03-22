package com.example.doanmobile

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "bank.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL("""
            CREATE TABLE users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE,
                password TEXT,
                balance REAL,
                role TEXT
            )
        """)

        // 👑 ADMIN mặc định
        db.execSQL("""
            INSERT INTO users (username, password, balance, role)
            VALUES ('admin', '123', 1000, 'admin')
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun register(username: String, password: String): Boolean {
        val db = writableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM users WHERE username=?",
            arrayOf(username)
        )

        if (cursor.count > 0) {
            cursor.close()
            return false
        }

        cursor.close()

        val values = ContentValues().apply {
            put("username", username)
            put("password", password)
            put("balance", 0)
            put("role", "user")
        }

        db.insert("users", null, values)
        return true
    }

    fun loginRole(username: String, password: String): String? {
        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT role FROM users WHERE username=? AND password=?",
            arrayOf(username, password)
        )

        if (cursor.moveToFirst()) {
            val role = cursor.getString(0)
            cursor.close()
            return role
        }

        cursor.close()
        return null
    }
    fun getAllUsers(): List<User> {
        val list = mutableListOf<User>()
        val db = readableDatabase

        val cursor = db.rawQuery("SELECT * FROM users", null)

        while (cursor.moveToNext()) {
            val user = User(
                id = cursor.getInt(0),
                username = cursor.getString(1),
                password = cursor.getString(2),
                balance = cursor.getDouble(3),
                role = cursor.getString(4)
            )
            list.add(user)
        }

        cursor.close()
        return list
    }

    fun deleteUser(id: Int) {
        val db = writableDatabase
        db.delete("users", "id=?", arrayOf(id.toString()))
    }
}