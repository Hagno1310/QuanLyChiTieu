package com.example.quanlychitieu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class LoginActivity : Activity() {

    // Tài khoản cố định (hard-code)
    private val USERNAME = "admin"
    private val PASSWORD = "123456"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val edtUsername = findViewById<EditText>(R.id.edtUsername)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val tvError = findViewById<TextView>(R.id.tvError)

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            val username = edtUsername.text.toString().trim()
            val password = edtPassword.text.toString()

            val error = when {
                username.isEmpty() || password.isEmpty() -> "Vui lòng nhập tên đăng nhập và mật khẩu"
                username != USERNAME || password != PASSWORD -> "Sai tên đăng nhập hoặc mật khẩu"
                else -> null
            }

            if (error != null) {
                tvError.text = error
                tvError.visibility = View.VISIBLE
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish() // không quay lại màn hình đăng nhập bằng nút Back
            }
        }
    }
}
