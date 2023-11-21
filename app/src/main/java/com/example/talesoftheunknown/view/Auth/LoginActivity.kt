package com.example.talesoftheunknown.view.Auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.talesoftheunknown.component.ProtectedButton
import com.example.talesoftheunknown.component.ProtectedEditText
import com.example.talesoftheunknown.data.Model.Auth.Login
import com.example.talesoftheunknown.data.Model.Auth.UserRequest
import com.example.talesoftheunknown.data.Model.Auth.UserResponse
import com.example.talesoftheunknown.data.Retrofit.ApiConfig
import com.example.talesoftheunknown.databinding.ActivityLoginBinding
import com.example.talesoftheunknown.view.Main.MainActivity
import com.example.talesoftheunknown.view.ViewModelFactory
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding
    private lateinit var protectedButton: ProtectedButton
    private lateinit var protectedEditText: ProtectedEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        protectedButton = binding.protectedButton
        protectedEditText = binding.RIdEdLoginPassword

        setupView()
        setupAction()
        playAnimation()
//        setMyButtonEnable()

        protectedEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    private fun setMyButtonEnable() {
        val result = protectedEditText.text
        protectedButton.isEnabled = result != null && result.toString().length >= 8
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.protectedButton.setOnClickListener {
            val request = UserRequest(email = "test", password = "test")
            request.email = binding.RIdEdLoginEmail.text.toString().trim()
            request.password = binding.RIdEdLoginPassword.text.toString().trim()

            lifecycleScope.launch {
                try {
                    val apiService = ApiConfig.getApiService(token = "")
                    val response = apiService.login(request.email, request.password)
                    val userResponse = response.loginResult

                    if (userResponse != null) {
                        val name = userResponse.name
                        val token = userResponse.token
                        viewModel.saveSession(UserResponse(Login("", name, token), false, "Success", true ))
                    }

                    AlertDialog.Builder(this@LoginActivity).apply {
                        setTitle("Success")
                        setMessage("data: ${response.loginResult}")
                        setPositiveButton("Next") { _, _ ->
                            val intent = Intent(context, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                        create()
                        show()
                    }
                } catch(e: HttpException) {
                    val errorMessage = "Login failed: ${e.message()}"
                    Log.e("Login", errorMessage)
                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.textButtonRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val message =
            ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(binding.protectedButton, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                message,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login
            )
            startDelay = 100
        }.start()
    }

}