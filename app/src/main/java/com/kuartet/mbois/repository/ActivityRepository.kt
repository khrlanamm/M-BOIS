package com.kuartet.mbois.repository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class ActivityRepository {
    private val firestore = FirebaseFirestore.getInstance()

    fun updateUserActivity(email: String) {
        val activityData = hashMapOf(
            "email" to email,
            "last_opened" to FieldValue.serverTimestamp()
        )

        firestore.collection("activity")
            .document(email)
            .set(activityData, SetOptions.merge())
    }
}