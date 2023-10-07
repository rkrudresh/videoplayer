package c.m.videoplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import c.m.videoplayer.ui.theme.VideoplayerTheme
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VideoplayerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VideoPlayer()
                }
            }
        }
    }
}

@Composable
fun VideoPlayer() {
    val videoUrl = "android.resource://${LocalContext.current.packageName}/${R.raw.nature}" // Replace with the actual resource name
    val context = LocalContext.current
    val player = remember { SimpleExoPlayer.Builder(context).build() }
    val playerView = remember { PlayerView(context) }
    val playWhenReady by remember { mutableStateOf(true) }

    val mediaItem = remember { com.google.android.exoplayer2.MediaItem.fromUri(videoUrl) }

    DisposableEffect(Unit) {
        player.setMediaItem(mediaItem)
        playerView.player = player
        player.playWhenReady = playWhenReady
        player.prepare()

        onDispose {
            player.stop()
            player.release()
        }
    }

    AndroidView(factory = { playerView })
}
