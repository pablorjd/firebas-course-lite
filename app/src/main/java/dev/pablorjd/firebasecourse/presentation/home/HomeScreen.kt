package dev.pablorjd.firebasecourse.presentation.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.pablorjd.firebasecourse.presentation.model.Artist
import dev.pablorjd.firebasecourse.ui.theme.Black

//@Preview(showBackground = true, showSystemUi = true, name = "Home Screen")
@Composable
fun HomeScreen(paddingValues: PaddingValues, viewModel: HomeViewModel = HomeViewModel()) {

    val artists: State<List<Artist>> = viewModel.artists.collectAsState()
    Log.i("TAG", "HomeScreen: ${artists.value}")
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
                ArtistItem(it)
            }
        }
    }
}

@Composable
fun ArtistItem(artist: Artist) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            modifier = Modifier.padding(8.dp).size(72.dp).clip(CircleShape),
            model = artist.image, contentDescription = artist.description)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = artist.name!!, color = Color.White , fontWeight = FontWeight.Bold)
    }
}




