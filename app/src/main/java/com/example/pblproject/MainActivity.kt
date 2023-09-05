package com.example.pblproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.pblproject.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        val rootview = binding.root
        setContentView(rootview)

        binding.buttonLogin.setOnClickListener {
            val intent = Intent(this,MainScreen::class.java)
            startActivity(intent)
        }

        val clickableTextView = binding.textviewClickable

        clickableTextView.setOnClickListener(View.OnClickListener {
            // Handle the click event here
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        })


    }
}