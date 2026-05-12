package com.example.nammashaaleinventory.ui.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object AssetList : Screen("asset_list")
    object AssetRegister : Screen("asset_register")
    object AssetDetail : Screen("asset_detail/{assetId}") {
        fun createRoute(assetId: String) = "asset_detail/$assetId"
    }
    object HealthCheck : Screen("health_check")
    object IssueLog : Screen("issue_log")
    object RepairRequest : Screen("repair_request")
    object Settings : Screen("settings")
}
