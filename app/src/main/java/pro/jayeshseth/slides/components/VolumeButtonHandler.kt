package pro.jayeshseth.slides.components

import android.view.KeyEvent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

/**
 * handle hardware keys to navigate between slides.
 *
 * alternative for clicker
 */
@Composable
fun VolumeButtonsHandler(
    onVolumeUp: () -> Unit,
    onVolumeDown: () -> Unit
) {
    val context = LocalContext.current
    val view = LocalView.current

    DisposableEffect(context) {
        val keyEventDispatcher = ViewCompat.OnUnhandledKeyEventListenerCompat { _, event ->
            when {
                event.keyCode == KeyEvent.KEYCODE_VOLUME_UP &&
                        event.action == KeyEvent.ACTION_DOWN -> {
                    onVolumeUp()
                    true
                }

                event.keyCode == KeyEvent.KEYCODE_VOLUME_DOWN &&
                        event.action == KeyEvent.ACTION_DOWN -> {
                    onVolumeDown()
                    true
                }

                else -> {
                    false
                }
            }
        }

        ViewCompat.addOnUnhandledKeyEventListener(view, keyEventDispatcher)

        onDispose {
            ViewCompat.removeOnUnhandledKeyEventListener(view, keyEventDispatcher)
        }
    }
}
