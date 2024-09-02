package dev.pablorjd.firebasecourse

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.pablorjd.firebasecourse.ui.theme.FirebaseCourseTheme

class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navHostController = rememberNavController()
            FirebaseCourseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    NavigationWrapper(navHostController, auth, it)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currenUser = auth.currentUser

        if(currenUser != null) {
            Log.i("Auth", "Session Iniciada")
            //navHostController.navigate("home")
            auth.signOut()
        } else {
            Log.i("Auth", "Session Cerrada")
            //navHostController.navigate("login")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        auth.signOut()
    }
}
