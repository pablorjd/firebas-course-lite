package dev.pablorjd.firebasecourse.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dev.pablorjd.firebasecourse.presentation.model.Artist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private var db: FirebaseFirestore = Firebase.firestore
    private val _artists = MutableStateFlow<List<Artist>>(emptyList())
    val artists: StateFlow<List<Artist>> = _artists

    init {

        repeat(10) {
           // populateData()
        }
        getArtists()
    }

    private fun populateData() {
            val random = (0..100000).random()
            val artist = Artist("Artist", "Description $random", "https://fastly.picsum.photos/id/1060/500/300.jpg?hmac=vuNbjMS6QUHEfb8vZJniDu1jkR2PJo1cwwqUsKogMBg")
            db.collection("artists")
                .add(artist)
    }

    private fun getArtists() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                getArtistsUseCase()
            }
            _artists.value = result
        }
    }

    private suspend fun getArtistsUseCase(): List<Artist> {
        return try {
            db.collection("artists")
                .get()
                .await()
                .documents.
                mapNotNull { snapshot ->
                snapshot.toObject(Artist::class.java)
            }
        } catch (e: Exception) {
            emptyList()
        }

    }
}