# Rancangan Sistem Perpustakaan Mini

- Nama: Nur Intani Munajat
- NIM: 2515080
- Topik: Perpustakaan Mini
- ADT: Sorted List (array terurut)

## 1. Deskripsi Sistem
<!-- 2-4 kalimat: sistem ini untuk siapa, masalah apa yang diselesaikan,
     apa yang bisa dilakukan pengguna lewat menu console. -->

## 2. Daftar Class dan Alasan Pemilihan
### Buku
- Objek dunia nyata:
- Alasan dijadikan class:

### Anggota
- Objek dunia nyata:
- Alasan dijadikan class:

### Peminjaman
- Objek dunia nyata:
- Alasan dijadikan class:

### Perpustakaan
- Objek dunia nyata:
- Alasan dijadikan class:

### ADT (nama class Sorted List Anda)
- Fungsi dalam aplikasi:

### Main/Menu
- Fungsi:

## 3. Aturan Bisnis (Invariant)
<!-- Tulis dengan kata-kata Anda sendiri. Untuk tiap aturan, tentukan
     di class dan method mana aturan dijaga, dan pesan error saat dilanggar. -->
### Aturan 1:
- Dijaga di (class.method):
- Pesan error:

### Aturan 2:
- Dijaga di (class.method):
- Pesan error:

## 4. Penentuan Topik dan Jenis ADT

- Digit terakhir NIM adalah 0, sehingga topik adalah **Perpustakaan Mini**.
- Digit kedua dari belakang adalah 8, dan 8 mod 3 = 2, sehingga ADT adalah **Sorted List (array terurut)**.

Class wajib: Buku, Anggota, Peminjaman, Perpustakaan.

Invariant wajib:
1. Anggota maksimal meminjam 3 buku pada waktu yang sama.
2. Stok buku tidak boleh negatif.

Atribut kunci pengurutan ADT adalah **kodeBuku**. Dengan kunci ini, pencarian berdasarkan kode memakai binary search, sedangkan pencarian berdasarkan judul memakai linear search.

---