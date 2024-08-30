package dev.pablorjd.firebasecourse

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.pablorjd.firebasecourse.presentation.initial.InitialScreen
import dev.pablorjd.firebasecourse.presentation.login.LoginScreen
import dev.pablorjd.firebasecourse.presentation.singup.SingupScreen

@Composable
fun NavigationWrapper(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = "initial") {
        composable("initial") {
            InitialScreen()
        }
        composable("login") {
            LoginScreen()
        }
        composable("singup") {
            SingupScreen()
        }
    }
}