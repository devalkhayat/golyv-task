package com.golyv.features.memories.domain.repo

import com.golyv.features.memories.domain.model.MemoryModel

interface MemoriesRepository {

    suspend fun Add(model: MemoryModel):Boolean

    suspend fun GetAll():List<MemoryModel>
}