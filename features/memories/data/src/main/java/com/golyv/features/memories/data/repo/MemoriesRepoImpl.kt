package com.golyv.features.memories.data.repo

import com.golyv.core.datasource.local.AppDatabase
import com.golyv.features.memories.data.mappers.toDataSourceInstance
import com.golyv.features.memories.data.mappers.toDomainInstance
import com.golyv.features.memories.domain.model.MemoryModel
import com.golyv.features.memories.domain.repo.MemoriesRepository
import javax.inject.Inject


class MemoriesRepoImpl @Inject constructor(private val db:AppDatabase):MemoriesRepository {

    override suspend fun Add(model: MemoryModel):Boolean {

        try {
            db.memoriesDao().Add(model.toDataSourceInstance())
            return true
        } catch(ex:Exception) {
            return false
        }
    }

    override suspend fun GetAll(): List<MemoryModel> {
        var result = mutableListOf<MemoryModel>()
        db.memoriesDao().GetAll().forEach {
            result.add(it.toDomainInstance())
        }
        return result
    }
}