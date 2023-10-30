package com.example.kotlinmidproject.ui.home

// The F1 Api will only load 1 image per time, doesn't good for recyclerView
// I can on the response API (non 404 http code) but the data seems misisng
// Idk, maybe I can ask Pak Abdu Later
// Using the cat image seems a good option

import ApiService
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmidproject.R
import com.example.kotlinmidproject.databinding.FragmentHomeBinding
import com.example.kotlinmidproject.model.User
import com.example.kotlinmidproject.model.UserResponse
import com.example.kotlinmidproject.util.Constant.Companion.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var apiService: ApiService
    private lateinit var adapter: HomeAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        retrieveData()

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = HomeAdapter(emptyList())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun retrieveData() {
        apiService.getUsers().enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val users = response.body()?.data
                    users?.let { it ->
                        showUsers(it)
                    }
//                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showUsers(users: List<User>) {
        val recyclerView: RecyclerView = view?.findViewById(R.id.recycler_view) ?: return
        recyclerView.adapter = HomeAdapter(users)
    }


}