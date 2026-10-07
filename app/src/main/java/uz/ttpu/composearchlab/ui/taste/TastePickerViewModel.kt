package uz.ttpu.composearchlab.ui.taste

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TastePickerViewModel : ViewModel() {

    // Private and mutable: only the ViewModel can change the state.
    // Public and read-only: the UI can only observe it.
    private val _state = MutableStateFlow(TastePickerState(artists = seedArtists))
    val state: StateFlow<TastePickerState> = _state.asStateFlow()

    // Selecting the already-selected genre clears the filter (selectedGenre = null).
    fun onGenreClick(genre: String) {
        _state.update {
            it.copy(selectedGenre = if (it.selectedGenre == genre) null else genre)
        }
    }

    // Adds the id if it is absent, removes it if it is present.
    // The + and - operators create a new Set, so the old one is never mutated.
    fun onArtistLikeToggled(id: Int) {
        _state.update {
            it.copy(
                likedIds = if (id in it.likedIds) it.likedIds - id else it.likedIds + id
            )
        }
    }
}