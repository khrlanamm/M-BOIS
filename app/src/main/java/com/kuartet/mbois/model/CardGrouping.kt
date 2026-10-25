package com.kuartet.mbois.model

fun groupCardsByCategory(cards: List<MboisCard>): Map<String, List<MboisCard>> =
    cards.sortedWith(
        compareBy(
            { it.categoryId },
            { it.cardIndex }
        )
    ).groupBy { it.categoryName }
