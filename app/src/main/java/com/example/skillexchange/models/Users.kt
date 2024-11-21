package com.example.skillexchange.models

import android.os.Parcel
import android.os.Parcelable

data class Users (
    val photoUrl: String? = "",
    val status: String? = "",
    val userId: String? = "",
    val name: String? = "",
    val userEmail: String? = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(photoUrl)
        parcel.writeString(status)
        parcel.writeString(userId)
        parcel.writeString(name)
        parcel.writeString(userEmail)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Users> {
        override fun createFromParcel(parcel: Parcel): Users {
            return Users(parcel)
        }

        override fun newArray(size: Int): Array<Users?> {
            return arrayOfNulls(size)
        }
    }
}
