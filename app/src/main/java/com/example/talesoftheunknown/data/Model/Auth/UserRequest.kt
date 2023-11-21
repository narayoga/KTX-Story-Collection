package com.example.talesoftheunknown.data.Model.Auth

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @field:SerializedName("email")
    var email: String,
    @field:SerializedName("password")
    var password: String
)
