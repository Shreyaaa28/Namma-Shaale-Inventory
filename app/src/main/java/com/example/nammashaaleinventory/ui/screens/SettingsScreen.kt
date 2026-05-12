package com.example.nammashaaleinventory.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nammashaaleinventory.InventoryApplication
import com.example.nammashaaleinventory.ui.viewmodel.SettingsViewModel
import com.example.nammashaaleinventory.ui.viewmodel.SettingsViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val application = context.applicationContext as InventoryApplication
    val viewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModelFactory(application.preferenceManager)
    )

    val schoolName by viewModel.schoolName.collectAsState()
    val customCategories by viewModel.customCategories.collectAsState()

    var editingSchoolName by remember { mutableStateOf(schoolName) }

    // Update state when flow emits new values
    LaunchedEffect(schoolName) {
        editingSchoolName = schoolName
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = androidx.compose.ui.graphics.Color.White,
                    navigationIconContentColor = androidx.compose.ui.graphics.Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("School Details", style = MaterialTheme.typography.titleMedium)
            
            OutlinedTextField(
                value = editingSchoolName,
                onValueChange = { editingSchoolName = it },
                label = { Text("School Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (editingSchoolName.isNotBlank()) {
                        viewModel.updateSchoolName(editingSchoolName)
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Settings")
            }

            Spacer(modifier = Modifier.height(32.dp))
            Text("About App", style = MaterialTheme.typography.titleMedium)
            Text("Namma-Shaale Inventory\nVersion 1.0\nOffline-First Asset Manager")
        }
    }
}
