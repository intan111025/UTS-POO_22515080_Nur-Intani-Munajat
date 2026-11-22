package perpustakaan.model;

import perpustakaan.adt.DaftarBukuTerurut;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Pengelola perpustakaan: memegang koleksi buku (ADT), anggota, dan peminjaman,
 * serta menjalankan aturan bisnis.
 */
public class Perpustakaan {
    private String nama;
    private String alamat;
    private DaftarBukuTerurut daftarBuku;
    private ArrayList<Anggota> daftarAnggota;
    private ArrayList<Peminjaman> daftarPeminjaman;
    private int counterPeminjaman;
    private double totalDenda;

    /** Konstruktor default. */
    public Perpustakaan() {
        this("Perpustakaan Mini", "-", DaftarBukuTerurut.KAPASITAS_DEFAULT);
    }

    public Perpustakaan(String nama, String alamat) {
        this(nama, alamat, DaftarBukuTerurut.KAPASITAS_DEFAULT);
    }

    /** Konstruktor lengkap dengan kapasitas ADT. */
    public Perpustakaan(String nama, String alamat, int kapasitasBuku) {
        setNama(nama);
        setAlamat(alamat);
        this.daftarBuku = new DaftarBukuTerurut(kapasitasBuku);
        this.daftarAnggota = new ArrayList<>();
        this.daftarPeminjaman = new ArrayList<>();
        this.counterPeminjaman = 0;
        this.totalDenda = 0;
    }

    public String getNama() { return nama; }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama perpustakaan tidak boleh kosong.");
        }
        this.nama = nama.trim();
    }

    public String getAlamat() { return alamat; }

    public void setAlamat(String alamat) {
        this.alamat = (alamat == null || alamat.trim().isEmpty()) ? "-" : alamat.trim();
    }

    public DaftarBukuTerurut getDaftarBuku() { return daftarBuku; }

    public boolean tambahBuku(Buku buku) {
        return daftarBuku.insert(buku);
    }

    public boolean tambahAnggota(Anggota anggota) {
        if (cariAnggota(anggota.getIdAnggota()) != null) {
            System.out.println("[ERROR] ID anggota " + anggota.getIdAnggota() + " sudah terdaftar.");
            return false;
        }
        daftarAnggota.add(anggota);
        return true;
    }

    public Anggota cariAnggota(String idAnggota) {
        if (idAnggota == null) {
            return null;
        }
        for (Anggota a : daftarAnggota) {
            if (a.getIdAnggota().equalsIgnoreCase(idAnggota.trim())) {
                return a;
            }
        }
        return null;
    }

    public Peminjaman cariPeminjaman(String idPeminjaman) {
        if (idPeminjaman == null) {
            return null;
        }
        for (Peminjaman p : daftarPeminjaman) {
            if (p.getIdPeminjaman().equalsIgnoreCase(idPeminjaman.trim())) {
                return p;
            }
        }
        return null;
    }

    /**
     * Memproses peminjaman dan menegakkan aturan bisnis.
     * @return objek Peminjaman, atau null jika ditolak (pesan ditampilkan).
     */
    public Peminjaman pinjamBuku(String idAnggota, String kodeBuku, LocalDate tanggal, int lamaHari) {
        Anggota anggota = cariAnggota(idAnggota);
        if (anggota == null) {
            System.out.println("[ERROR] Anggota tidak ditemukan.");
            return null;
        }
        Buku buku = daftarBuku.search(kodeBuku);
        if (buku == null) {
            System.out.println("[ERROR] Buku tidak ditemukan.");
            return null;
        }
        // Aturan: maksimal 3 buku per anggota
        if (!anggota.bisaMeminjam()) {
            System.out.println("[ERROR] Batas peminjaman " + Anggota.MAX_PINJAM
                    + " buku tercapai atau anggota nonaktif.");
            return null;
        }
        // Aturan: stok tidak boleh negatif
        if (!buku.isTersedia()) {
            System.out.println("[ERROR] Stok buku habis.");
            return null;
        }
        buku.kurangiStok();
        anggota.tambahPinjamAktif();
        counterPeminjaman++;
        Peminjaman p = new Peminjaman(String.format("P%03d", counterPeminjaman),
                anggota, buku, tanggal, lamaHari);
        daftarPeminjaman.add(p);
        return p;
    }

    /** Mengembalikan buku. @return denda, atau -1 jika gagal. */
    public double kembalikanBuku(String idPeminjaman, LocalDate tanggalKembali) {
        Peminjaman p = cariPeminjaman(idPeminjaman);
        if (p == null) {
            System.out.println("[ERROR] Peminjaman tidak ditemukan.");
            return -1;
        }
        if (p.isDikembalikan()) {
            System.out.println("[ERROR] Buku pada peminjaman ini sudah dikembalikan.");
            return -1;
        }
        double denda = p.kembalikan(tanggalKembali);
        p.getBuku().tambahStok(1);
        p.getAnggota().kurangiPinjamAktif();
        totalDenda += denda;
        return denda;
    }

    public void tampilkanSemuaBuku() {
        daftarBuku.tampilkan();
    }

    public void tampilkanSemuaAnggota() {
        if (daftarAnggota.isEmpty()) {
            System.out.println("Belum ada anggota.");
            return;
        }
        for (Anggota a : daftarAnggota) {
            a.tampilkanInfo();
        }
    }

    public void tampilkanSemuaPeminjaman() {
        if (daftarPeminjaman.isEmpty()) {
            System.out.println("Belum ada peminjaman.");
            return;
        }
        for (Peminjaman p : daftarPeminjaman) {
            p.tampilkanInfo();
        }
    }

    public void tampilkanRingkasan() {
        int aktif = 0;
        for (Peminjaman p : daftarPeminjaman) {
            if (!p.isDikembalikan()) {
                aktif++;
            }
        }
        System.out.println("=== RINGKASAN " + nama.toUpperCase() + " ===");
        System.out.println("Jumlah judul buku    : " + daftarBuku.size() + " / " + daftarBuku.getKapasitas());
        System.out.println("Jumlah anggota       : " + daftarAnggota.size());
        System.out.println("Total peminjaman     : " + daftarPeminjaman.size());
        System.out.println("Peminjaman aktif     : " + aktif);
        System.out.printf("Total denda terkumpul: Rp %,.0f%n", totalDenda);
    }
}