package com.example.nammashaaleinventory.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
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
import com.example.nammashaaleinventory.ui.theme.*
import com.example.nammashaaleinventory.ui.viewmodel.AssetViewModel
import com.example.nammashaaleinventory.ui.viewmodel.AssetViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthCheckScreen(
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val application = context.applicationContext as InventoryApplication
    val viewModel: AssetViewModel = viewModel(
        factory = AssetViewModelFactory(application.repository)
    )

    val assets by viewModel.assets.collectAsState()
    var currentIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Health Checkup") },
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
        if (assets.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text("No assets to check.")
            }
        } else if (currentIndex >= assets.size) {
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Default.Check, contentDescription = null, tint = StatusWorking, modifier = Modifier.size(64.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text("Audit Complete!", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(32.dp))
                Button(onClick = onNavigateBack) {
                    Text("Return to Dashboard")
                }
            }
        } else {
            val asset = assets[currentIndex]
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Asset ${currentIndex + 1} of ${assets.size}",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Gray
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Card(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(asset.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        Text(asset.category, style = MaterialTheme.typography.titleMedium, color = Color.Gray)
                        
                        if (asset.serialNumber != null) {
                            Text("S/N: ${asset.serialNumber}", style = MaterialTheme.typography.bodyMedium)
                        }
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        Text("Current Status: ${asset.status}", fontWeight = FontWeight.SemiBold)
                        
                        Spacer(modifier = Modifier.weight(1f))
                        
                        Text("Update Status:", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            StatusButton(
                                modifier = Modifier.weight(1f),
                                label = "Working",
                                color = StatusWorking,
                                onClick = {
                                    viewModel.updateAssetStatus(asset, "WORKING")
                                    currentIndex++
                                }
                            )
                            StatusButton(
                                modifier = Modifier.weight(1f),
                                label = "Repair",
                                color = StatusNeedsRepair,
                                onClick = {
                                    viewModel.updateAssetStatus(asset, "NEEDS_REPAIR")
                                    currentIndex++
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            StatusButton(
                                modifier = Modifier.weight(1f),
                                label = "Broken",
                                color = StatusBroken,
                                onClick = {
                                    viewModel.updateAssetStatus(asset, "BROKEN")
                                    currentIndex++
                                }
                            )
                            StatusButton(
                                modifier = Modifier.weight(1f),
                                label = "Lost",
                                color = StatusLost,
                                onClick = {
                                    viewModel.updateAssetStatus(asset, "LOST")
                                    currentIndex++
                                }
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                TextButton(onClick = { currentIndex++ }) {
                    Text("Skip this asset")
                }
            }
        }
    }
}

@Composable
fun StatusButton(
    modifier: Modifier = Modifier,
    label: String,
    color: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Text(label, color = Color.White, style = MaterialTheme.typography.labelLarge)
    }
}
