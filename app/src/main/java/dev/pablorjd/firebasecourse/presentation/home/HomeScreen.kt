package dev.pablorjd.firebasecourse.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.pablorjd.firebasecourse.R
import dev.pablorjd.firebasecourse.presentation.model.Artist
import dev.pablorjd.firebasecourse.presentation.model.Player
import dev.pablorjd.firebasecourse.ui.theme.Black

//@Preview(showBackground = true, showSystemUi = true, name = "Home Screen")
@Composable
fun HomeScreen(paddingValues: PaddingValues, viewModel: HomeViewModel = HomeViewModel()) {

    val artists: State<List<Artist>> = viewModel.artists.collectAsState()
    val player by viewModel.player.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Black),
        //horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Popular Artist",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(16.dp)
        )



        LazyRow() {
            items(artists.value) {
                ArtistItem(artist = it, onItemSelected = { viewModel.addPlayer(it) })
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        player?.let {
            PlayerItem(player = it, onCancelSelected = { viewModel.onCancelSelected() },
                onPlaySelected = { viewModel.onPlay() })
        }
    }
}

@Composable
fun PlayerItem(player: Player, onPlaySelected: () -> Unit, onCancelSelected: () -> Unit) {
    val icon = if (player.play == true) R.drawable.ic_pause else R.drawable.ic_play
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.DarkGray),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = player.artist?.name.orEmpty(),
            color = Color.White,
            modifier = Modifier.padding(12.dp)
        )

        Spacer(modifier = Modifier.weight(1f))
        Image(painter = painterResource(id = icon),
            contentDescription = "play/pause",
            modifier = Modifier
                .size(40.dp)
                .clickable { onPlaySelected() })
        Image(painter = painterResource(id = R.drawable.ic_close),
            contentDescription = "Close",
            modifier = Modifier
                .size(40.dp)
                .clickable { onCancelSelected() })
    }
}

@Composable
fun ArtistItem(artist: Artist, onItemSelected: (Artist) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onItemSelected(artist) }) {
        AsyncImage(
            modifier = Modifier
                .padding(8.dp)
                .size(72.dp)
                .clip(CircleShape),
            model = artist.image, contentDescription = artist.description
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = artist.name!!, color = Color.White, fontWeight = FontWeight.Bold)
    }
}




