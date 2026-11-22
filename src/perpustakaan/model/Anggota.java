package perpustakaan.model;

/**
 * Entitas Anggota perpustakaan.
 * Invariant: maksimal meminjam {@value #MAX_PINJAM} buku.
 */
public class Anggota {
    public static final int MAX_PINJAM = 3;

    private String idAnggota;
    private String nama;
    private int jumlahPinjamAktif;
    private boolean aktif;

    /** Konstruktor default. */
    public Anggota() {
        this.idAnggota = "";
        this.nama = "";
        this.jumlahPinjamAktif = 0;
        this.aktif = true;
    }

    /** Konstruktor berparameter. */
    public Anggota(String idAnggota, String nama) {
        setIdAnggota(idAnggota);
        setNama(nama);
        this.jumlahPinjamAktif = 0;
        this.aktif = true;
    }

    public String getIdAnggota() { return idAnggota; }

    public void setIdAnggota(String idAnggota) {
        if (idAnggota == null || idAnggota.trim().isEmpty()) {
            throw new IllegalArgumentException("ID anggota tidak boleh kosong.");
        }
        this.idAnggota = idAnggota.trim().toUpperCase();
    }

    public String getNama() { return nama; }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama tidak boleh kosong.");
        }
        this.nama = nama.trim();
    }

    public int getJumlahPinjamAktif() { return jumlahPinjamAktif; }

    public void setJumlahPinjamAktif(int jumlahPinjamAktif) {
        if (jumlahPinjamAktif < 0 || jumlahPinjamAktif > MAX_PINJAM) {
            throw new IllegalArgumentException("Jumlah pinjam harus 0 sampai " + MAX_PINJAM + ".");
        }
        this.jumlahPinjamAktif = jumlahPinjamAktif;
    }

    public boolean isAktif() { return aktif; }

    public void setAktif(boolean aktif) { this.aktif = aktif; }

    /** @return true jika anggota aktif dan belum mencapai batas pinjam. */
    public boolean bisaMeminjam() {
        return aktif && jumlahPinjamAktif < MAX_PINJAM;
    }

    /** Menambah pinjaman aktif. @return false jika batas tercapai. */
    public boolean tambahPinjamAktif() {
        if (jumlahPinjamAktif >= MAX_PINJAM) {
            return false;
        }
        jumlahPinjamAktif++;
        return true;
    }

    /** Mengurangi pinjaman aktif. @return false jika sudah 0. */
    public boolean kurangiPinjamAktif() {
        if (jumlahPinjamAktif <= 0) {
            return false;
        }
        jumlahPinjamAktif--;
        return true;
    }

    public void tampilkanInfo() {
        System.out.printf("%-8s | %-25s | pinjam aktif: %d/%d | %s%n", idAnggota, nama,
                jumlahPinjamAktif, MAX_PINJAM, aktif ? "aktif" : "nonaktif");
    }
}