package com.example.editphotovideo.ui.main.template.model

import android.content.Context
import com.example.editphotovideo.R

data class TemplateSection(val type: TemplateType, val title: String, val templates: List<Template>)

fun getSectionTrending(context: Context): List<TemplateSection> {

    val section = TemplateSection(
        TemplateType.TRENDING,
        context.getString(R.string.trending), getTemplateTrending()
    )
    return listOf(section)
}


fun getSectionAutumn(context: Context): List<TemplateSection> {
    val section = TemplateSection(
        TemplateType.AUTUMN,
        context.getString(R.string.autumn),
        getTemplateAutumn()
    )
    return listOf(section)
}

fun getSectionNoel(context: Context): List<TemplateSection> {
    val section =
        TemplateSection(TemplateType.NOEL, context.getString(R.string.noel), getTemplateNoel())
    return listOf(section)
}

fun getSectionHaloween(context: Context): List<TemplateSection> {
    val section = TemplateSection(
        TemplateType.HALOWEEN,
        context.getString(R.string.haloween), getTemplateHaloween()
    )
    return listOf(section)
}

fun getSectionNeon(context: Context): List<TemplateSection> {
    val section =
        TemplateSection(TemplateType.NEON, context.getString(R.string.neon), getTemplateNeon())
    return listOf(section)
}

fun getSectionWedding(context: Context): List<TemplateSection> {
    val section = TemplateSection(
        TemplateType.WEDDING,
        context.getString(R.string.wedding), getTemplateWedding()
    )
    return listOf(section)
}

fun getAllSection(context: Context) =
    getSectionTrending(context) + getSectionAutumn(context) + getSectionNoel(context) + getSectionHaloween(
        context
    ) + getSectionNeon(context) + getSectionWedding(context)
