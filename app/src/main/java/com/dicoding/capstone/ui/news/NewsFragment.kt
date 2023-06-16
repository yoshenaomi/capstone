package com.dicoding.capstone.ui.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.capstone.data.network.retrofit.ApiConfig
import com.dicoding.capstone.databinding.FragmentNewsBinding
import com.dicoding.capstone.model.NewsModel
import com.dicoding.capstone.ui.home.ListNewsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        val view = binding.root

        fetchNews()
        return view
    }

    private fun fetchNews() {
        val client = ApiConfig.getApiService().getNews()

        client.enqueue(object : Callback<List<NewsModel>> {
            override fun onResponse(
                call: Call<List<NewsModel>>,
                response: Response<List<NewsModel>>
            ) {
                if (response.isSuccessful) {
                    val newsModel = response.body()
                    newsModel?.let {
                        val data = it
                        setItemsData(data)
                    }
                }
            }

            override fun onFailure(call: Call<List<NewsModel>>, t: Throwable) {
                Log.e(tag, "On Failure : ${t.message}")
            }

        })
    }

    private fun setItemsData(data: List<NewsModel?>?) {
        val listItemData = ArrayList<NewsModel>()
        if (data != null) {
            for (i in data) {
                i?.let { listItemData.add(it) }
            }
        }
        val adapter = ListNewsAdapter(listItemData)
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.newsRecyclerView.adapter = adapter
    }
}