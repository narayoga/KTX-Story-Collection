package com.example.talesoftheunknown.data.Model.Auth

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)