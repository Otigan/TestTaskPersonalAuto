package com.example.personalauto.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Manufacturer(val id: Int, val name: String) : Parcelable
