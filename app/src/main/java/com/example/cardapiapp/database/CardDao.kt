package com.example.cardapiapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cardapiapp.model.CardDTOItem
import com.example.cardapiapp.util.Constants

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCard(cardDTOItem: CardDTOItem)

    @Insert
    fun saveCardList(cardList: List<CardDTOItem>)

    @Update
    fun updateCard(cardDTOItem: CardDTOItem)

    @Delete
    fun deleteCard(cardDTOItem: CardDTOItem)

    @Query("SELECT * FROM ${Constants.TABLE_NAME}")
    fun getAllCardList(): List<CardDTOItem>

    @Query("DELETE FROM ${Constants.TABLE_NAME}")
    fun deleteAllCards()
}