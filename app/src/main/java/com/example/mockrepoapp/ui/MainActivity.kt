package com.example.mockrepoapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mockrepoapp.databinding.ActivityMainBinding
import com.example.mockrepoapp.network.ResponseState
import com.example.mockrepoapp.utils.ViewExtension.showHideView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setObserver()
    }

    private fun setObserver() {
        mainViewModel.getMemesLiveData.observe(this){ state ->
            when(state){
                is ResponseState.Loading -> {
                   binding.progressBar.showHideView(state.boolean)
                }
                is ResponseState.Success -> {
                    showToast("Successful")
                }
                is ResponseState.Error ->{
                    showToast(state.msg.name)
                }
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    private fun getMockDataAPI() {
        mainViewModel.getMockAPI()
    }


    override fun onResume() {
        super.onResume()
        getMockDataAPI()
    }
}