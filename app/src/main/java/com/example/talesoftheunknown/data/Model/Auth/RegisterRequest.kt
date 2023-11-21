package com.example.talesoftheunknown.data.Model.Auth

import android.provider.ContactsContract.CommonDataKinds.Email
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @field:SerializedName("name")
    var name: String,
    @field:SerializedName("email")
    var email: String,
    @field:SerializedName("password")
    var password: String
)
