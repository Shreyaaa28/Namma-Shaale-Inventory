package com.example.nammashaaleinventory.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
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
import com.example.nammashaaleinventory.data.local.entities.AssetEntity
import com.example.nammashaaleinventory.data.local.entities.IssueLogEntity
import com.example.nammashaaleinventory.ui.theme.StatusWorking
import com.example.nammashaaleinventory.ui.viewmodel.AssetViewModel
import com.example.nammashaaleinventory.ui.viewmodel.AssetViewModelFactory
import com.example.nammashaaleinventory.ui.viewmodel.IssueViewModel
import com.example.nammashaaleinventory.ui.viewmodel.IssueViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetDetailScreen(
    assetId: String,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val application = context.applicationContext as InventoryApplication
    val assetViewModel: AssetViewModel = viewModel(
        factory = AssetViewModelFactory(application.repository)
    )
    val issueViewModel: IssueViewModel = viewModel(
        factory = IssueViewModelFactory(application.repository)
    )

    val assets by assetViewModel.assets.collectAsState()
    val asset = assets.find { it.id == assetId }
    
    var showIssueDialog by remember { mutableStateOf(false) }
    var issueDescription by remember { mutableStateOf("") }
    var issueType by remember { mutableStateOf("DAMAGED") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(asset?.name ?: "Asset Detail") },
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
        if (asset == null) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text("Asset not found.")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        DetailItem("Category", asset.category)
                        DetailItem("Serial Number", asset.serialNumber ?: "N/A")
                        DetailItem("Purchase Year", asset.purchaseYear.toString())
                        DetailItem("Status", asset.status)
                    }
                }

                Button(
                    onClick = { showIssueDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(Icons.Default.Warning, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Report an Issue")
                }
            }
        }
    }

    if (showIssueDialog && asset != null) {
        AlertDialog(
            onDismissRequest = { showIssueDialog = false },
            title = { Text("Report Issue for ${asset.name}") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Issue Type")
                    // Simple selector
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(selected = issueType == "DAMAGED", onClick = { issueType = "DAMAGED" }, label = { Text("Damaged") })
                        FilterChip(selected = issueType == "LOST", onClick = { issueType = "LOST" }, label = { Text("Lost") })
                        FilterChip(selected = issueType == "STOLEN", onClick = { issueType = "STOLEN" }, label = { Text("Stolen") })
                    }
                    OutlinedTextField(
                        value = issueDescription,
                        onValueChange = { issueDescription = it },
                        label = { Text("Describe the issue") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (issueDescription.isNotBlank()) {
                            issueViewModel.reportIssue(
                                IssueLogEntity(
                                    assetId = asset.id,
                                    issueType = issueType,
                                    description = issueDescription,
                                    photoPath = null
                                )
                            )
                            showIssueDialog = false
                            issueDescription = ""
                        }
                    }
                ) {
                    Text("Submit")
                }
            },
            dismissButton = {
                TextButton(onClick = { showIssueDialog = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(label, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        Text(value, modifier = Modifier.weight(1f))
    }
}
