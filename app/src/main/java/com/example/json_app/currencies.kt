package com.example.json_app

import com.google.gson.annotations.SerializedName

class currencies {

        @SerializedName("date")
        var date: String? = null

        @SerializedName("eur")
        var eur: Datum? = null

    class Datum {

              @SerializedName("sar")
              var sar: String? = null

              @SerializedName("doge")
              var doge: String? = null

              @SerializedName("usd")
              var usd: String? = null

              @SerializedName("aud")
              var aud: String? = null

              @SerializedName("jpy")
              var jpy: String? = null

              @SerializedName("cny")
              var cny: String? = null

           }

    }




