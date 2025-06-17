package com.example.editphotovideo.ui.main.template.model

data class Template(val id: Int, val imagePath: String, val imagePathBackgr: String)

fun getTemplateTrending(): List<Template> {
    return (1..6).map { i ->
        Template(id = i, imagePath = "template/trending/template_$i.png", imagePathBackgr = "anh_backgr/trending/template_$i.webp")
    }
}

fun getTemplateAutumn(): List<Template> {
    return (7..12).map { i ->
        Template(id = i, imagePath = "template/autumn/template_$i.png", imagePathBackgr = "anh_backgr/autumn/template_$i.webp")
    }

}

fun getTemplateNoel(): List<Template> {
    return (13..18).map { i ->
        Template(id = i, imagePath = "template/noel/template_$i.png", imagePathBackgr = "anh_backgr/noel/template_$i.webp")
    }
}

fun getTemplateHaloween(): List<Template> {
    return (19..24).map { i ->
        Template(id = i, imagePath = "template/haloween/template_$i.png", imagePathBackgr = "anh_backgr/haloween/template_$i.webp")
    }
}

fun getTemplateNeon(): List<Template> {
    return (25..30).map { i ->
        Template(id = i, imagePath = "template/neon/template_$i.png", imagePathBackgr = "anh_backgr/neon/template_$i.webp")
    }
}

fun getTemplateWedding(): List<Template> {
    return (31..36).map { i ->
        Template(id = i, imagePath = "template/wedding/template_$i.png", imagePathBackgr = "anh_backgr/wedding/template_$i.webp")
    }
}

fun getAllTemplate():MutableList<DisplayTemplate>{
    val allTemplates = mutableListOf<DisplayTemplate>()
    getTemplateTrending().forEachIndexed { i, path ->
        allTemplates.add(DisplayTemplate(TemplateType.TRENDING, path.imagePath, path.imagePathBackgr,path.id))
    }
    getTemplateAutumn().forEachIndexed { i, path ->
        allTemplates.add(DisplayTemplate(TemplateType.AUTUMN, path.imagePath,  path.imagePathBackgr,path.id))
    }
    getTemplateNoel().forEachIndexed { i, path ->
        allTemplates.add(DisplayTemplate(TemplateType.NOEL, path.imagePath,  path.imagePathBackgr,path.id))
    }
    getTemplateHaloween().forEachIndexed { i, path ->
        allTemplates.add(DisplayTemplate(TemplateType.HALOWEEN, path.imagePath,  path.imagePathBackgr,path.id))
    }

    getTemplateNeon().forEachIndexed { i, path ->
        allTemplates.add(DisplayTemplate(TemplateType.NEON, path.imagePath,  path.imagePathBackgr,path.id))
    }
    getTemplateWedding().forEachIndexed { i, path ->
        allTemplates.add(DisplayTemplate(TemplateType.WEDDING, path.imagePath,  path.imagePathBackgr,path.id))
    }

    return allTemplates
}