package com.example.restaurtantchatbotapp



import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_about.*


class AboutClass : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_about)


        btnLocation.setOnClickListener(){
            Toast.makeText(this,"Google Map", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,MapsFragment::class.java)
            startActivity(intent)
        }

    }



}