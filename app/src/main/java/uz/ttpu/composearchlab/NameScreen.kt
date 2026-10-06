package uz.ttpu.composearchlab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Stateless: receives state (name), reports events (onNameChange). No remember inside.
@Composable
fun NameField(
    name: String,
    onNameChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("Name") },
        modifier = modifier
    )
}

// Stateful: owns the state and passes it down to NameField.
@Composable
fun NameScreen(modifier: Modifier = Modifier) {
    // rememberSaveable stores the value in a Bundle, so it survives rotation;
    // remember is lost because the Activity and composition are recreated.
    var name by rememberSaveable { mutableStateOf("") }
    Column(modifier.padding(24.dp)) {
        NameField(name = name, onNameChange = { name = it })
        Text("Hello, $name!")
    }
}

@Preview(showBackground = true)
@Composable
private fun NameFieldPreview() {
    NameField(name = "Amin", onNameChange = {})
}