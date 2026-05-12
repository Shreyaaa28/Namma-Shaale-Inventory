package com.example.nammashaaleinventory.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "issue_logs",
    foreignKeys = [
        ForeignKey(
            entity = AssetEntity::class,
            parentColumns = ["id"],
            childColumns = ["assetId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["assetId"])]
)
data class IssueLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val assetId: String,
    val issueType: String, // LOST, DAMAGED, STOLEN, OTHER
    val description: String,
    val photoPath: String?,
    val date: Long = System.currentTimeMillis(),
    val isResolved: Boolean = false
)
