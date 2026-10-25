package com.kuartet.mbois.model

const val DEFAULT_MODEL_NAME = "gemini-2.5-flash"

fun buildSystemInstruction(template: String, card: MboisCard): String =
    template
        .replace("{card_name}", card.name)
        .replace("{category_name}", card.categoryName)

fun resolveModelName(config: AiConfig): String =
    config.modelName.ifEmpty { DEFAULT_MODEL_NAME }
