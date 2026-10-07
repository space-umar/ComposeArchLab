package uz.ttpu.composearchlab.ui.signin

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    // _uiState is private and mutable, uiState is read-only: the state can be
    // changed in only one place - the ViewModel (state encapsulation).
    private val _uiState = mutableStateOf<SignInUiState>(SignInUiState.SignedOut)
    val uiState: State<SignInUiState>
        get() = _uiState

    fun onSignIn(email: String, password: String) {
        // 1: double-tap protection
        if (_uiState.value is SignInUiState.InProgress) return
        // 2: show progress, start coroutine
        _uiState.value = SignInUiState.InProgress
        viewModelScope.launch {
            // 3: simulate network
            delay(1500)
            // 4: hard-coded only because this is a simulation
            _uiState.value = if (password == "kotlin123") {
                SignInUiState.SignedIn(email)
            } else {
                SignInUiState.Error("Wrong email or password")
            }
        }
    }

    // Task 9: "the Snackbar was shown" is an event; reset the state
    fun onErrorShown() {
        if (_uiState.value is SignInUiState.Error) {
            _uiState.value = SignInUiState.SignedOut
        }
    }
}