package com.example.json_app

import retrofit2.http.GET
import com.example.json_app.currencies.Datum
import retrofit2.Call

interface APIInterface {

        @GET("eur.json")
        fun getCurrency(): Call<currencies>?
}