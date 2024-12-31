package com.golyv.features.memories.domain.use_cases

import com.golyv.core.common.UiEvent
import com.golyv.features.memories.domain.model.MemoryModel
import com.golyv.features.memories.domain.repo.MemoriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AddMemoryUseCase @Inject constructor(private val memoriesRepository: MemoriesRepository) {

    operator fun invoke(location:String) = flow<UiEvent<Boolean>> {
        emit(UiEvent.Loading())
        emit(UiEvent.Success(memoriesRepository.Add(model = MemoryModel(location = location))))
    }.catch() {
        emit(UiEvent.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}