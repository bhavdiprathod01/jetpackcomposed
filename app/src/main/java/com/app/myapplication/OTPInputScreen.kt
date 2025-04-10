package com.app.myapplication

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.app.myapplication.ui.LoginViewModel

@Composable
fun OTPScreen(
    navController: NavController,
    mobile: String,
    viewModel: LoginViewModel = viewModel()
) {
    var otp by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Observe the login response
//    val otpResponse = viewModel.loginResponse  // Assuming the OTP response is in the viewModel
//
//    LaunchedEffect(otpResponse) {
//        otpResponse?.let {
//            if (it.isSuccessful) {
//                Toast.makeText(navController.context, "OTP Verified!", Toast.LENGTH_SHORT).show()
//                navController.navigate("home")  // Navigate to home screen on successful OTP verification
//            } else {
//                errorMessage = "OTP verification failed. Please try again."
//            }
//            isLoading = false
//        }
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        errorMessage?.let {
            ErrorMessageText(it)  // Show error message if OTP verification fails
        }

        Spacer(modifier = Modifier.height(20.dp))

        ErrorMessageText("Enter OTP sent to $mobile")

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = otp,
            onValueChange = { otp = it },
            label = { Text("OTP") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (otp.isNotBlank()) {
                    isLoading = true
                    errorMessage = null
                    viewModel.verifyOtp(mobile, otp)  // Trigger OTP verification
                } else {
                    errorMessage = "Please enter the OTP."
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text(text = if (isLoading) "Verifying OTP..." else "Verify OTP")
        }
    }
}
