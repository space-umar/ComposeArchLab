package uz.ttpu.composearchlab.ui.signin

// A sealed interface fits better than an enum because the cases carry different
// data (Error has a message, SignedIn has an email), which an enum cannot do.
// Impossible combinations (loading + error) cannot be expressed, and `when`
// is checked for exhaustiveness by the compiler.
sealed interface SignInUiState {
    data object SignedOut : SignInUiState
    data object InProgress : SignInUiState
    data class Error(val message: String) : SignInUiState
    data class SignedIn(val email: String) : SignInUiState
}