package com.kuartet.mbois.repository

import com.google.firebase.firestore.FirebaseFirestore
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
}