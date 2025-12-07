package com.kuartet.mbois.model

import com.google.firebase.firestore.PropertyName

data class AiConfig(
    @get:PropertyName("model_name")
    @set:PropertyName("model_name")
    var modelName: String = "gemini-2.5-flash",

    @get:PropertyName("system_instruction_template")
    @set:PropertyName("system_instruction_template")
    var systemInstructionTemplate: String = ""
)