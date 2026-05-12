package com.example.nammashaaleinventory.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nammashaaleinventory.InventoryApplication
import com.example.nammashaaleinventory.ui.viewmodel.AssetViewModel
import com.example.nammashaaleinventory.ui.viewmodel.AssetViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepairRequestScreen(
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val application = context.applicationContext as InventoryApplication
    val viewModel: AssetViewModel = viewModel(
        factory = AssetViewModelFactory(application.repository)
    )

    val assets by viewModel.assets.collectAsState()
    val needsRepairAssets = assets.filter { it.status == "NEEDS_REPAIR" }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Repair Requests") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        if (needsRepairAssets.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Build, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.LightGray)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("No assets need repair at the moment.", color = Color.Gray)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(needsRepairAssets) { asset ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(asset.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                            Text(asset.category, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Button(
                                onClick = {
                                    // In a full implementation, this would create a RepairFlagEntity
                                    // and possibly update the asset status or add an entry to a repair log.
                                    // For now, let's just show a snackbar or mock the action.
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Mark as Sent for Repair")
                            }
                        }
                    }
                }
            }
        }
    }
}
