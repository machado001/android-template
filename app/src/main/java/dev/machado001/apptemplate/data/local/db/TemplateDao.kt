package dev.machado001.apptemplate.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TemplateDao {
    @Query("SELECT * FROM template_items ORDER BY id DESC")
    fun observeItems(): Flow<List<TemplateEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: TemplateEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(items: List<TemplateEntity>)

    @Query("SELECT COUNT(*) FROM template_items")
    suspend fun count(): Int
}
