package com.example.meowfacts.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meowfacts.model.MeowFactResponse
import com.example.meowfacts.services.ApiConfig
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

 class MainViewModel() : ViewModel() {

    private val _meowFact = MutableLiveData<MeowFactResponse>()
    val meowFactData: LiveData<MeowFactResponse> get() = _meowFact

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    var errorMessage: String = ""
        private set

    fun getMeowFact() {

        _isLoading.value = true
        _isError.value = false

        val client = ApiConfig.getApiService().getMeowFacts()

        // Send API request using Retrofit
        client.enqueue(object : Callback<MeowFactResponse> {

            override fun onResponse(
                call: Call<MeowFactResponse>,
                response: Response<MeowFactResponse>
            ) {
                val responseBody = response.body()
                if (!response.isSuccessful || responseBody == null) {
                    onError("Data Processing Error")
                    return
                }

                _isLoading.value = false
                _meowFact.postValue(responseBody)
            }

            override fun onFailure(call: Call<MeowFactResponse>, t: Throwable) {
                onError(t.message)
                t.printStackTrace()
            }

        })
    }

    private fun onError(inputMessage: String?) {

        val message = if (inputMessage.isNullOrBlank() or inputMessage.isNullOrEmpty()) "Unknown Error"
        else inputMessage

        errorMessage = StringBuilder("ERROR: ")
            .append("$message some data may not displayed properly").toString()

        _isError.value = true
        _isLoading.value = false
    }

}