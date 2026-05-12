# Internship Report: Namma Shaale Inventory

**Project Name:** Namma Shaale Inventory: An Offline-First Asset Audit System  
**Organisation:** Namma Shaale Initiative  
**Submitted By:** [Student Name]  
**Registration No:** [Registration Number]  
**Period:** May 2026  

---

## Table of Contents
1. [Chapter 1: Introduction](#chapter-1-introduction)
2. [Chapter 2: About the Organisation](#chapter-2-about-the-organisation)
3. [Chapter 3: System Requirements](#chapter-3-system-requirements)
4. [Chapter 4: Methodology and Architecture](#chapter-4-methodology-and-architecture)
5. [Chapter 5: Project Description: Namma Shaale Inventory](#chapter-5-project-description-namma-shaale-inventory)
6. [Chapter 6: Code Implementation](#chapter-6-code-implementation)
7. [Chapter 7: Results and Output](#chapter-7-results-and-output)
8. [Chapter 8: Challenges Faced](#chapter-8-challenges-faced)
9. [Chapter 9: Conclusion](#chapter-9-conclusion)
10. [Chapter 10: Future Enhancements and Learning Outcomes](#chapter-10-future-enhancements-and-learning-outcomes)
11. [References](#references)

---

## Chapter 1: Introduction

### 1.1 Background of the Project
In the current educational landscape, government schools face significant challenges in managing their assets. The shift towards digital record-keeping is often hampered by poor internet connectivity and limited technical resources. This project, **Namma Shaale Inventory**, aims to bridge this gap by providing a robust, offline-first solution for auditing and maintaining school assets.

### 1.2 Problem Statement
Existing inventory systems typically require persistent internet access and complex server configurations. Manual record-keeping in physical ledgers leads to data redundancy, loss of information, and lack of historical tracking for asset maintenance. There is a critical need for a lightweight mobile tool that can run on low-end devices and function without a network.

### 1.3 Objectives of the Internship
- To develop an offline-first Android application using Jetpack Compose.
- To implement a 'Health Check' audit system for periodic asset verification.
- To integrate local data persistence using Room Database.
- To enable visual evidence capture via Camera integration.
- To design a user-centric UI for non-technical school staff.

---

## Chapter 2: About the Organisation

### 2.1 About Namma Shaale Initiative
Namma Shaale Initiative is a technology solutions provider specializing in educational technology and social impact applications. The organization focuses on creating digital tools that empower local communities and institutions through efficient resource management and simplified workflows.

### 2.2 Domain and Focus Area
The domain of this project falls under **Educational Resource Planning (ERP)** and **Digital Infrastructure Management**. The primary focus is on Government School Ecosystems where reliability and simplicity are key performance indicators.

### 2.3 Tools and Technologies Used
- **Kotlin:** Primary programming language.
- **Jetpack Compose:** Modern toolkit for building native UI.
- **Room Database:** SQLite abstraction for local data storage.
- **CameraX:** API for reliable photo capture.
- **Hilt:** Dependency injection for modular architecture.
- **Coroutines & Flow:** Asynchronous programming and data streams.

---

## Chapter 3: System Requirements

### 3.1 Hardware Requirements
- **Device:** Android Smartphone or Tablet.
- **Processor:** Quad-core 1.2 GHz or higher.
- **RAM:** Minimum 2GB (4GB Recommended).
- **Storage:** 100MB free space for app and local database.
- **Camera:** 5MP Rear camera for asset documentation.

### 3.2 Software Requirements
- **Operating System:** Android 7.0 (Nougat) or higher.
- **Development IDE:** Android Studio Ladybug/Flamingo.
- **Database:** Room (SQLite) version 2.6+.

---

## Chapter 4: Methodology and Architecture

### 4.1 Development Methodology
The project followed an **Iterative Agile Methodology**. Features were developed in short sprints, starting with the core database schema, followed by the UI components, and finally the camera and audit logic. This allowed for frequent testing on actual low-end devices.

### 4.2 Application Architecture
The app implements the **MVVM (Model-View-ViewModel)** architecture. This ensures a clean separation between the UI (Compose screens) and the business logic (ViewModels and Repository), facilitating easier maintenance and testing.

### 4.3 Navigation Flow
- **Splash Screen:** Branding and initialization.
- **Dashboard:** Overview of asset counts and health status.
- **Health Check:** Guided audit workflow for verifying assets.
- **Asset List:** Browsing and searching the inventory.
- **Asset Details:** Viewing history and updating condition.

---

## Chapter 5: Project Description: Namma Shaale Inventory

### 5.1 App Overview and Vision
Namma Shaale Inventory is designed as a 'digital caretaker' for school resources. The name reflects its mission to ensure the 'health' (functional status) of every asset in the school. Its vision is to eliminate resource wastage and ensure that every student has access to working equipment.

### 5.2 Scope of the Application
- Registration of all school assets (Furniture, IT, Books).
- Periodic 'Health Check' audits to update asset status.
- Issue logging for broken or missing items.
- Offline-first data management with local database.

### 5.3 Module Description
- **Inventory Module:** Handles the CRUD operations for assets.
- **Audit Module:** Manages the 'Health Check' logic and verification flow.
- **Media Module:** Interface for capturing and storing asset photos.
- **Reporting Module:** Generates local summaries of asset conditions.

---

## Chapter 6: Code Implementation

### 6.1 Key Code Snippets

**Inventory Repository Implementation:**
```kotlin
class InventoryRepository(private val assetDao: AssetDao) {
    val allAssets: Flow<List<AssetEntity>> = assetDao.getAllAssets()
    
    suspend fun updateAssetStatus(asset: AssetEntity, newStatus: String) {
        assetDao.updateAsset(asset.copy(status = newStatus))
    }
}
```

**Health Check UI Component (Compose):**
```kotlin
@Composable
fun HealthCheckScreen(viewModel: AssetViewModel) {
    val assets by viewModel.assets.collectAsState()
    
    LazyColumn { 
        items(assets) { asset ->
            AssetAuditCard(
                asset = asset,
                onStatusChange = { newStatus ->
                    viewModel.updateAssetStatus(asset, newStatus)
                }
            )
        }
    }
}
```

---

## Chapter 7: Results and Output

### 7.1 Application Screenshots
- **Figure 7.1:** Dashboard Overview - Displays health distribution chart.
- **Figure 7.2:** Audit Flow - Step-by-step verification of items.
- **Figure 7.3:** Asset Entry - Form for adding new inventory items.

### 7.2 Feature Verification
Verification was performed by populating the database with 100+ assets and performing audits in 'Airplane Mode'. The app successfully maintained data integrity and allowed photo capture, proving its offline-first capability.

---

## Chapter 8: Challenges Faced

### 8.1 Technical Challenges
- **CameraX Stability:** Achieving consistent camera performance across different low-end Android hardware required significant experimentation with aspect ratios and image analyzers.
- **Room Database Migrations:** Handling changes to the asset schema while preserving existing local data was a complex task requiring precise migration scripts.

### 8.2 Non-Technical Challenges
- **Requirement Ambiguity:** Defining a set of 'Asset Statuses' (Working, Needs Repair, Broken) that was both comprehensive and easy for school staff to understand.

---

## Chapter 9: Conclusion
The development of **Namma Shaale Inventory** has demonstrated that modern mobile technologies like Jetpack Compose and Room can be effectively used to solve real-world logistical problems in low-resource environments. The project successfully met its objectives of providing an offline-first asset audit system that is both reliable and user-friendly.

---

## Chapter 10: Future Enhancements and Learning Outcomes

### Future Enhancements
- Barcode/QR Code Scanning for instant asset identification.
- Automatic PDF report generation for school audits.
- Multi-language support (Kannada, Hindi) for local staff.
- Cloud Syncing module for centralized district-level tracking.

### Learning Outcomes
- Deep understanding of the Android MVVM architecture.
- Proficiency in building declarative UIs with Jetpack Compose.
- Experience in managing local data persistence and offline states.
- Improved problem-solving skills for hardware-specific issues.

---

## References
- Android Developer Documentation: https://developer.android.com
- Jetpack Compose Tutorial: https://developer.android.com/jetpack/compose
- Room Persistence Library: https://developer.android.com/training/data-storage/room
- CameraX Guide: https://developer.android.com/training/camerax
