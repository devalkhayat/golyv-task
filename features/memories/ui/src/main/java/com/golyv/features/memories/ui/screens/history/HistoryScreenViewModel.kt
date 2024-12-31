package com.golyv.features.memories.ui.screens.history

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.golyv.core.common.UiEvent
import com.golyv.features.memories.domain.model.MemoryModel
import com.golyv.features.memories.domain.use_cases.AddMemoryUseCase
import com.golyv.features.memories.domain.use_cases.GetAllMemoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryScreenViewModel @Inject constructor(private val getAllMemoriesUseCase: GetAllMemoriesUseCase,private val addMemoryUseCase: AddMemoryUseCase):ViewModel(){

    private val _memoriesList= mutableStateOf(HistoryScreenStateHolder<List<MemoryModel>>())
    val memoriesList:State<HistoryScreenStateHolder<List<MemoryModel>>> get()=_memoriesList

    private val _addResult= mutableStateOf(HistoryScreenStateHolder<Boolean>())
    val addResult:State<HistoryScreenStateHolder<Boolean>> get()=_addResult

    fun getAll() {
        viewModelScope.launch {
            getAllMemoriesUseCase().onEach {
                when(it){
                    is UiEvent.Loading -> _memoriesList.value= HistoryScreenStateHolder(isLoading = true)
                    is UiEvent.Success -> _memoriesList.value= HistoryScreenStateHolder(data=it.data)
                    is UiEvent.Error -> _memoriesList.value= HistoryScreenStateHolder(error = it.message.toString())
                }
            }.launchIn(viewModelScope)

        }
    }

    fun add(location:String){

        viewModelScope.launch {
            addMemoryUseCase(location).onEach {
                when(it){
                    is UiEvent.Loading -> _addResult.value= HistoryScreenStateHolder(isLoading = true)
                    is UiEvent.Success -> _addResult.value= HistoryScreenStateHolder<Boolean>(data=it.data)
                    is UiEvent.Error -> _addResult.value= HistoryScreenStateHolder(error = it.message.toString())
                }
            }.launchIn(viewModelScope)

        }
    }
}