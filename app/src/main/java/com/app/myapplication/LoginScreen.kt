package com.app.myapplication

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.myapplication.ui.LoginState
import com.app.myapplication.ui.LoginViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val context = LocalContext.current
    var mobile by remember { mutableStateOf("") }
    val loginState by viewModel.loginState.collectAsState()

    HandleLoginState(
        loginState = loginState,
        mobile = mobile,
        navController = navController,
        viewModel = viewModel,
        context = context
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Show error message if there's an error state
            if (loginState is LoginState.Error) {
                ErrorMessageText((loginState as LoginState.Error).message)
                Spacer(modifier = Modifier.height(16.dp))
            }

            OutlinedTextField(
                value = mobile,
                onValueChange = { mobile = it },
                label = { Text("Mobile Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (mobile.isNotEmpty()) {
                        viewModel.loginUser(mobile)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = loginState !is LoginState.Loading
            ) {
                Text(text = if (loginState is LoginState.Loading) "Processing..." else "Login")
            }
        }

        // Show loading indicator in center when loading
        if (loginState is LoginState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                strokeWidth = 4.dp
            )
        }
    }
}

@Composable
private fun HandleLoginState(
    loginState: LoginState,
    mobile: String,
    navController: NavController,
    viewModel: LoginViewModel,
    context: Context
) {
    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Success -> {
                if (loginState.response.isSuccessful) {
                    Toast.makeText(context, "OTP Sent!", Toast.LENGTH_SHORT).show()
                    navController.navigate("otp_screen/$mobile") {
                        popUpTo("login_screen") { inclusive = true }
                    }
                    viewModel.resetState()
                }
            }
            else -> {}
        }
    }
}

@Composable
fun ErrorMessageText(errorMessage: String) {
    Text(
        text = errorMessage,
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.Bold
        )
    )
}