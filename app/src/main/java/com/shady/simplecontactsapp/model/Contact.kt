package com.shady.simplecontactsapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Contact(
    val name: String = "",
    val phone: String = ""
) : Parcelable