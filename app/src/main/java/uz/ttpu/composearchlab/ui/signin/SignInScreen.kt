package uz.ttpu.composearchlab.ui.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import uz.ttpu.composearchlab.ui.theme.ComposeArchLabTheme

@Composable
fun SignInRoute(viewModel: SignInViewModel = viewModel()) {
    val uiState = viewModel.uiState.value
    val snackbarHostState = remember { SnackbarHostState() }

    // BUG (Task 8): after a rotation the Snackbar appears again.
    // Why: the ViewModel survives the rotation and still holds the Error state.
    // The Activity and the composition are recreated, so this LaunchedEffect starts
    // again, sees Error, and shows the same message a second time.
    LaunchedEffect(uiState) {
        if (uiState is SignInUiState.Error) {
            snackbarHostState.showSnackbar(uiState.message)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        SignInScreen(
            uiState = uiState,
            onSignIn = viewModel::onSignIn,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun SignInScreen(
    uiState: SignInUiState,
    onSignIn: (email: String, password: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        when (uiState) {
            is SignInUiState.SignedIn -> Text("Welcome, ${uiState.email}")
            SignInUiState.InProgress -> {
                SignInForm(
                    email, { email = it }, password, { password = it },
                    enabled = false,
                    onSignIn = { onSignIn(email, password) }
                )
                CircularProgressIndicator()
            }
            SignInUiState.SignedOut,
            is SignInUiState.Error -> {
                SignInForm(
                    email, { email = it }, password, { password = it },
                    enabled = true,
                    onSignIn = { onSignIn(email, password) }
                )
            }
        }
    }
}

@Composable
private fun SignInForm(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    enabled: Boolean,
    onSignIn: () -> Unit
) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Email") },
        singleLine = true
    )
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Password") },
        singleLine = true,
        visualTransformation = PasswordVisualTransformation()
    )
    Button(onClick = onSignIn, enabled = enabled) {
        Text("Sign in")
    }
}

@Preview(showBackground = true)
@Composable
private fun SignedOutPreview() = ComposeArchLabTheme {
    SignInScreen(SignInUiState.SignedOut, { _, _ -> })
}

@Preview(showBackground = true)
@Composable
private fun InProgressPreview() = ComposeArchLabTheme {
    SignInScreen(SignInUiState.InProgress, { _, _ -> })
}

@Preview(showBackground = true)
@Composable
private fun ErrorPreview() = ComposeArchLabTheme {
    SignInScreen(SignInUiState.Error("Wrong email or password"), { _, _ -> })
}

@Preview(showBackground = true)
@Composable
private fun SignedInPreview() = ComposeArchLabTheme {
    SignInScreen(SignInUiState.SignedIn("amin@mail.uz"), { _, _ -> })
}