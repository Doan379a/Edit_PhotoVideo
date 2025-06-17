package com.example.editphotovideo.ui.main.template.model

enum class TemplateType : java.io.Serializable {
    TRENDING, AUTUMN, NOEL, HALOWEEN, NEON, WEDDING
}

data class DisplayTemplate(
    val sectionType: TemplateType,
    val imagePath: String,
    val imgbackgr: String,
    val id: Int
)
