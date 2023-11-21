package com.example.talesoftheunknown.view.Auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    val passwordError = MutableLiveData<String>("")
    val passwordErrorVisible = MutableLiveData<Boolean>(false)

    fun validatePassword(password: String) {
        if (password.length < 8) {
            passwordError.value = "Password harus memiliki setidaknya 8 karakter"
            passwordErrorVisible.value = true
        } else {
            passwordError.value = ""
            passwordErrorVisible.value = false
        }
    }
}