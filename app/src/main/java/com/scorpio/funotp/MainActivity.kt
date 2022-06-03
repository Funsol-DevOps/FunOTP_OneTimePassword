package com.scorpio.funotp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.scorpio.funotp.databinding.ActivityMainBinding
import com.scorpio.otpsdk.model.OtpRequest
import com.scorpio.otpsdk.viewModels.OTPViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Sending OTP")
            .setMessage("Please wait while we send the OTP.")
            .setCancelable(false)
            .create()

        val subject = "this is test subject"
        val email = ""
        val upper = "Hello!\nThis is your otp"
        val lower = "Regards!\nOTP Sender App"

        val authToken = ""

        val otpViewModel = ViewModelProvider(this)[OTPViewModel::class.java]
        otpViewModel.otpResendRemainingTime.observe(this) {
            Log.i("MyTimerLog", "onCreate: $it")
        }

        val requestServer = OtpRequest(subject, upper, lower)

        with(binding) {
            btnSendOtp.setOnClickListener {
                alertDialog.show()
                otpViewModel.sendOtp(authToken, email, requestServer) {
                    if (it)
                        Toast.makeText(this@MainActivity, "otp sent!", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(this@MainActivity, "otp not sent, Please try again!", Toast.LENGTH_SHORT).show()

                    alertDialog.dismiss()
                }
            }

            btnVerifyOtp.setOnClickListener {
                if (otpViewModel.verifyOtp(edReceiveOtp.text.trim().toString()))
                    Toast.makeText(this@MainActivity, "otp verified", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this@MainActivity, "otp not verified", Toast.LENGTH_SHORT).show()
            }
        }

    }
}