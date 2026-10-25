# M-BOIS (Aplikasi Android Pendamping M-BOIS Kuartet)

Aplikasi Android edukasi interaktif berbasis kartu AR (Augmented Reality) yang menggunakan teknologi AI untuk memberikan pengalaman belajar yang menarik dan inovatif.

## 📱 Tentang Aplikasi

M-BOIS adalah aplikasi mobile yang menggabungkan teknologi pemindaian kartu, AR, dan AI untuk menciptakan pengalaman pembelajaran interaktif. Pengguna dapat memindai kartu fisik untuk mengakses konten edukatif dengan visualisasi 3D dan berinteraksi dengan AI assistant.

## ✨ Fitur Utama

- **Autentikasi Pengguna**: Login menggunakan Google Sign-In dengan Firebase Authentication
- **Onboarding Screen**: Pengenalan aplikasi untuk pengguna baru
- **Pemindaian Kartu**: Teknologi barcode scanning untuk mendeteksi kartu fisik
- **Katalog Kartu**: Tampilan koleksi kartu edukatif berdasarkan kategori
- **Detail Kartu**: Informasi lengkap setiap kartu dengan gambar, audio, dan deskripsi
- **Mode Interaktif**: Integrasi dengan Gemini AI untuk pembelajaran interaktif
- **AR Support**: Dukungan konten Augmented Reality (AR)
- **Profil Pengguna**: Manajemen akun dan preferensi pengguna

## 🛠️ Teknologi yang Digunakan

### Framework & UI

- **Jetpack Compose**: Modern UI toolkit untuk Android
- **Material Design 3**: Desain UI yang konsisten dan modern
- **Navigation Compose**: Navigasi antar screen

### Backend & Database

- **Firebase Authentication**: Autentikasi pengguna dengan Google Sign-In
- **Firebase Firestore**: Database cloud untuk menyimpan data kartu
- **Google Play Services**: Integrasi layanan Google

### AI & ML

- **Gemini AI**: Chatbot interaktif untuk pembelajaran
- **ML Kit Barcode Scanning**: Pemindaian barcode pada kartu

### Camera & AR

- **CameraX**: API kamera modern untuk pemindaian
- **Accompanist Permissions**: Manajemen permission runtime

### Media & Networking

- **Coil**: Image loading library dengan dukungan network
- **OkHttp**: HTTP client untuk networking
- **Compose Markdown**: Rendering konten markdown

## 📋 Persyaratan

- **Minimum SDK**: Android 7.0 (API 24)
- **Target SDK**: Android 14 (API 36)
- **Compile SDK**: Android 14 (API 36)
- **Kotlin**: 1.9+
- **Gradle**: 8.0+

## 🔧 Setup dan Instalasi

### 1. Clone Repository

```bash
git clone https://github.com/khrlanamm/M-BOIS.git
cd M-BOIS
```

### 2. Konfigurasi Firebase

- Buat project baru di [Firebase Console](https://console.firebase.google.com/)
- Download file `google-services.json`
- Letakkan file tersebut di folder `app/`
- Aktifkan Firebase Authentication dan Firestore

### 3. Konfigurasi API Key

Buat file `local.properties` di root project dan tambahkan:

```properties
GEMINI_API_KEY=your_gemini_api_key_here
```

### 4. Build dan Run

```bash
./gradlew clean build
./gradlew installDebug
```

Atau gunakan Android Studio:

- Buka project di Android Studio
- Sync Gradle files
- Run aplikasi di emulator atau device fisik

## 📦 Struktur Project

```
com.kuartet.mbois/
├── model/              # Data models
├── repository/         # Data layer
├── viewmodel/          # ViewModels
├── ui/
│   ├── components/     # Reusable UI components
│   ├── screens/        # Screen composables
│   └── theme/          # Theme configuration
└── navigation/         # Navigation setup
```

## 🎯 Flow Aplikasi

1. **OnBoarding** → Pengenalan aplikasi
2. **Authentication** → Login dengan Google
3. **Home** → Katalog kartu berdasarkan kategori
4. **Scan** → Pemindaian kartu fisik
5. **Detail** → Informasi lengkap kartu
6. **Interactive** → Chat dengan AI tentang konten kartu
7. **Profile** → Pengaturan akun

## 🔐 Permissions

Aplikasi memerlukan permission berikut:

- **CAMERA**: Untuk pemindaian barcode pada kartu
- **INTERNET**: Untuk koneksi ke Firebase dan Gemini AI

## 📝 Dependencies Utama

```text
// Jetpack Compose
androidx.compose.ui
androidx.compose.material3
androidx.navigation.compose

// Firebase
com.google.firebase:firebase-auth
com.google.firebase:firebase-firestore

// AI & ML
com.google.ai.client.generativeai
com.google.mlkit:barcode-scanning

// Camera
androidx.camera:camera-camera2
androidx.camera:camera-lifecycle

// Image Loading
io.coil-kt:coil-compose
```

## 👥 Tim Pengembang

Dikembangkan oleh **Tim M-BOIS**

## 📄 Lisensi

Copyright © 2025 Tim M-BOIS. All rights reserved. Kode sumber dibuka hanya untuk referensi. Lihat [LICENSE](LICENSE).

## 📞 Kontak

Ketua Pengembang: khoirulanam1604@mail.ugm.ac.id

---

**M-BOIS** - Main Budaya, Ojo Ilang Saklawase!
