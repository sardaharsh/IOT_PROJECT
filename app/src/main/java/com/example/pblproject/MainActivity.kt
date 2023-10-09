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

    private lateinit var auth: FirebaseAuth;
    private lateinit var binding: ActivityMainBinding

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this,MainScreen::class.java)
            startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        val rootview = binding.root
        setContentView(rootview)
        auth= FirebaseAuth.getInstance();

        val editTextEmail = binding.textviewEmail
        val editTextPassword = binding.textviewPassword

        binding.buttonLogin.setOnClickListener {
            val password : String = editTextPassword.text.toString()
            val email : String = editTextEmail.text.toString()

            if(TextUtils.isEmpty(email)){
                Toast.makeText(this,"enter email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(password)){
                Toast.makeText(this,"enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        Toast.makeText(
                            baseContext,
                            "Authentication successful.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        val intent = Intent(this,MainScreen::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }


        }

        val clickableTextView = binding.textviewClickable

        clickableTextView.setOnClickListener(View.OnClickListener {
            // Handle the click event here
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        })


    }
}