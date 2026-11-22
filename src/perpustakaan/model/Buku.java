package perpustakaan.model;

/**
 * Entitas Buku yang dipinjam anggota.
 * Invariant: stok tidak boleh negatif.
 */
public class Buku {
    private String kodeBuku;
    private String judul;
    private String pengarang;
    private int stok;

    /** Konstruktor default. */
    public Buku() {
        this.kodeBuku = "";
        this.judul = "";
        this.pengarang = "";
        this.stok = 0;
    }

    /** Konstruktor berparameter, memakai setter agar validasi ikut berjalan. */
    public Buku(String kodeBuku, String judul, String pengarang, int stok) {
        setKodeBuku(kodeBuku);
        setJudul(judul);
        setPengarang(pengarang);
        setStok(stok);
    }

    public String getKodeBuku() { return kodeBuku; }

    public void setKodeBuku(String kodeBuku) {
        if (kodeBuku == null || kodeBuku.trim().isEmpty()) {
            throw new IllegalArgumentException("Kode buku tidak boleh kosong.");
        }
        this.kodeBuku = kodeBuku.trim().toUpperCase();
    }

    public String getJudul() { return judul; }

    public void setJudul(String judul) {
        if (judul == null || judul.trim().isEmpty()) {
            throw new IllegalArgumentException("Judul tidak boleh kosong.");
        }
        this.judul = judul.trim();
    }

    public String getPengarang() { return pengarang; }

    public void setPengarang(String pengarang) {
        if (pengarang == null || pengarang.trim().isEmpty()) {
            throw new IllegalArgumentException("Pengarang tidak boleh kosong.");
        }
        this.pengarang = pengarang.trim();
    }

    public int getStok() { return stok; }

    public void setStok(int stok) {
        // Validasi invariant: stok tidak boleh negatif
        if (stok < 0) {
            throw new IllegalArgumentException("Stok tidak boleh negatif.");
        }
        this.stok = stok;
    }

    /** @return true jika masih ada stok. */
    public boolean isTersedia() { return stok > 0; }

    /** Mengurangi stok 1. @return false jika stok sudah 0. */
    public boolean kurangiStok() {
        if (stok == 0) {
            return false;
        }
        stok--;
        return true;
    }

    /** Menambah stok. @return false jika jumlah tidak positif. */
    public boolean tambahStok(int jumlah) {
        if (jumlah <= 0) {
            return false;
        }
        stok += jumlah;
        return true;
    }

    public void tampilkanInfo() {
        System.out.printf("%-8s | %-28s | %-20s | stok: %d%n", kodeBuku, judul, pengarang, stok);
    }
}