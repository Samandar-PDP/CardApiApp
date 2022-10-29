package com.example.cardapiapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardapiapp.R
import com.example.cardapiapp.adapter.CardAdapter
import com.example.cardapiapp.database.CardDatabase
import com.example.cardapiapp.databinding.ActivityMainBinding
import com.example.cardapiapp.model.CardDTO
import com.example.cardapiapp.model.CardDTOItem
import com.example.cardapiapp.network.RetroInstance
import com.example.cardapiapp.util.NetworkUtils
import com.example.cardapiapp.util.error
import com.example.cardapiapp.util.success
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val cardDatabase by lazy { CardDatabase(this) }
    private val cardAdapter by lazy { CardAdapter() }
    private lateinit var networkUtils: NetworkUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        networkUtils = NetworkUtils(this)
        setupRv()
        if (networkUtils.isNetworkConnected()) {
            getFromApi()
            success("From Api")
        } else {
            getFromDatabase()
            success("From Database")
        }
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            cardAdapter.notifyDataSetChanged()
        }
        binding.fab.setOnClickListener {
            if (networkUtils.isNetworkConnected()) {
                startActivity(Intent(this, AddUpdateActivity::class.java))
            } else {
                error("Check internet connection")
            }
        }
        cardAdapter.onItemClick = { item, color ->
            if (networkUtils.isNetworkConnected()) {
                val bundle = bundleOf("card" to item)
                val intent = Intent(this, AddUpdateActivity::class.java)
                intent.putExtra("color", color)
                intent.putExtras(bundle)
                startActivity(intent)
            } else {
                error("Check internet connection")
            }
        }
        cardAdapter.onItemLongClick = { item, pos ->
            deleteCard(item, pos)
        }
    }

    private fun deleteCard(item: CardDTOItem, pos: Int) {
        cardDatabase.dao.deleteCard(item)
        cardAdapter.notifyItemRemoved(pos)
        cardAdapter.cardList.removeAt(pos)
        RetroInstance.apiService.deleteCard(item.id).enqueue(object : Callback<CardDTOItem> {
            override fun onResponse(call: Call<CardDTOItem>, response: Response<CardDTOItem>) {
                if (response.isSuccessful) {
                    success("Successfully deleted")
                }
            }

            override fun onFailure(call: Call<CardDTOItem>, t: Throwable) {
                error(t.message.toString())
            }
        })
    }

    private fun getFromApi() {
        RetroInstance.apiService.getAllCardList().enqueue(object : Callback<CardDTO> {
            override fun onResponse(call: Call<CardDTO>, response: Response<CardDTO>) {
                if (response.isSuccessful) {
                    binding.progressBar.isVisible = false
                    cardAdapter.cardList = response.body()!!
                    cardDatabase.dao.deleteAllCards()
                    cardDatabase.dao.saveCardList(response.body()!!)
                    cardAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<CardDTO>, t: Throwable) {
                error(t.message.toString())
            }
        })
    }

    private fun getFromDatabase() {
        cardAdapter.cardList = cardDatabase.dao.getAllCardList().toMutableList()
        binding.progressBar.isVisible = false
        cardAdapter.notifyDataSetChanged()
    }

    private fun setupRv() = binding.recyclerView.apply {
        adapter = cardAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

    override fun onRestart() {
        super.onRestart()
        getFromApi()
    }
}