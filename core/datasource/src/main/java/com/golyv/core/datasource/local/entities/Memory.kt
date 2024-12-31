package com.golyv.core.datasource.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblMemories")
data class Memory(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    val location:String
)
