import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.pfe.connectcoach.App

// Desktop preview: runs the app in a phone-sized window (no emulator needed).
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Connect with Your Coach",
        state = rememberWindowState(width = 420.dp, height = 880.dp),
    ) { App() }
}
