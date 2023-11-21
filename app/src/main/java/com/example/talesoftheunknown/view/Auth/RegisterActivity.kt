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
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.talesoftheunknown.component.ProtectedButton
import com.example.talesoftheunknown.component.ProtectedEditText
import com.example.talesoftheunknown.data.Model.Auth.RegisterRequest
import com.example.talesoftheunknown.data.Retrofit.ApiConfig
import com.example.talesoftheunknown.databinding.ActivityRegisterBinding
import com.example.talesoftheunknown.view.Main.MainActivity
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import android.widget.Toast
import com.google.gson.Gson

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var protectedButton: ProtectedButton
    private lateinit var protectedEditText: ProtectedEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        protectedButton = binding.protectedButton
        protectedEditText = binding.RIdEdRegisterPassword

        setupView()
        setupAction()
        playAnimation()
        setMyButtonEnable()

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
            val request = RegisterRequest(name="test", email = "test", password = "123")
            request.name = binding.RIdEdRegisterName.text.toString().trim()
            request.email = binding.RIdEdRegisterEmail.text.toString().trim()
            request.password = binding.RIdEdRegisterPassword.text.toString().trim()

            lifecycleScope.launch {
                try {
                    val apiService = ApiConfig.getApiService(token = "")
                    val response = apiService.register(request.name, request.email, request.password)
                    Toast.makeText(this@RegisterActivity, "${response.error}", Toast.LENGTH_SHORT).show()
                    AlertDialog.Builder(this@RegisterActivity).apply {
                        setTitle("Yeah!")
                        setMessage("Akun dengan ${request.email} sudah jadi nih. Yuk, login dan belajar coding.")
                        setPositiveButton("Lanjut") { _, _ ->
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                        }
                        create()
                        show()
                    }
//                    if(response.error == false) {
//                        val message = response.message
//                        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
//                        intent.putExtra("message", message)
//                        startActivity(intent)
//                    }
                } catch(e:HttpException) {
                    val errorMessage = "Registrasi Gagal: ${e.message()}"
                    Log.e("RegisterActivity", errorMessage)
                    runOnUiThread {
                        Toast.makeText(this@RegisterActivity, "Registrasi Gagal", Toast.LENGTH_SHORT).show()
                    }
                }
            }


        }
        binding.textButtonLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
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
        val nameTextView =
            ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(100)
        val nameEditTextLayout =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val signup = ObjectAnimator.ofFloat(binding.protectedButton, View.ALPHA, 1f).setDuration(100)


        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                signup
            )
            startDelay = 100
        }.start()
    }
}
