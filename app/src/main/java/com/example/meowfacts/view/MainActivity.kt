package com.example.meowfacts.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.meowfacts.R
import com.example.meowfacts.model.MeowFactResponse
import com.example.meowfacts.viewmodel.MainViewModel
import com.example.meowfacts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
     private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        //set view model
        binding.viewModel = mainViewModel

        //set lifecycle owner
        binding.lifecycleOwner = this
        subscribe()
        }

    private fun subscribe() {
        mainViewModel.isLoading.observe(this) { isLoading ->
            // Set the result text to Loading
            if (isLoading) binding.tvResult.text = resources.getString(R.string.loading)
        }

        mainViewModel.isError.observe(this) { isError ->
            // Hide display image and set the result text to the error message
            if (isError) {
             binding.tvResult.text = mainViewModel.errorMessage
            }
        }

        mainViewModel.meowFactData.observe(this) { facts ->
            // Display MeowFact data to the UI
            setResultText(facts)
        }
    }

    private fun setResultText(factsResponse: MeowFactResponse) {
     binding.tvResult.text = factsResponse.data[0]
    }

}