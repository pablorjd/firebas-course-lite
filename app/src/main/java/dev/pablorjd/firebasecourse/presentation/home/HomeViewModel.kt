package dev.pablorjd.firebasecourse.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dev.pablorjd.firebasecourse.presentation.model.Artist
import dev.pablorjd.firebasecourse.presentation.model.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private var db: FirebaseFirestore = Firebase.firestore
    private val realtime = Firebase.database
    private val _artists = MutableStateFlow<List<Artist>>(emptyList())
    val artists: StateFlow<List<Artist>> = _artists

    private val _player = MutableStateFlow<Player?>(null)
    val player: StateFlow<Player?> = _player

    init {
        getArtists()
        getPlayer()
    }

    private fun getPlayer() {
        viewModelScope.launch {
            collectPlayer().collect {
                val player = it.getValue(Player::class.java)
                _player.value = player
            }
        }
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
            Log.i("Error getArtistsUseCase()", "Error getting data ${e.message}")
            emptyList()
        }

    }

    private fun collectPlayer() : Flow<DataSnapshot> = callbackFlow {
        val listener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                trySend(snapshot).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Error collectPlayer()", "Error getting data", error.toException())
                close(error.toException())
                throw Exception(error.toException())
            }
        }
        //val ref1 = realtime.getReference("player")
        val ref = realtime.reference.child("player")
        ref.addValueEventListener(listener)

        awaitClose {
            ref.removeEventListener(listener)
        }
    }

    fun onPlay() {
        val currentPlayer = _player.value?.copy(play = !player.value?.play!!)
        val ref = realtime.reference.child("player")
        ref.setValue(currentPlayer)
    }

    fun onCancelSelected() {
        val ref = realtime.reference.child("player")
        ref.setValue(null)
    }
    fun addPlayer(artist: Artist) {
        val ref = realtime.reference.child("player")
        val player = Player(artist = artist, play = true)
        ref.setValue(player)
    }
}














