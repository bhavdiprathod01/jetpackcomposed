package com.app.myapplication
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login_screen") {
        composable("login_screen") {
            LoginScreen(navController = navController)
        }

        composable("otp_screen/{mobile}") { backStackEntry ->
            val mobile = backStackEntry.arguments?.getString("mobile") ?: ""
            OTPScreen(navController = navController, mobile = mobile)
        }

        composable("home") {
            // Home screen composable goes here
        }
    }
}
