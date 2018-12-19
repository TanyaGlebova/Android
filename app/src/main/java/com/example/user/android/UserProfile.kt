package com.example.user.android


import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserProfile(
        var fullName: String = "",
        var phone: String = "")