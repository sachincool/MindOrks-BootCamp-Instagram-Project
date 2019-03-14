package com.example.instagramclone

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.instagramclone.common.Status
import com.example.instagramclone.ui.login.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class MainActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        loginViewModel.emailValidationLiveData.observe(this, Observer {
            if (it.status == Status.ERROR) etEmail.setTextColor(Color.RED)
            else etEmail.setTextColor(Color.BLACK)
        })

        loginViewModel.passwordValidationLiveData.observe(this, Observer {
            if (it.status == Status.ERROR) etPassword.setTextColor(Color.RED)
            else etPassword.setTextColor(Color.BLACK)
        })

        loginViewModel.navigationLiveData.observe(this, Observer {
            if (it) Toast.makeText(applicationContext, "Login Success", Toast.LENGTH_SHORT).show()
        })

        setupView()
    }

    private fun setupView() {
        btLogin.setOnClickListener {
            loginViewModel.onLoginClick(etEmail.text.toString(), etPassword.text.toString())
        }

        etEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                etEmail.setTextColor(Color.BLACK)
            }
        })

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                etPassword.setTextColor(Color.BLACK)
            }
        })
    }
}
