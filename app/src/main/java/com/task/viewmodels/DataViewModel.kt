package com.task.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.dto.MovieDetailsDTO
import com.task.dto.MovieListDTO
import com.task.repositories.MovieDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
  private val dataRepository: MovieDataRepository,
) : ViewModel() {
  //movie list
  private val _movies: MutableStateFlow<MovieListDTO?> = MutableStateFlow(null)
  val movies = _movies.asStateFlow()

  //movie detail
  private val _movie: MutableStateFlow<MovieDetailsDTO?> = MutableStateFlow(null)
  var movie = _movie.asStateFlow()

  fun getMovieList(page: Int) {
    viewModelScope.launch {
      try {
        dataRepository.getPopularList(page).collect {
          if (it.isSuccessful) {
            _movies.value = it.body()
          } else {
            _movies.value = null
          }
        }
      } catch (e: Exception) {
        _movies.value = null
      }
    }
  }

  fun getMovieDetails(movie_id: Int) {
    viewModelScope.launch {
      try {
        dataRepository.getMovieDetail(movie_id).collect {
          if (it.isSuccessful) {
            _movie.value = it.body()
          }
        }
      } catch (e: Exception) {
        _movie.value = null
      }
    }
  }
}