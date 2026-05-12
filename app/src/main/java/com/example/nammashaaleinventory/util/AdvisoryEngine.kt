package com.example.nammashaaleinventory.util

import com.example.nammashaaleinventory.data.local.entities.AssetEntity
import com.example.nammashaaleinventory.data.local.entities.IssueLogEntity
import com.example.nammashaaleinventory.data.local.entities.RepairFlagEntity

data class Advisory(
    val title: String,
    val message: String,
    val type: AdvisoryType
)

enum class AdvisoryType {
    INFO, WARNING, SUCCESS, URGENT
}

object AdvisoryEngine {
    fun generateAdvisories(
        assets: List<AssetEntity>,
        pendingRepairs: List<RepairFlagEntity>,
        recentIssues: List<IssueLogEntity>,
        lastAuditTimestamp: Long?
    ): List<Advisory> {
        val advisories = mutableListOf<Advisory>()

        // Rule 1: Broken/Lost items check
        val brokenOrLostByCategory = assets.filter { it.status == "BROKEN" || it.status == "LOST" }
            .groupBy { it.category }
        
        brokenOrLostByCategory.forEach { (category, list) ->
            if (list.size >= 4) {
                advisories.add(
                    Advisory(
                        "Schedule SDMC Visit",
                        "Category '$category' has ${list.size} broken/lost items. A management committee review is recommended.",
                        AdvisoryType.WARNING
                    )
                )
            }
        }

        // Rule 2: Repair backlog
        if (pendingRepairs.size >= 3) {
            advisories.add(
                Advisory(
                    "Action Required: Pending Repairs",
                    "There are ${pendingRepairs.size} pending repair requests. Please follow up with the service provider.",
                    AdvisoryType.URGENT
                )
            )
        }

        // Rule 3: Health check frequency
        val thirtyFiveDaysMillis = 35L * 24 * 60 * 60 * 1000
        if (lastAuditTimestamp == null || (System.currentTimeMillis() - lastAuditTimestamp) > thirtyFiveDaysMillis) {
            advisories.add(
                Advisory(
                    "Audit Overdue",
                    "No health check has been performed in over 35 days. Run a health check soon to ensure asset integrity.",
                    AdvisoryType.INFO
                )
            )
        }

        // Rule 4: Working percentage
        if (assets.isNotEmpty()) {
            val workingCount = assets.count { it.status == "WORKING" }
            val workingPercentage = (workingCount.toFloat() / assets.size.toFloat()) * 100
            if (workingPercentage >= 90) {
                advisories.add(
                    Advisory(
                        "Excellent Maintenance",
                        "${workingPercentage.toInt()}% of assets are in good condition. Keep it up!",
                        AdvisoryType.SUCCESS
                    )
                )
            }
        }

        // Rule 5: Issue trends
        val sevenDaysMillis = 7L * 24 * 60 * 60 * 1000
        val veryRecentIssues = recentIssues.filter { it.date > (System.currentTimeMillis() - sevenDaysMillis) }
        if (veryRecentIssues.size >= 5) {
            advisories.add(
                Advisory(
                    "Review Issue Trends",
                    "Many issues (${veryRecentIssues.size}) were logged in the last 7 days. Check for recurring problems.",
                    AdvisoryType.WARNING
                )
            )
        }

        // Default if no problems
        if (advisories.isEmpty() && assets.isNotEmpty()) {
            advisories.add(
                Advisory(
                    "Status Update",
                    "All assets look good based on recent logs.",
                    AdvisoryType.SUCCESS
                )
            )
        }

        return advisories
    }
}
