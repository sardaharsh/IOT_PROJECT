package com.example.pblproject

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.io.InputStream
import java.util.*

class BluetoothConnectionActivity : AppCompatActivity() {

    private val BLUETOOTH_PERMISSION_REQUEST = 1
    private val HC05_MAC_ADDRESS = "98:D3:71:F5:DF:26" // Replace with your HC-05's MAC address
    private val UUID_STRING = "00001101-0000-1000-8000-00805F9B34FB" // Example UUID

    private lateinit var bluetoothSocket: BluetoothSocket
    private lateinit var inputStream: InputStream
    private lateinit var textDisplay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_connection)

        textDisplay = findViewById(R.id.text)

        // Initialize BluetoothAdapter
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

        if (bluetoothAdapter == null) {
            // Bluetooth is not supported on this device
            // Handle this situation accordingly
            Toast.makeText(this, "Bluetooth is not supported on this device", Toast.LENGTH_SHORT).show()
            return
        }

        // Check for Bluetooth permission
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request Bluetooth permission if not granted
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.BLUETOOTH),
                BLUETOOTH_PERMISSION_REQUEST
            )
            return
        }

        val hc05Device: BluetoothDevice? = bluetoothAdapter.getRemoteDevice(HC05_MAC_ADDRESS)
        val uuid: UUID = UUID.fromString(UUID_STRING)

        if (hc05Device != null) {
            // Create a BluetoothSocket
            try {
                bluetoothSocket = hc05Device.createRfcommSocketToServiceRecord(uuid)
                bluetoothSocket.connect()
                inputStream = bluetoothSocket.inputStream
                startReceivingData()
            } catch (e: IOException) {
                // Handle connection error
                Toast.makeText(this, "Bluetooth connection error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Device not found or Bluetooth is disabled
            // Handle this situation accordingly
            Toast.makeText(this, "Device not found or Bluetooth is disabled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startReceivingData() {
        val handler = Handler(Looper.getMainLooper())

        Thread {
            val buffer = ByteArray(1024) // Adjust the buffer size as needed
            while (true) {
                try {
                    val bytesRead = inputStream.read(buffer)
                    if (bytesRead != -1) {
                        val receivedData = String(buffer, 0, bytesRead, Charsets.UTF_8)
                        handler.post {
                            val textPulseRate = findViewById<TextView>(R.id.text)
                            textPulseRate.text = "Pulse Rate: $receivedData"
                        }
                    } else {
                        // Handle end of stream or other conditions
                        break
                    }
                } catch (e: IOException) {
                    // Handle data receiving error
                    handler.post {
                        Toast.makeText(this, "Error receiving data: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }.start()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == BLUETOOTH_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can now proceed with Bluetooth operations
                // You can put the Bluetooth connection code here if needed
            } else {
                // Permission denied, handle this case (e.g., show a message to the user)
                Toast.makeText(this, "Bluetooth permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            inputStream.close()
            bluetoothSocket.close()
        } catch (e: IOException) {
            // Handle socket or stream closing error
        }
    }
}
