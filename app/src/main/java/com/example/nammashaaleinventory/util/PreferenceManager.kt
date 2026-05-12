package com.example.nammashaaleinventory.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferenceManager(private val context: Context) {
    companion object {
        val SCHOOL_NAME = stringPreferencesKey("school_name")
        val CUSTOM_CATEGORIES = stringPreferencesKey("custom_categories")
    }

    val schoolName: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[SCHOOL_NAME] ?: "My Government School"
    }

    val customCategories: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[CUSTOM_CATEGORIES] ?: "Tablets,Sports Kits,Lab Equipment,Furniture"
    }

    suspend fun saveSchoolName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[SCHOOL_NAME] = name
        }
    }

    suspend fun saveCustomCategories(categories: String) {
        context.dataStore.edit { preferences ->
            preferences[CUSTOM_CATEGORIES] = categories
        }
    }
}
