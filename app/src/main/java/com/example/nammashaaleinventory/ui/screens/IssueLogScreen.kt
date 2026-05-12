package com.example.nammashaaleinventory.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.nammashaaleinventory.ui.viewmodel.AssetViewModel
import com.example.nammashaaleinventory.ui.viewmodel.AssetViewModelFactory
import com.example.nammashaaleinventory.ui.viewmodel.IssueViewModel
import com.example.nammashaaleinventory.ui.viewmodel.IssueViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssueLogScreen(
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val application = context.applicationContext as InventoryApplication
    val issueViewModel: IssueViewModel = viewModel(
        factory = IssueViewModelFactory(application.repository)
    )
    val assetViewModel: AssetViewModel = viewModel(
        factory = AssetViewModelFactory(application.repository)
    )

    val issues by issueViewModel.issues.collectAsState()
    val assets by assetViewModel.assets.collectAsState()
    
    val assetMap = assets.associateBy { it.id }
    val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Issue Log") },
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
        if (issues.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.LightGray)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("No issues reported.", color = Color.Gray)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(issues) { issue ->
                    val asset = assetMap[issue.assetId]
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = if (issue.isResolved) Color(0xFFE8F5E9) else Color(0xFFFFF3E0)
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = issue.issueType,
                                    fontWeight = FontWeight.Bold,
                                    color = if (issue.isResolved) Color(0xFF388E3C) else Color(0xFFF57C00),
                                    style = MaterialTheme.typography.labelLarge
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = dateFormatter.format(Date(issue.date)),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Gray
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = asset?.name ?: "Unknown Asset",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            
                            Text(
                                text = issue.description,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            
                            if (!issue.isResolved) {
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = { issueViewModel.resolveIssue(issue) },
                                    modifier = Modifier.align(Alignment.End),
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                                ) {
                                    Text("Mark Resolved")
                                }
                            } else {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "Resolved",
                                    modifier = Modifier.align(Alignment.End),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color(0xFF388E3C)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
