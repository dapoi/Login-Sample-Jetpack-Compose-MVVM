package com.dapoi.salttest.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dapoi.salttest.navigation.Screen
import com.dapoi.salttest.ui.DataStoreViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
    dataStoreViewModel: DataStoreViewModel = hiltViewModel()
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var isEmailValid by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            text = "Login Here",
            fontSize = 40.sp,
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.primary
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            value = email,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email"
                )
            },
            onValueChange = {
                email = it
                isEmailValid = it.contains("@")
            },
            label = { Text("Email") },
            isError = if (email.isEmpty()) false else !isEmailValid,
        )
        if (!isEmailValid && email.isNotEmpty()) {
            Text(
                text = "Please enter a valid email address",
                color = MaterialTheme.colors.error,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 1.dp),
            value = password,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password"
                )
            },
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        imageVector = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "Show password"
                    )
                }
            },
            onValueChange = {
                password = it
                isPasswordValid = it.length >= 6
            },
            label = { Text("Password") },
            isError = if (password.isEmpty()) false else !isPasswordValid,
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )
        if (!isPasswordValid && password.isNotEmpty()) {
            Text(
                text = "Password must be at least 6 characters",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                color = MaterialTheme.colors.error,
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            onClick = {
                val state = loginViewModel.loginState.value
                if (state.loginResponse.token.isNotEmpty()) {
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.LoginScreen.route) { inclusive = true }
                    }
                }
                keyboardController?.hide()
                loginViewModel.login(email, password)
            },
            enabled = isEmailValid && isPasswordValid,
        ) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.height(8.dp))
        loginViewModel.loginState.collectAsState().value.let { response ->
            response.apply {
                when {
                    isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.CenterHorizontally),
                            color = MaterialTheme.colors.primary
                        )
                    }
                    error.isNotEmpty() -> {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            text = error,
                            color = MaterialTheme.colors.error,
                            fontSize = 14.sp
                        )
                    }
                    loginResponse.token.isNotEmpty() -> {
                        dataStoreViewModel.apply {
                            saveToken(loginResponse.token)
                            saveEmail(email)
                            isLogin(true)
                        }
                    }
                }
            }
        }
    }
}
