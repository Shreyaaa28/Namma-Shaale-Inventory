package com.example.nammashaaleinventory.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assets")
data class AssetEntity(
    @PrimaryKey val id: String,
    val name: String,
    val category: String,
    val serialNumber: String?,
    val purchaseYear: Int,
    val status: String, // WORKING, NEEDS_REPAIR, BROKEN, LOST
    val photoPath: String?,
    val lastCheckedDate: Long,
    val createdAt: Long = System.currentTimeMillis()
)
