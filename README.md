# 🎓 Form Mahasiswa - Jetpack Compose CRUD

Aplikasi manajemen data mahasiswa sederhana yang dibangun menggunakan **Android Jetpack Compose (Material 3)**. Proyek ini merupakan latihan implementasi logika CRUD (Create, Read, Update, Delete) dengan fokus pada state management yang reaktif.

---

## 📺 Video Demo
Lihat demo aplikasi selengkapnya di YouTube:  
👉 **[Klik di sini untuk menonton video demo](https://youtu.be/Qwtik0sWG_w)**

---

## ✨ Fitur Utama
Aplikasi ini memiliki fitur lengkap untuk mengelola data mahasiswa secara efisien:

*   **Tambah Data (Create):** Input NIM dan Nama Lengkap mahasiswa.
*   **Tampil Data (Read):** Daftar mahasiswa ditampilkan secara dinamis dalam kartu (Card) yang rapi.
*   **Ubah Data (Update):** Pilih mahasiswa dari daftar untuk mengedit informasi mereka.
*   **Hapus Data (Delete):** Menghapus data mahasiswa yang dipilih dari sistem.
*   **Pencarian (Search):** Cari mahasiswa berdasarkan Nama atau NIM secara real-time.
*   **Pengurutan (Sorting):** Urutkan daftar mahasiswa dari A-Z (Ascending) atau Z-A (Descending).

## 🛠️ Teknologi yang Digunakan
*   **Bahasa:** Kotlin
*   **UI Framework:** Jetpack Compose (Material 3)
*   **State Management:** `remember`, `mutableStateOf`, `mutableStateListOf`
*   **Navigasi:** Scaffold & TopAppBar (Material Design 3)

## 💡 Penjelasan Alur (Realm Latihan)
Aplikasi ini menggunakan konsep **Mock-up Database Logic**. Meskipun tampilan merujuk pada "Realm", data saat ini dikelola menggunakan `State` di dalam memori (RAM) untuk tujuan latihan alur data.

**Kenapa menggunakan alur ini?**
1.  **Latihan State Management:** Memastikan UI bereaksi secara instan terhadap setiap perubahan data (Recomposition).
2.  **Struktur Data Siap Pakai:** Data class dan logika ID unik sudah disesuaikan agar mudah dihubungkan ke Realm Database di tahap pengembangan selanjutnya.
3.  **Simulasi Query:** Fitur Search dan Sort mensimulasikan query database pada list data yang dinamis.

---

## 🚀 Cara Menjalankan Proyek
1.  Clone repository ini.
2.  Buka proyek menggunakan **Android Studio Ladybug** atau versi terbaru.
3.  Pastikan SDK Android sudah terinstal.
4.  Klik **Run 'app'** untuk mencoba di Emulator atau Perangkat Fisik.

---

**Developed for learning purposes - UPH 24SI3**
