package com.example.pblproject

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.activity_main_home, container, false)

        val button = rootView.findViewById<View>(R.id.button_logout)
        button.setOnClickListener{
            Firebase.auth.signOut()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
        // Find the card view by its ID
        val cardViewPulseRate = rootView.findViewById<View>(R.id.card_view_pulse_rate)
        val cardViewTemperature = rootView.findViewById<View>(R.id.card_view_temperature)

        // Set an OnClickListener to the card view
        cardViewPulseRate.setOnClickListener {
            // Start the new activity here
            val intent = Intent(activity, PulseRateActivity::class.java)
            startActivity(intent)
        }
        cardViewTemperature.setOnClickListener {
            // Start the new activity here
            val intent = Intent(activity, Temperature::class.java)
            startActivity(intent)
        }
        return rootView
    }
}
