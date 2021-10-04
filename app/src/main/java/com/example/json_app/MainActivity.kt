package com.example.json_app

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.json_app.currencies.Datum
import com.example.json_app.APIInterface
import com.example.json_app.APIClient

class MainActivity : AppCompatActivity() {
    private var curr: currencies? = null

    lateinit var userinput: EditText
    lateinit var ddm: Spinner
    lateinit var convert: Button
    lateinit var response: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userinput = findViewById(R.id.userinput)
        ddm = findViewById(R.id.ddm)
        convert = findViewById(R.id.button)
        response = findViewById(R.id.textView3)

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val call: Call<currencies>? = apiInterface!!.getCurrency()

        var currency = arrayListOf("sar", "doge", "usd", "aud", "jpy", "cny")

        var selected: Int = 0

        if (ddm != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currency)
            ddm.adapter = adapter

            ddm.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    selected = position
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    Toast.makeText(applicationContext, "one choice must be selected",Toast.LENGTH_SHORT).show()
                }
            }
        }

        convert.setOnClickListener {

            var sel = userinput.text.toString()
            var currency: Double = sel.toDouble()

            getCurrency(onResult = {
                curr = it
                when (selected) {
                    0 -> display(calc(curr?.eur?.sar?.toDouble(), currency));
                    1 -> display(calc(curr?.eur?.doge?.toDouble(), currency));
                    2 -> display(calc(curr?.eur?.usd?.toDouble(), currency));
                    3 -> display(calc(curr?.eur?.aud?.toDouble(), currency));
                    4 -> display(calc(curr?.eur?.jpy?.toDouble(), currency));
                    5 -> display(calc(curr?.eur?.cny?.toDouble(), currency));
                }
            })
        }

    }
/////////////////////////////////////////////////////////////////////////////
    @SuppressLint("SetTextI18n")
    private fun display(calc: Double) { response.text = "= $calc" }

    private fun calc(i: Double?, sel: Double): Double {
        var s = 0.0
        if (i != null) {
            s = (i * sel)
        }
        return s
    }

    private fun getCurrency(onResult: (currencies?) -> Unit) {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        if (apiInterface != null) {
            apiInterface.getCurrency()?.enqueue(object : Callback<currencies> {
                override fun onResponse(
                    call: Call<currencies>,
                    response: Response<currencies>
                ) {
                    onResult(response.body())

                }

                override fun onFailure(call: Call<currencies>, t: Throwable) {
                    onResult(null)
                    Toast.makeText(applicationContext, "" + t.message, Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

}