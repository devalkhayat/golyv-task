package com.golyv.features.memories.ui.screens.history

import com.golyv.features.memories.domain.model.MemoryModel

data class HistoryScreenStateHolder<T>(val isLoading:Boolean=false, val data: T?=null, val error:String="")