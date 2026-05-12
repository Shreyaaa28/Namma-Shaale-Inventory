package com.example.nammashaaleinventory.data.repository

import com.example.nammashaaleinventory.data.local.dao.AssetDao
import com.example.nammashaaleinventory.data.local.dao.ConditionCheckDao
import com.example.nammashaaleinventory.data.local.dao.IssueLogDao
import com.example.nammashaaleinventory.data.local.dao.RepairFlagDao
import com.example.nammashaaleinventory.data.local.entities.AssetEntity
import com.example.nammashaaleinventory.data.local.entities.ConditionCheckEntity
import com.example.nammashaaleinventory.data.local.entities.IssueLogEntity
import com.example.nammashaaleinventory.data.local.entities.RepairFlagEntity
import kotlinx.coroutines.flow.Flow

class InventoryRepository(
    private val assetDao: AssetDao,
    private val conditionCheckDao: ConditionCheckDao,
    private val issueLogDao: IssueLogDao,
    private val repairFlagDao: RepairFlagDao
) {
    // Asset methods
    val allAssets: Flow<List<AssetEntity>> = assetDao.getAllAssets()
    fun getAssetById(id: String) = suspend { assetDao.getAssetById(id) }
    fun getAssetCount() = assetDao.getAssetCount()
    fun getAssetCountByStatus(status: String) = assetDao.getAssetCountByStatus(status)
    fun searchAssets(query: String) = assetDao.searchAssets(query)
    fun getAssetsByCategory(category: String) = assetDao.getAssetsByCategory(category)
    
    suspend fun insertAsset(asset: AssetEntity) = assetDao.insertAsset(asset)
    suspend fun updateAsset(asset: AssetEntity) = assetDao.updateAsset(asset)
    suspend fun deleteAsset(asset: AssetEntity) = assetDao.deleteAsset(asset)

    // Condition Check methods
    fun getChecksForAsset(assetId: String) = conditionCheckDao.getChecksForAsset(assetId)
    suspend fun insertConditionCheck(check: ConditionCheckEntity) = conditionCheckDao.insertCheck(check)
    suspend fun getLastCheck() = conditionCheckDao.getLastCheck()

    // Issue Log methods
    val allIssues: Flow<List<IssueLogEntity>> = issueLogDao.getAllIssues()
    fun getIssuesForAsset(assetId: String) = issueLogDao.getIssuesForAsset(assetId)
    fun getIssuesByType(type: String) = issueLogDao.getIssuesByType(type)
    fun getIssueCountSince(since: Long) = issueLogDao.getIssueCountSince(since)
    suspend fun insertIssue(issue: IssueLogEntity) = issueLogDao.insertIssue(issue)
    suspend fun updateIssue(issue: IssueLogEntity) = issueLogDao.updateIssue(issue)

    // Repair Flag methods
    val pendingRepairs: Flow<List<RepairFlagEntity>> = repairFlagDao.getPendingRepairs()
    fun getPendingRepairCount() = repairFlagDao.getPendingRepairCount()
    fun getRepairsForAsset(assetId: String) = repairFlagDao.getRepairsForAsset(assetId)
    suspend fun insertRepairFlag(repairFlag: RepairFlagEntity) = repairFlagDao.insertRepairFlag(repairFlag)
    suspend fun updateRepairFlag(repairFlag: RepairFlagEntity) = repairFlagDao.updateRepairFlag(repairFlag)
}
