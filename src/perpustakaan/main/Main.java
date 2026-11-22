package perpustakaan.main;

import perpustakaan.adt.DaftarBukuTerurut;
import perpustakaan.model.Anggota;
import perpustakaan.model.Buku;
import perpustakaan.model.Peminjaman;
import perpustakaan.model.Perpustakaan;
import perpustakaan.util.InputHelper;

import java.time.LocalDate;

/** Titik masuk program dan menu console. */
public class Main {
    private static Perpustakaan perpustakaan;

    public static void main(String[] args) {
        System.out.println("=== SISTEM PERPUSTAKAAN MINI ===");
        int kapasitas = InputHelper.bacaInt("Kapasitas daftar buku (1-100): ", 1, 100);
        perpustakaan = new Perpustakaan("Perpustakaan Mini", "Kendari", kapasitas);

        boolean jalan = true;
        while (jalan) {
            tampilkanMenu();
            int pilihan = InputHelper.bacaInt("Pilih menu (1-7): ", 1, 7);
            try {
                switch (pilihan) {
                    case 1: menuBuku(); break;
                    case 2: menuAnggota(); break;
                    case 3: menuTransaksi(); break;
                    case 4: menuCari(); break;
                    case 5: menuAdt(); break;
                    case 6: perpustakaan.tampilkanRingkasan(); break;
                    default:
                        System.out.println("Terima kasih. Program selesai.");
                        jalan = false;
                }
            } catch (IllegalArgumentException e) {
                // Pelanggaran validasi ditampilkan sebagai pesan, bukan menghentikan program
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
    }

    private static void tampilkanMenu() {
        System.out.println("\n===== MENU UTAMA =====");
        System.out.println("1. Kelola Buku");
        System.out.println("2. Kelola Anggota");
        System.out.println("3. Transaksi (pinjam / kembali)");
        System.out.println("4. Cari Data");
        System.out.println("5. Operasi ADT (Sorted List)");
        System.out.println("6. Laporan Ringkasan");
        System.out.println("7. Keluar");
    }

    private static void menuBuku() {
        System.out.println("-- Kelola Buku -- 1. Tambah  2. Tampilkan semua  0. Kembali");
        int p = InputHelper.bacaInt("Pilih: ", 0, 2);
        if (p == 1) {
            tambahBuku();
        } else if (p == 2) {
            perpustakaan.tampilkanSemuaBuku();
        }
    }

    private static void tambahBuku() {
        String kode = InputHelper.bacaString("Kode buku : ");
        String judul = InputHelper.bacaString("Judul     : ");
        String pengarang = InputHelper.bacaString("Pengarang : ");
        int stok = InputHelper.bacaInt("Stok      : ");
        Buku buku = new Buku(kode, judul, pengarang, stok);
        if (perpustakaan.tambahBuku(buku)) {
            System.out.println("Buku berhasil ditambahkan.");
        }
    }

    private static void menuAnggota() {
        System.out.println("-- Kelola Anggota -- 1. Tambah  2. Tampilkan semua  0. Kembali");
        int p = InputHelper.bacaInt("Pilih: ", 0, 2);
        if (p == 1) {
            String id = InputHelper.bacaString("ID anggota : ");
            String nama = InputHelper.bacaString("Nama       : ");
            if (perpustakaan.tambahAnggota(new Anggota(id, nama))) {
                System.out.println("Anggota berhasil ditambahkan.");
            }
        } else if (p == 2) {
            perpustakaan.tampilkanSemuaAnggota();
        }
    }

    private static void menuTransaksi() {
        System.out.println("-- Transaksi -- 1. Pinjam  2. Kembalikan  3. Tampilkan semua  0. Kembali");
        int p = InputHelper.bacaInt("Pilih: ", 0, 3);
        if (p == 1) {
            String idAnggota = InputHelper.bacaString("ID anggota : ");
            String kodeBuku = InputHelper.bacaString("Kode buku  : ");
            LocalDate tgl = InputHelper.bacaTanggal("Tanggal pinjam (yyyy-MM-dd, kosong=hari ini): ");
            int lama = InputHelper.bacaInt("Lama pinjam (hari): ", 1, 60);
            Peminjaman pm = perpustakaan.pinjamBuku(idAnggota, kodeBuku, tgl, lama);
            if (pm != null) {
                System.out.println("Peminjaman berhasil:");
                pm.tampilkanInfo();
            }
        } else if (p == 2) {
            String id = InputHelper.bacaString("ID peminjaman : ");
            LocalDate tgl = InputHelper.bacaTanggal("Tanggal kembali (yyyy-MM-dd, kosong=hari ini): ");
            double denda = perpustakaan.kembalikanBuku(id, tgl);
            if (denda >= 0) {
                System.out.printf("Buku dikembalikan. Denda: Rp %,.0f%n", denda);
            }
        } else if (p == 3) {
            perpustakaan.tampilkanSemuaPeminjaman();
        }
    }

    private static void menuCari() {
        System.out.println("-- Cari -- 1. Buku by kode  2. Buku by judul  3. Anggota by ID  0. Kembali");
        int p = InputHelper.bacaInt("Pilih: ", 0, 3);
        if (p == 1) {
            tampilHasilBuku(perpustakaan.getDaftarBuku().search(InputHelper.bacaString("Kode buku: ")));
        } else if (p == 2) {
            tampilHasilBuku(perpustakaan.getDaftarBuku().searchByJudul(InputHelper.bacaString("Judul: ")));
        } else if (p == 3) {
            Anggota a = perpustakaan.cariAnggota(InputHelper.bacaString("ID anggota: "));
            if (a == null) {
                System.out.println("Anggota tidak ditemukan.");
            } else {
                a.tampilkanInfo();
            }
        }
    }

    private static void tampilHasilBuku(Buku b) {
        if (b == null) {
            System.out.println("Buku tidak ditemukan.");
        } else {
            b.tampilkanInfo();
        }
    }

    private static void menuAdt() {
        System.out.println("-- Operasi ADT -- 1. insert  2. remove  3. search  4. get(index)"
                + "  5. tampilkan  6. size/isEmpty/isFull  0. Kembali");
        int p = InputHelper.bacaInt("Pilih: ", 0, 6);
        DaftarBukuTerurut adt = perpustakaan.getDaftarBuku();
        switch (p) {
            case 1:
                tambahBuku();
                break;
            case 2:
                Buku hapus = adt.remove(InputHelper.bacaString("Kode buku yang dihapus: "));
                if (hapus != null) {
                    System.out.println("Dihapus: " + hapus.getKodeBuku());
                }
                break;
            case 3:
                tampilHasilBuku(adt.search(InputHelper.bacaString("Kode buku: ")));
                break;
            case 4:
                Buku g = adt.get(InputHelper.bacaInt("Indeks: "));
                if (g != null) {
                    g.tampilkanInfo();
                }
                break;
            case 5:
                adt.tampilkan();
                break;
            case 6:
                System.out.println("size=" + adt.size() + ", isEmpty=" + adt.isEmpty()
                        + ", isFull=" + adt.isFull() + ", kapasitas=" + adt.getKapasitas());
                break;
            default:
                break;
        }
    }
}