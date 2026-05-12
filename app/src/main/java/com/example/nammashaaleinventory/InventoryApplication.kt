package com.example.nammashaaleinventory

import android.app.Application
import com.example.nammashaaleinventory.data.local.database.AppDatabase
import com.example.nammashaaleinventory.data.repository.InventoryRepository

class InventoryApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { 
        InventoryRepository(
            database.assetDao(),
            database.conditionCheckDao(),
            database.issueLogDao(),
            database.repairFlagDao()
        )
    }
    val preferenceManager by lazy { com.example.nammashaaleinventory.util.PreferenceManager(this) }
}
