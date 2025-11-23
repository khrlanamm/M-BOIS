package com.kuartet.mbois.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuartet.mbois.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.kuartet.mbois.model.MboisCard
import com.kuartet.mbois.repository.CardRepository
import com.kuartet.mbois.ui.screens.ChatMessage
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

    val chatMessages = mutableStateListOf<ChatMessage>()
    private var chatSession: com.google.ai.client.generativeai.Chat? = null
    private val _isAiLoading = MutableStateFlow(false)
    val isAiLoading: StateFlow<Boolean> = _isAiLoading

    init {
        fetchCards()
    }

    fun fetchCards() {
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

    fun getCardById(id: String): MboisCard? {
        val currentState = _uiState.value
        return if (currentState is HomeUiState.Success) {
            currentState.groupedCards.values.flatten().find { it.id == id }
        } else {
            null
        }
    }

    fun initChatSession(card: MboisCard) {
        chatMessages.clear()

        val systemInstruction = """
            Anda adalah AI ChatBot resmi dari aplikasi M-BOIS.
            Tugas anda hanya menjawab pertanyaan seputar "${card.name}" yang merupakan kategori "${card.categoryName}" di Jawa Timur.
            
            Aturan:
            1. Jika user bertanya tentang ${card.name}, jelaskan dengan detail, menarik, dan sopan.
            2. Jika user bertanya hal lain di luar konteks ${card.name} atau budaya Jawa Timur, tolak dengan halus.
            3. Contoh penolakan: "Maaf, saya adalah AI M-BOIS yang hanya dapat mendiskusikan tentang ${card.name}."
            4. Gunakan Bahasa Indonesia yang komunikatif namun tetap santai.
        """.trimIndent()

        val generativeModel = GenerativeModel(
            modelName = "gemini-2.5-pro",
            apiKey = BuildConfig.GEMINI_API_KEY,
            systemInstruction = content { text(systemInstruction) }
        )

        chatSession = generativeModel.startChat()

        chatMessages.add(
            ChatMessage(
                text = "Halo! Apa yang ingin kamu ketahui mengenai ${card.name}?",
                isUser = false
            )
        )
    }

    fun sendChatMessage(message: String) {
        if (message.isBlank()) return

        chatMessages.add(ChatMessage(text = message, isUser = true))
        _isAiLoading.value = true

        viewModelScope.launch {
            try {
                val response = chatSession?.sendMessage(message)
                val responseText = response?.text ?: "Maaf, saya sedang mengalami gangguan."

                chatMessages.add(ChatMessage(text = responseText, isUser = false))
            } catch (e: Exception) {
                chatMessages.add(ChatMessage(text = "Gagal terhubung ke AI: ${e.message}", isUser = false))
            } finally {
                _isAiLoading.value = false
            }
        }
    }
}