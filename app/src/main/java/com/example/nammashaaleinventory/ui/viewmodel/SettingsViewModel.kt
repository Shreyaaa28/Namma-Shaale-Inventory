package com.example.nammashaaleinventory.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.nammashaaleinventory.util.PreferenceManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    val schoolName: StateFlow<String> = preferenceManager.schoolName
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "My Government School"
        )

    val customCategories: StateFlow<String> = preferenceManager.customCategories
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "Tablets,Sports Kits,Lab Equipment,Furniture"
        )

    fun updateSchoolName(name: String) {
        viewModelScope.launch {
            preferenceManager.saveSchoolName(name)
        }
    }

    fun updateCategories(categories: String) {
        viewModelScope.launch {
            preferenceManager.saveCustomCategories(categories)
        }
    }
}

class SettingsViewModelFactory(
    private val preferenceManager: PreferenceManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(preferenceManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
