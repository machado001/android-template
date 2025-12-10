package dev.machado001.apptemplate.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TemplateEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TemplateDatabase : RoomDatabase() {
    abstract fun templateDao(): TemplateDao
}
