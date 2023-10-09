package com.example.pblproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.pblproject.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth;
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        val rootview = binding.root
        setContentView(rootview)
        mAuth= FirebaseAuth.getInstance();

        val editTextEmail = binding.textviewEmail
        val editTextPassword = binding.textviewPassword

        binding.buttonLogin.setOnClickListener {
            var password : String = editTextPassword.text.toString()
            var email : String = editTextEmail.text.toString()

            if(TextUtils.isEmpty(email)){
                Toast.makeText(this,"enter email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

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