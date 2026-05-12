package com.example.nammashaaleinventory.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.nammashaaleinventory.data.local.entities.AssetEntity
import com.example.nammashaaleinventory.data.repository.InventoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AssetViewModel(
    private val repository: InventoryRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory

    val assets: StateFlow<List<AssetEntity>> = combine(
        repository.allAssets,
        _searchQuery,
        _selectedCategory
    ) { assets, query, category ->
        assets.filter { asset ->
            val matchesQuery = asset.name.contains(query, ignoreCase = true) ||
                    (asset.serialNumber?.contains(query, ignoreCase = true) == true)
            val matchesCategory = category == null || asset.category == category
            matchesQuery && matchesCategory
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setCategoryFilter(category: String?) {
        _selectedCategory.value = category
    }

    fun addAsset(asset: AssetEntity) {
        viewModelScope.launch {
            repository.insertAsset(asset)
        }
    }

    fun deleteAsset(asset: AssetEntity) {
        viewModelScope.launch {
            repository.deleteAsset(asset)
        }
    }

    fun updateAssetStatus(asset: AssetEntity, newStatus: String) {
        viewModelScope.launch {
            repository.insertAsset(asset.copy(
                status = newStatus,
                lastCheckedDate = System.currentTimeMillis()
            ))
        }
    }
}

class AssetViewModelFactory(
    private val repository: InventoryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AssetViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AssetViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
