package com.kuartet.mbois.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.kuartet.mbois.model.AiConfig
import com.kuartet.mbois.model.MboisCard
import kotlinx.coroutines.tasks.await

class CardRepository {
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getAllCards(): List<MboisCard> {
        return try {
            val snapshot = firestore.collection("cards").get().await()
            snapshot.toObjects(MboisCard::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getAiConfig(): AiConfig {
        return try {
            val snapshot = firestore.collection("mboisai").document("config").get().await()
            snapshot.toObject(AiConfig::class.java) ?: AiConfig()
        } catch (e: Exception) {
            AiConfig()
        }
    }
}