package com.example.nammashaaleinventory.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nammashaaleinventory.ui.screens.AssetListScreen
import com.example.nammashaaleinventory.ui.screens.AssetRegisterScreen
import com.example.nammashaaleinventory.ui.screens.DashboardScreen
import com.example.nammashaaleinventory.ui.screens.SettingsScreen

@Composable
fun InventoryNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Dashboard.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToAssetList = { navController.navigate(Screen.AssetList.route) },
                onNavigateToRegister = { navController.navigate(Screen.AssetRegister.route) },
                onNavigateToHealthCheck = { navController.navigate(Screen.HealthCheck.route) },
                onNavigateToIssues = { navController.navigate(Screen.IssueLog.route) },
                onNavigateToRepair = { navController.navigate(Screen.RepairRequest.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
            )
        }
        composable(Screen.AssetList.route) {
            AssetListScreen(
                onNavigateToDetail = { assetId -> navController.navigate(Screen.AssetDetail.createRoute(assetId)) },
                onNavigateToRegister = { navController.navigate(Screen.AssetRegister.route) }
            )
        }
        composable(Screen.AssetRegister.route) {
            AssetRegisterScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.HealthCheck.route) {
            com.example.nammashaaleinventory.ui.screens.HealthCheckScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.AssetDetail.route) { backStackEntry ->
            val assetId = backStackEntry.arguments?.getString("assetId") ?: return@composable
            com.example.nammashaaleinventory.ui.screens.AssetDetailScreen(
                assetId = assetId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.IssueLog.route) {
            com.example.nammashaaleinventory.ui.screens.IssueLogScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.RepairRequest.route) {
            com.example.nammashaaleinventory.ui.screens.RepairRequestScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
