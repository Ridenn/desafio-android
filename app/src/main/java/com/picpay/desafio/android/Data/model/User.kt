package com.picpay.desafio.android.Data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.picpay.desafio.android.Domain.model.UserDomain
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("img")
    val img: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("id")
    val id: Int,
    @SerializedName("username")
    val username: String? = null
) : Parcelable

fun User.toDomain(): UserDomain =
    UserDomain(
        img = this.img,
        name = this.name,
        id = this.id,
        username = this.username
    )
