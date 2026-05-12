package com.example.nammashaaleinventory.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nammashaaleinventory.InventoryApplication
import com.example.nammashaaleinventory.ui.theme.*
import com.example.nammashaaleinventory.ui.viewmodel.DashboardViewModel
import com.example.nammashaaleinventory.ui.viewmodel.DashboardViewModelFactory
import com.example.nammashaaleinventory.util.AdvisoryType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToAssetList: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToHealthCheck: () -> Unit,
    onNavigateToIssues: () -> Unit,
    onNavigateToRepair: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val context = LocalContext.current
    val application = context.applicationContext as InventoryApplication
    val viewModel: DashboardViewModel = viewModel(
        factory = DashboardViewModelFactory(application.repository, application.preferenceManager)
    )
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.schoolName, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick = {}) { Icon(Icons.Default.Home, contentDescription = "Dashboard") }
                    IconButton(onClick = onNavigateToAssetList) { Icon(Icons.Default.List, contentDescription = "Assets") }
                    IconButton(onClick = onNavigateToIssues) { Icon(Icons.Default.Warning, contentDescription = "Issues") }
                    IconButton(onClick = {
                        val report = viewModel.generateReport()
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, report)
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                    }) { Icon(Icons.Default.Share, contentDescription = "Report") }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToRegister) {
                Icon(Icons.Default.Add, contentDescription = "Add Asset")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("Asset Summary", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Total",
                        count = uiState.totalAssets,
                        color = MaterialTheme.colorScheme.primary,
                        onClick = onNavigateToAssetList
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Working",
                        count = uiState.workingCount,
                        color = StatusWorking
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Needs Repair",
                        count = uiState.needsRepairCount,
                        color = StatusNeedsRepair
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Broken",
                        count = uiState.brokenCount,
                        color = StatusBroken
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Lost",
                        count = uiState.lostCount,
                        color = StatusLost
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = onNavigateToHealthCheck,
                        modifier = Modifier.weight(1f).height(56.dp)
                    ) {
                        Text("Start Health Check")
                    }
                    OutlinedButton(
                        onClick = onNavigateToRepair,
                        modifier = Modifier.weight(1f).height(56.dp)
                    ) {
                        Text("Repairs")
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Smart Advisories", style = MaterialTheme.typography.titleLarge)
            }

            items(uiState.advisories) { advisory ->
                val bgColor = when (advisory.type) {
                    AdvisoryType.INFO -> Color(0xFFE3F2FD)
                    AdvisoryType.WARNING -> Color(0xFFFFF3E0)
                    AdvisoryType.SUCCESS -> Color(0xFFE8F5E9)
                    AdvisoryType.URGENT -> Color(0xFFFFEBEE)
                }
                val iconColor = when (advisory.type) {
                    AdvisoryType.INFO -> Color(0xFF1976D2)
                    AdvisoryType.WARNING -> Color(0xFFF57C00)
                    AdvisoryType.SUCCESS -> Color(0xFF388E3C)
                    AdvisoryType.URGENT -> Color(0xFFD32F2F)
                }
                val icon = when (advisory.type) {
                    AdvisoryType.INFO -> Icons.Default.Info
                    AdvisoryType.WARNING -> Icons.Default.Warning
                    AdvisoryType.SUCCESS -> Icons.Default.CheckCircle
                    AdvisoryType.URGENT -> Icons.Default.Warning
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = bgColor)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(32.dp))
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(advisory.title, fontWeight = FontWeight.Bold, color = iconColor)
                            Text(advisory.message, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    title: String,
    count: Int,
    color: Color,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, color = Color.White, style = MaterialTheme.typography.labelSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(count.toString(), color = Color.White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }
    }
}
