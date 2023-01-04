package com.dapoi.salttest.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.dapoi.salttest.navigation.Screen
import com.dapoi.salttest.ui.DataStoreViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    dataStoreViewModel: DataStoreViewModel = hiltViewModel()
) {
    var name = ""
    var token = ""
    val lifecycleOwner = LocalContext.current as LifecycleOwner

    dataStoreViewModel.apply {
        getEmail.observe(lifecycleOwner) {
            name = it
        }
        getToken.observe(lifecycleOwner) {
            token = it
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name,
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.h3,
            fontSize = 20.sp
        )
        Text(
            text = token,
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.h3,
            fontSize = 20.sp
        )
        Button(onClick = {
            dataStoreViewModel.clear()
            navController.popBackStack(Screen.LoginScreen.route, false)
        }) {
            Text(text = "Logout")
        }
    }
}