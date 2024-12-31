package com.golyv.features.memories.data.mappers

import com.golyv.core.datasource.local.entities.Memory
import com.golyv.features.memories.domain.model.MemoryModel

fun MemoryModel.toDataSourceInstance():Memory{
    return Memory(location=this.location)
}

fun Memory.toDomainInstance():MemoryModel{
    return MemoryModel(id=this.id,location=this.location)
}