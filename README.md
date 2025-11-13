<p align="center">
<!--
CATATAN: Pastikan Anda mengganti URL placeholder ini dengan URL logo Anda yang sudah diunggah ke GitHub/Penyimpanan Aset.
Contoh: https://www.google.com/search?q=https://raw.githubusercontent.com/username/repo/main/assets/logo.png
-->
<img src="https://www.google.com/search?q=https://user-images.githubusercontent.com/username/repo/assets/Logo.png" width="200" alt="Logo M-BOIS Kuartet">
</p>

<h1 align="center">M-BOIS Kuartet (Aplikasi Android)</h1>

<p align="center">
<strong>Main Budaya Ojo Ilang Saklawase</strong>
</p>

<p align="center">
Aplikasi pendamping digital untuk Kartu Kuartet Budaya M-BOIS, menghidupkan budaya Jawa Timur melalui Augmented Reality (AR) dan Artificial Intelligence (AI).
</p>

<!-- Lencana (Badges) yang sudah diperbaiki ukurannya menggunakan shields.io -->

<p align="center">
<img alt="Platform" src="https://www.google.com/search?q=https://img.shields.io/badge/Platform-Android-3DDC84%3Fstyle%3Dfor-the-badge%26logo%3Dandroid">
<img alt="Kotlin" src="https://www.google.com/search?q=https://img.shields.io/badge/Kotlin-1.9.x-7F52FF%3Fstyle%3Dfor-the-badge%26logo%3Dkotlin">
<img alt="Jetpack Compose" src="https://www.google.com/search?q=https://img.shields.io/badge/UI-Jetpack_Compose-4285F4%3Fstyle%3Dfor-the-badge%26logo%3Djetpackcompose">
<img alt="AR" src="https://www.google.com/search?q=https://img.shields.io/badge/AR-ARCore-FFC107%3Fstyle%3Dfor-the-badge%26logo%3Dgoogle">
<img alt="AI" src="https://www.google.com/search?q=https://img.shields.io/badge/AI-Gemini_API-1E88E5%3Fstyle%3Dfor-the-badge%26logo%3Dgoogleai">
<img alt="3D Tool" src="https://www.google.com/search?q=https://img.shields.io/badge/3D-Vectary-blue%3Fstyle%3Dfor-the-badge%26logo%3Dvectary">
</p>

📜 Tentang Proyek

M-BOIS Kuartet adalah sebuah inovasi yang menjembatani permainan kartu fisik (Phygital) dengan pengalaman digital yang imersif. Aplikasi Android ini dirancang untuk menjadi pendamping (companion app) dari 48 kartu kuartet fisik yang menampilkan budaya Jawa Timur.

Tujuan utamanya adalah untuk meningkatkan literasi budaya di kalangan generasi muda dengan mengubah metode pembelajaran pasif (membaca kartu) menjadi pengalaman aktif, interaktif, dan menarik melalui teknologi AR, model 3D, dan AI Chatbot.

✨ Fitur Utama

Aplikasi ini dibangun dengan alur pengguna yang intuitif dan kaya fitur:

Galeri Budaya:

Halaman utama yang menampilkan katalog lengkap 48 kartu budaya.

Pengguna dapat menjelajahi semua budaya (Makanan, Tarian, Senjata, dll.) bahkan tanpa memiliki kartu fisiknya.

Scan QR Code (Fisik ke Digital):

Fitur pemindaian QR Code yang terintegrasi untuk memindai marker di belakang setiap kartu fisik.

Membawa pengguna secara instan dari kartu fisik ke Layar Interaktif yang relevan.

Model 3D Interaktif:

Menampilkan model 3D berkualitas tinggi dari setiap item budaya (misal: Candi, Makanan, Alat Musik).

Pengguna dapat melakukan rotate, zoom, dan pan untuk melihat objek dari segala sisi.

AI Chatbot Kontekstual:

Bagian paling inovatif: layar split-screen yang menampilkan model 3D di atas dan AI Chatbot di bawah.

Chatbot AI (ditenagai oleh Google Gemini API) sudah di-grounding untuk menjadi "ahli" pada topik yang sedang ditampilkan.

Pengguna dapat bertanya secara mendalam ("Terbuat dari apa ini?", "Kapan tradisi ini dilakukan?") dan mendapatkan jawaban yang relevan dan aman.

Integrasi Augmented Reality (AR):

Tombol "Lihat di Ruang Anda" yang akan memproyeksikan model 3D ke dunia nyata menggunakan ARCore.

Memberikan pengalaman imersif seakan-akan objek budaya tersebut benar-benar ada di depan pengguna.

Voice Over (Audio Narasi):

Tombol audio yang menyediakan narasi singkat dan menarik tentang fakta unik setiap budaya, meningkatkan aksesibilitas.

Sistem Login (Google):

Proses onboarding yang mulus dan login satu ketuk menggunakan Google Sign-In untuk personalisasi.

📱 Alur Aplikasi

Diagram alur pengguna sederhana dari aplikasi:

<!-- Ini adalah sintaks Mermaid yang benar agar diagram dapat dirender -->

graph TD
    A[Splash Screen] --> B(Onboarding)
    B --> C{Login Google}
    C --> D[Layar Utama (Galeri Budaya)]
    
    D -- Klik Scan QR --> E[Layar Scan (CameraX)]
    D -- Klik Kartu --> F[Layar Detail Kartu]
    
    F -- Klik Tombol 3D/AI --> G(Layar Interaktif 3D + AI)
    E -- Scan Berhasil --> G
    
    G -- Klik Tombol AR --> H[Layar AR (ARCore)]
    G -- Interaksi Chat --> G


🛠️ Tumpukan Teknologi (Tech Stack) & Arsitektur

Aplikasi ini dibangun native untuk Android menggunakan teknologi modern dan praktik terbaik untuk memastikan performa, skalabilitas, dan kemudahan pengembangan.

Bahasa Pemrograman: Kotlin

Sepenuhnya ditulis dalam Kotlin. Memanfaatkan fitur-fitur modern seperti Coroutines untuk asynchronous programming dan type safety.

Framework UI: Jetpack Compose

UI aplikasi ini dibangun secara deklaratif menggunakan Jetpack Compose. Ini memungkinkan pembuatan UI yang dinamis, reaktif, dan indah dengan lebih sedikit kode.

Arsitektur: MVVM (Model-View-ViewModel)

Mengikuti pola arsitektur MVVM yang direkomendasikan Google.

View (Composable Functions) bereaksi terhadap perubahan State dari ViewModel.

ViewModel berisi logika bisnis dan mengelola State menggunakan Kotlin Flows/StateFlow.

Model (Repository) mengurus pengambilan data (baik dari API maupun lokal).

Komponen Jetpack Utama:

Compose Navigation: Mengelola alur navigasi antar screen (Composable).

ViewModel: Untuk menyimpan dan mengelola state UI.

State & StateFlow: Digunakan untuk state management yang reaktif.

CameraX: Untuk membangun fungsionalitas scanner QR Code yang custom.

Augmented Reality (AR): ARCore

Menggunakan ARCore SDK untuk fungsionalitas AR, secara spesifik untuk menempatkan model 3D di permukaan dunia nyata.

Artificial Intelligence (AI): Google Gemini API

Fungsionalitas AI Chatbot ditenagai oleh Gemini API.

Menggunakan teknik grounding (RAG) dengan mengirimkan prompt sistem yang spesifik dan kontekstual untuk memastikan jawaban AI tetap fokus pada topik budaya yang dipilih.

3D Tooling: Filament & Vectary

Menggunakan Filament (atau library setara) untuk rendering 3D performa tinggi di dalam view aplikasi.

Aset model 3D (.glb/.gltf) disiapkan dan dioptimalkan menggunakan Vectary.

Networking: Retrofit & OkHttp

Untuk berkomunikasi dengan Gemini API dan endpoint lainnya.

🖼️ Tampilan Aplikasi (Mockup)

Tampilan dan screenshot aplikasi akan ditambahkan pada pembaruan berikutnya.

📄 Lisensi

Hak Cipta (c) 2025 - Tim M-BOIS.
