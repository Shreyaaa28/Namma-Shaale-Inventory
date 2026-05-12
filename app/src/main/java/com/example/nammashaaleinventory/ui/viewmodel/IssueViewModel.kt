package com.example.nammashaaleinventory.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.nammashaaleinventory.data.local.entities.IssueLogEntity
import com.example.nammashaaleinventory.data.repository.InventoryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class IssueViewModel(
    private val repository: InventoryRepository
) : ViewModel() {

    val issues: StateFlow<List<IssueLogEntity>> = repository.allIssues
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun resolveIssue(issue: IssueLogEntity) {
        viewModelScope.launch {
            repository.updateIssue(issue.copy(isResolved = true))
        }
    }

    fun reportIssue(issue: IssueLogEntity) {
        viewModelScope.launch {
            repository.insertIssue(issue)
        }
    }
}

class IssueViewModelFactory(
    private val repository: InventoryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IssueViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return IssueViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
