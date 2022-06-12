package com.picpay.desafio.android.Domain.model

import android.os.Parcelable
import com.picpay.desafio.android.Data.model.User
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class UserDomain(
    val img: String,
    val name: String,
    val id: Int,
    val username: String
) : Parcelable

fun UserDomain.toRemote(): User {
    return User(
        img = this.img,
        name = this.name,
        id = this.id,
        username = this.username
    )
}