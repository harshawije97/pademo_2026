package com.example.processappdemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.retain.retain
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val emailText = findViewById<TextInputEditText>(R.id.emailEditText)
        val passwordText = findViewById<TextInputEditText>(R.id.passwordEditText)
        val emailLayout = findViewById<TextInputLayout>(R.id.emailInputLayout)
        val passwordLayout = findViewById<TextInputLayout>(R.id.passwordInputLayout)
        val authButton = findViewById<MaterialButton>(R.id.loginButton)

        authButton.setOnClickListener {
            val email = emailText.text.toString().trim()
            val password = passwordText.text.toString().trim()

            emailLayout.error = null
            passwordLayout.error = null

            if (email.isEmpty()){
                emailLayout.error = "Email is required"
                return@setOnClickListener
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                emailLayout.error = "Enter a valid email"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                passwordLayout.error = "Password is required"
                return@setOnClickListener
            }

            if (password.length < 6) {
                passwordLayout.error = "Password must be at least 6 characters"
                return@setOnClickListener
            }

            Toast.makeText(this,"Login success", Toast.LENGTH_SHORT).show()

        }
    }
}