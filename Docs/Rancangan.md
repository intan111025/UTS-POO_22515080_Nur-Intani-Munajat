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
### 5.Buku
Atribut:
- kodeBuku : String
- judul : String
- pengarang : String
- stok : int

Konstruktor:
+ Buku()
+ Buku(kodeBuku : String, judul : String, pengarang : String, stok : int)

Method:
+ getKodeBuku() : String
+ setKodeBuku(kodeBuku : String) : void
+ getJudul() : String
+ setJudul(judul : String) : void
+ getPengarang() : String
+ setPengarang(pengarang : String) : void
+ getStok() : int
+ setStok(stok : int) : void
+ isTersedia() : boolean
+ kurangiStok() : boolean
+ tambahStok(jumlah : int) : boolean
+ tampilkanInfo() : void

Hubungan: disimpan oleh DaftarBukuTerurut, dan bisa muncul di 0..* Peminjaman.

### 6.Anggota
Atribut:
+ MAX_PINJAM : int = 3 (static, konstanta)
- idAnggota : String
- nama : String
- jumlahPinjamAktif : int
- aktif : boolean

Konstruktor:
+ Anggota()
+ Anggota(idAnggota : String, nama : String)

Method:
+ getIdAnggota() : String
+ setIdAnggota(idAnggota : String) : void
+ getNama() : String
+ setNama(nama : String) : void
+ getJumlahPinjamAktif() : int
+ setJumlahPinjamAktif(jumlah : int) : void
+ isAktif() : boolean
+ setAktif(aktif : boolean) : void
+ bisaMeminjam() : boolean
+ tambahPinjamAktif() : boolean
+ kurangiPinjamAktif() : boolean
+ tampilkanInfo() : void

Hubungan: melakukan 0..3 Peminjaman aktif (asosiasi).