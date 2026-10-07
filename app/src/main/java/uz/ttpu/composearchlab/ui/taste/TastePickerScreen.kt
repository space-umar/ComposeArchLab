package uz.ttpu.composearchlab.ui.taste

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import uz.ttpu.composearchlab.ui.theme.ComposeArchLabTheme

@Composable
fun TastePickerRoute(viewModel: TastePickerViewModel = viewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    TastePickerScreen(
        state = state,
        onGenreClick = viewModel::onGenreClick,
        onLikeClick = viewModel::onArtistLikeToggled
    )
}

@Composable
fun TastePickerScreen(
    state: TastePickerState,
    onGenreClick: (String) -> Unit,
    onLikeClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            TastePickerBottomBar(
                likedCount = state.likedCount,
                required = REQUIRED_LIKES,
                canContinue = state.canContinue,
                onContinueClick = { Log.d("TastePicker", "Continue clicked") },
                onSkipClick = { Log.d("TastePicker", "Later clicked") }
            )
        }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            GenreChips(
                genres = state.genres,
                selectedGenre = state.selectedGenre,
                onGenreClick = onGenreClick
            )
            ArtistGrid(
                artists = state.visibleArtists,
                likedIds = state.likedIds,
                onLikeClick = onLikeClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TastePickerScreenPreview() = ComposeArchLabTheme {
    TastePickerScreen(
        state = TastePickerState(seedArtists, likedIds = setOf(1, 2)),
        onGenreClick = {},
        onLikeClick = {}
    )
}