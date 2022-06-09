package com.picpay.desafio.android.Data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.picpay.desafio.android.Domain.model.UserDomain
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("img") val img: String,
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String
) : Parcelable

fun User.toDomain(): UserDomain {
    return UserDomain(
        img = this.img,
        name = this.name,
        id = this.id,
        username = this.username
    )
}