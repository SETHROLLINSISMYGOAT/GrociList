package com.siddhant.grocilist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.siddhant.grocilist.ui.auth.OtpInputScreen
import com.siddhant.grocilist.ui.auth.PhoneInputScreen
import com.siddhant.grocilist.ui.cart.CartScreen
import com.siddhant.grocilist.ui.checkout.CheckoutScreen
import com.siddhant.grocilist.ui.home.HomeScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "phone_input") {
        composable("phone_input") {
            PhoneInputScreen(navController = navController)
        }
        composable(
            "otp/{verificationId}",
            arguments = listOf(navArgument("verificationId") { type = NavType.StringType })
        ) { backStackEntry ->
            val verificationId = backStackEntry.arguments?.getString("verificationId") ?: ""
            OtpInputScreen(verificationId = verificationId, navController = navController)
        }
        composable("home"){
            HomeScreen(navController=navController)
        }
        composable("cart"){
            CartScreen(navController=navController)
        }
        composable("checkout"){
            CheckoutScreen(navController=navController)
        }

    }
}