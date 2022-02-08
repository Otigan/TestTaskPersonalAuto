package com.example.personalauto.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Year(val id: Int, val year: String): Parcelable
