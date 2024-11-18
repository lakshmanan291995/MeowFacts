package com.example.meowfacts.model

import com.google.gson.annotations.SerializedName

public data class MeowFactResponse(

    @SerializedName("data" ) var data : ArrayList<String> = arrayListOf()

)

