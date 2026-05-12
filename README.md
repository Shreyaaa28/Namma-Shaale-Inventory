# Namma Shaale Inventory 🏫📦

**Namma Shaale Inventory** is a robust, offline-first Android application designed for government schools to efficiently manage, audit, and maintain their physical assets. Built with a focus on reliability in low-resource environments, it ensures that school resources—from furniture to IT equipment—are tracked and maintained without requiring constant internet connectivity.

---

## 🌟 Key Features

- **📶 Offline-First Architecture**: Perform full inventory audits, register new assets, and log issues without any internet connection. Data is persisted locally and can be synced later.
- **🏥 Asset Health Checks**: A guided workflow for periodic audits to verify the functional status ("health") of every item in the school.
- **📸 Visual Evidence**: Integrated CameraX support for capturing and storing photos of assets during registration or issue reporting.
- **📊 Maintenance Dashboard**: Real-time overview of asset distribution and health status using dynamic charts.
- **🛠️ Issue Tracking**: Streamlined system for logging repair requests and tracking the lifecycle of broken or missing assets.
- **🚀 Optimized for Low-End Devices**: Lightweight UI and efficient data handling designed to run smoothly on devices with limited RAM and processing power.

---

## 🛠️ Tech Stack

- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (Declarative UI)
- **Local Database**: [Room](https://developer.android.com/training/data-storage/room) (SQLite abstraction)
- **Dependency Injection**: [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- **Camera**: [CameraX](https://developer.android.com/training/camerax)
- **Concurrency**: [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html)
- **Architecture**: MVVM (Model-View-ViewModel)

---

## 🏗️ Architecture

The project follows the **MVVM (Model-View-ViewModel)** architectural pattern, ensuring a clean separation of concerns:

- **Model**: Room Database entities and Data Access Objects (DAOs).
- **View**: Composable screens and components using Jetpack Compose.
- **ViewModel**: Handles UI logic and communicates with the Repository to provide data to the UI via StateFlow.
- **Repository**: Acts as a single source of truth for data operations, abstracting the Room database.

---

## 📂 Project Structure

```
app/src/main/java/com/example/nammashaaleinventory/
├── data/               # Room Entities, DAOs, and Database configuration
├── repository/         # Data repositories
├── ui/
│   ├── components/     # Reusable UI elements (Cards, Buttons, etc.)
│   ├── navigation/     # NavGraph and route definitions
│   ├── screens/        # Full-page Composable screens
│   └── viewmodel/      # Business logic for each screen
└── utils/              # Helper classes and constants
```

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug or newer.
- Android SDK 24 (Android 7.0) or higher.
- A physical Android device (recommended for testing CameraX).

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/Shreyaaa28/Namma-Shaale-Inventory.git
   ```
2. Open the project in **Android Studio**.
3. Let Gradle sync and build the project.
4. Run the app on an emulator or physical device.

---

## 🛣️ Roadmap

- [ ] **QR/Barcode Scanning**: Quick asset identification and lookup.
- [ ] **PDF Reports**: Generate and export audit summaries locally.
- [ ] **Localization**: Multi-language support (Kannada, Hindi).
- [ ] **Cloud Sync**: Optional module for centralized district-level tracking.

---

## 📄 License

This project is developed for the **Namma Shaale Initiative**. All rights reserved.
