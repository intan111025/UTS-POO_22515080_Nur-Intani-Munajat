package perpustakaan.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Transaksi peminjaman yang menghubungkan satu Anggota dan satu Buku.
 */
public class Peminjaman {
    public static final double DENDA_PER_HARI = 1000.0;

    private String idPeminjaman;
    private Anggota anggota;
    private Buku buku;
    private LocalDate tanggalPinjam;
    private int lamaPinjamHari;
    private boolean dikembalikan;

    /** Konstruktor default. */
    public Peminjaman() {
        this.idPeminjaman = "";
        this.tanggalPinjam = LocalDate.now();
        this.lamaPinjamHari = 7;
        this.dikembalikan = false;
    }

    /** Konstruktor berparameter. */
    public Peminjaman(String idPeminjaman, Anggota anggota, Buku buku,
                      LocalDate tanggalPinjam, int lamaPinjamHari) {
        if (anggota == null || buku == null) {
            throw new IllegalArgumentException("Anggota dan buku wajib diisi.");
        }
        this.idPeminjaman = idPeminjaman;
        this.anggota = anggota;
        this.buku = buku;
        this.tanggalPinjam = tanggalPinjam;
        setLamaPinjamHari(lamaPinjamHari);
        this.dikembalikan = false;
    }

    public String getIdPeminjaman() { return idPeminjaman; }
    public Anggota getAnggota() { return anggota; }
    public Buku getBuku() { return buku; }
    public LocalDate getTanggalPinjam() { return tanggalPinjam; }
    public int getLamaPinjamHari() { return lamaPinjamHari; }

    public void setLamaPinjamHari(int lamaPinjamHari) {
        if (lamaPinjamHari <= 0) {
            throw new IllegalArgumentException("Lama pinjam harus lebih dari 0 hari.");
        }
        this.lamaPinjamHari = lamaPinjamHari;
    }

    public boolean isDikembalikan() { return dikembalikan; }

    public LocalDate hitungJatuhTempo() {
        return tanggalPinjam.plusDays(lamaPinjamHari);
    }

    /** Denda = hari terlambat x DENDA_PER_HARI (0 jika tepat waktu). */
    public double hitungDenda(LocalDate tanggalKembali) {
        long terlambat = ChronoUnit.DAYS.between(hitungJatuhTempo(), tanggalKembali);
        return terlambat > 0 ? terlambat * DENDA_PER_HARI : 0;
    }

    /** Menandai dikembalikan. @return besar denda. */
    public double kembalikan(LocalDate tanggalKembali) {
        this.dikembalikan = true;
        return hitungDenda(tanggalKembali);
    }

    public void tampilkanInfo() {
        System.out.printf("%-6s | %-8s %-15s | %-8s %-22s | pinjam: %s | tempo: %s | %s%n",
                idPeminjaman, anggota.getIdAnggota(), anggota.getNama(),
                buku.getKodeBuku(), buku.getJudul(), tanggalPinjam, hitungJatuhTempo(),
                dikembalikan ? "dikembalikan" : "dipinjam");
    }
}