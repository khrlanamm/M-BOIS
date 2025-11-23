package com.kuartet.mbois.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuartet.mbois.model.MboisCard
import com.kuartet.mbois.repository.CardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val groupedCards: Map<String, List<MboisCard>>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

class HomeViewModel : ViewModel() {
    private val repository = CardRepository()
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        fetchCards()
    }

    private fun fetchCards() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val cards = repository.getAllCards()
                if (cards.isNotEmpty()) {
                    val sortedCards = cards.sortedWith(
                        compareBy(
                            { it.categoryName },
                            { it.cardIndex }
                        )
                    )
                    val grouped = sortedCards.groupBy { it.categoryName }
                    _uiState.value = HomeUiState.Success(grouped)
                } else {
                    _uiState.value = HomeUiState.Error("Data tidak ditemukan")
                }
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Terjadi kesalahan")
            }
        }
    }
}