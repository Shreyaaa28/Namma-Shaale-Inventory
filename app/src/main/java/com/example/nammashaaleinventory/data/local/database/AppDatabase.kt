package com.example.nammashaaleinventory.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nammashaaleinventory.data.local.dao.AssetDao
import com.example.nammashaaleinventory.data.local.dao.ConditionCheckDao
import com.example.nammashaaleinventory.data.local.dao.IssueLogDao
import com.example.nammashaaleinventory.data.local.dao.RepairFlagDao
import com.example.nammashaaleinventory.data.local.entities.AssetEntity
import com.example.nammashaaleinventory.data.local.entities.ConditionCheckEntity
import com.example.nammashaaleinventory.data.local.entities.IssueLogEntity
import com.example.nammashaaleinventory.data.local.entities.RepairFlagEntity

@Database(
    entities = [
        AssetEntity::class,
        ConditionCheckEntity::class,
        IssueLogEntity::class,
        RepairFlagEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun assetDao(): AssetDao
    abstract fun conditionCheckDao(): ConditionCheckDao
    abstract fun issueLogDao(): IssueLogDao
    abstract fun repairFlagDao(): RepairFlagDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "namma_shaale_inventory_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
