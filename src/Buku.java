public class Buku {

    private String kodeBuku;
    private String judul;
    private String penulis;
    private int tahunTerbit;
    private int stok;

    // Constructor Default
    public Buku() {
        this.kodeBuku = "";
        this.judul = "";
        this.penulis = "";
        this.tahunTerbit = 0;
        this.stok = 0;
    }

    // Constructor Parameter
    public Buku(String kodeBuku, String judul, String penulis, int tahunTerbit, int stok) {
        this.kodeBuku = kodeBuku;
        this.judul = judul;
        this.penulis = penulis;
        this.tahunTerbit = tahunTerbit;
        this.stok = stok;
    }
}