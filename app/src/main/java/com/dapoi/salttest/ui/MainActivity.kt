package com.dapoi.salttest.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dapoi.salttest.navigation.Screen
import com.dapoi.salttest.ui.home.HomeScreen
import com.dapoi.salttest.ui.login.LoginScreen
import com.dapoi.salttest.ui.theme.LoginSampleUsingJetpackComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val dataStoreViewModel: DataStoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStoreViewModel.isLoggedIn.observe(this) { isLoggedIn ->
            setContent {
                LoginSampleUsingJetpackComposeTheme {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = if (isLoggedIn) Screen.HomeScreen.route else Screen.LoginScreen.route
                    ) {
                        composable(Screen.LoginScreen.route) {
                            LoginScreen(navController)
                        }
                        composable(Screen.HomeScreen.route) {
                            HomeScreen(navController)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DefaultPreview() {
    LoginSampleUsingJetpackComposeTheme {
        LoginScreen(navController = rememberNavController())
    }
}