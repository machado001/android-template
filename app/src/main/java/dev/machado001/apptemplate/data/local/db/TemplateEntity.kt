package dev.machado001.apptemplate.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.machado001.apptemplate.domain.model.TemplateItem

@Entity(tableName = "template_items")
data class TemplateEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String
) {
    fun toDomain(): TemplateItem = TemplateItem(id = id, title = title, description = description)

    companion object {
        fun fromDomain(item: TemplateItem): TemplateEntity =
            TemplateEntity(id = item.id, title = item.title, description = item.description)
    }
}
