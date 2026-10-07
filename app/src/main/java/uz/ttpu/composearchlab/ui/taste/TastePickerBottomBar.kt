package uz.ttpu.composearchlab.ui.taste

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.ttpu.composearchlab.ui.theme.ComposeArchLabTheme

@Composable
fun TastePickerBottomBar(
    likedCount: Int,
    required: Int,
    canContinue: Boolean,
    onContinueClick: () -> Unit,
    onSkipClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        tonalElevation = 8.dp
    ) {
        Column(
            modifier = Modifier.navigationBarsPadding().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        progress = { (likedCount.toFloat() / required).coerceAtMost(1f) },
                        modifier = Modifier.size(48.dp)
                    )
                    Text("$likedCount")
                }
                Text("Pick $required artists you like")
            }
            Row(
                modifier = Modifier.align(Alignment.End),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextButton(onClick = onSkipClick) { Text("Later") }
                Button(onClick = onContinueClick, enabled = canContinue) { Text("Continue") }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomBar0() = ComposeArchLabTheme {
    TastePickerBottomBar(0, 3, false, {}, {})
}

@Preview(showBackground = true)
@Composable
private fun BottomBar2() = ComposeArchLabTheme {
    TastePickerBottomBar(2, 3, false, {}, {})
}

@Preview(showBackground = true)
@Composable
private fun BottomBar3() = ComposeArchLabTheme {
    TastePickerBottomBar(3, 3, true, {}, {})
}