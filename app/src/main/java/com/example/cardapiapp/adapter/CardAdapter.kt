package com.example.cardapiapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cardapiapp.databinding.ItemLayoutBinding
import com.example.cardapiapp.model.CardDTOItem
import com.example.cardapiapp.util.Constants

class CardAdapter : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {
    private lateinit var context: Context

    var cardList = mutableListOf<CardDTOItem>()

    lateinit var onItemClick: (CardDTOItem, color: Int) -> Unit
    lateinit var onItemLongClick: (CardDTOItem, pos: Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        context = parent.context
        return CardViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(cardList[position])
    }

    override fun getItemCount(): Int = cardList.size

    inner class CardViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CardDTOItem) {
            val color = Constants.randomColor()
            binding.apply {
                cardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        color
                    )
                )
                textCardDate.text = "${item.cardDate1}/${item.cardDate2}"
                textCardHolderName.text = item.userName
                textCardName.text = item.bankName
                textCardNumbers.text = item.cardNumber
            }
            itemView.setOnClickListener {
                onItemClick(item, color)
            }
            itemView.setOnLongClickListener {
                onItemLongClick(item, adapterPosition)
                true
            }
        }
    }
}