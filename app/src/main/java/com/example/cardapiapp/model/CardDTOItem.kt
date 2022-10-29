package com.example.cardapiapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cardapiapp.util.Constants
import kotlinx.parcelize.Parcelize

@Entity(tableName = Constants.TABLE_NAME)
@Parcelize
data class CardDTOItem(
    @PrimaryKey(autoGenerate = true)
    val _id: Int,
    val bankName: String,
    val cardDate1: String,
    val cardDate2: String,
    val cardNumber: String,
    val id: String,
    val userName: String,
    val cvv: String
): Parcelable