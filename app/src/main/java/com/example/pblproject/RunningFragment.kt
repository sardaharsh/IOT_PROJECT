
package com.example.pblproject

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class RunningFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.activity_main_home, container, false)

        // Find the card view by its ID
        val cardView = rootView.findViewById<View>(R.id.card_view_pulse_rate)

        // Set an OnClickListener to the card view
        cardView.setOnClickListener {
            // Start the new activity here
            val intent = Intent(activity, BluetoothConnectionActivity::class.java)
            startActivity(intent)
        }

        return rootView
    }
}
