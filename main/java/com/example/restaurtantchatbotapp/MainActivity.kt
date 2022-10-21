package com.example.restaurtantchatbotapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    //Intents from one activity to another using onClicklisteners buttons
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_00.setOnClickListener(){
            Toast.makeText(this,"Home Page", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,HomeClass::class.java)
            startActivity(intent)
        }
        button_01.setOnClickListener(){
            Toast.makeText(this,"Menu Page", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,MenuClass::class.java)
            startActivity(intent)
        }
        button_02.setOnClickListener(){
            Toast.makeText(this,"About us Page", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,AboutClass::class.java)
            startActivity(intent)
        }
        button_03.setOnClickListener(){
            Toast.makeText(this,"Chatbot Page", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ChatbotClass::class.java)
            startActivity(intent)
        }
    }


}