package dev.pablorjd.firebasecourse

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import dev.pablorjd.firebasecourse.presentation.home.HomeScreen
import dev.pablorjd.firebasecourse.presentation.initial.InitialScreen
import dev.pablorjd.firebasecourse.presentation.login.LoginScreen
import dev.pablorjd.firebasecourse.presentation.singup.SingupScreen

@Composable
fun NavigationWrapper(
    navHostController: NavHostController,
    auth: FirebaseAuth,
    paddingValues: PaddingValues
) {
    NavHost(navController = navHostController, startDestination = "home") {
        composable("initial") {
            InitialScreen(
                navigateToLogin = { navHostController.navigate("login") },
                navigateToSignUp = { navHostController.navigate("singup") }
            )
        }
        composable("login") {
            LoginScreen(auth,
                navigateToHome = { navHostController.navigate("home") }
            )
        }
        composable("singup") {
            SingupScreen(auth, navigateToHome = { navHostController.navigate("home") })
        }
        composable("home") {
            HomeScreen(paddingValues)
        }
    }
}