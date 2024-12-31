package com.golyv.core.datasource.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.golyv.core.datasource.local.entities.Memory

@Dao
interface MemoriesDao {

    @Insert
    suspend fun Add(memory: Memory):Unit

    @Query("SELECT * FROM tblMemories")
    suspend fun GetAll():List<Memory>
}