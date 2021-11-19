package com.example.examenparcial3

import android.graphics.BitmapFactory
import android.util.Base64
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.android.volley.Response
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var nameTxt: EditText
    private lateinit var firstLastnameTxt: EditText
    private lateinit var secondLastnameTxt: EditText
    private lateinit var addressTxt: EditText
    private lateinit var personalImageView: ImageView
    private lateinit var bloodTxt: EditText
    private lateinit var gpsButton: Button

    private var longitud: String? = null
    private var latitude: String? = null
    private var picture: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        nameTxt = findViewById(R.id.nameTxt)
        firstLastnameTxt = findViewById(R.id.firstLastnameTxt)
        secondLastnameTxt = findViewById(R.id.secondLastnameTxt)
        addressTxt = findViewById(R.id.addressTxt)
        bloodTxt = findViewById(R.id.bloodTxt)
        personalImageView = findViewById(R.id.personalImageView)
        gpsButton = findViewById(R.id.gpsButton)


        val url = "https://gist.githubusercontent.com/AlfredoGarciaYapor/29dba5a081cf50473cd25083245f64c5/raw/62e593c19e225b7b4439d1faf2ab1c1a7e145e62/personalInfo.json"
        val queue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(Request.Method.GET, url, Response.Listener { response ->
            Log.d("response", "Este es el response obtenido ${response}")

            val contact = Gson().fromJson(response.toString(), info::class.java)

            nameTxt.setText(contact.name)
            firstLastnameTxt.setText(contact.firstLastname)
            secondLastnameTxt.setText(contact.secondLastname)
            addressTxt.setText(contact.address)
            bloodTxt.setText(contact.blood)
            latitude = contact.latitud
            longitud = contact.longitud
            picture = contact.picture

            val profilePicture = picture.toString()
            val base64Image: String = profilePicture.split(",").get(1)
            val decodedString = Base64.decode(base64Image, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            personalImageView.setImageBitmap(decodedByte)


        },
                Response.ErrorListener {}
        )

        queue.add(stringRequest)

        gpsButton.setOnClickListener{
            val intent = Intent(this,  MapActivity::class.java)
            Log.d("Button", "Se toco el boton de WebServicesActivity")
            intent.putExtra("Coordenates",  (latitude.toString() +"," +longitud.toString())  )
            startActivity(intent)
        }
    }
}