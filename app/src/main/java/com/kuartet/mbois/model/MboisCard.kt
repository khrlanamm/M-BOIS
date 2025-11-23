package com.kuartet.mbois.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class MboisCard(
    val id: String = "",
    val name: String = "",

    @get:PropertyName("category_id")
    @set:PropertyName("category_id")
    var categoryId: String = "",

    @get:PropertyName("category_name")
    @set:PropertyName("category_name")
    var categoryName: String = "",

    @get:PropertyName("card_index")
    @set:PropertyName("card_index")
    var cardIndex: Int = 0,

    @get:PropertyName("desc_short")
    @set:PropertyName("desc_short")
    var descShort: String = "",

    val desc: String = "",

    @get:PropertyName("card_url")
    @set:PropertyName("card_url")
    var cardUrl: String = "",

    @get:PropertyName("icon_url")
    @set:PropertyName("icon_url")
    var iconUrl: String = "",

    @get:PropertyName("audio_url")
    @set:PropertyName("audio_url")
    var audioUrl: String = "",

    @get:PropertyName("ar_url")
    @set:PropertyName("ar_url")
    var arUrl: String = "",

    @get:PropertyName("created_at")
    @set:PropertyName("created_at")
    var createdAt: Timestamp? = null
)