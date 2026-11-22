package perpustakaan.adt;

import perpustakaan.model.Buku;

/**
 * ADT Sorted List berbasis array. Elemen selalu urut naik berdasarkan kodeBuku.
 * Atribut internal private; akses hanya lewat operasi publik.
 */
public class DaftarBukuTerurut {
    public static final int KAPASITAS_DEFAULT = 100;

    private Buku[] data;
    private int jumlah;
    private int kapasitas;

    /** Konstruktor default (kapasitas 100). */
    public DaftarBukuTerurut() {
        this(KAPASITAS_DEFAULT);
    }

    /** Konstruktor dengan kapasitas tertentu. */
    public DaftarBukuTerurut(int kapasitas) {
        this.kapasitas = kapasitas > 0 ? kapasitas : KAPASITAS_DEFAULT;
        this.data = new Buku[this.kapasitas];
        this.jumlah = 0;
    }

    public int size() { return jumlah; }
    public boolean isEmpty() { return jumlah == 0; }
    public boolean isFull() { return jumlah == kapasitas; }
    public int getKapasitas() { return kapasitas; }

    /**
     * Binary search. @return indeks jika ketemu, atau -(posisiSisip)-1 jika tidak.
     */
    private int cariIndeks(String kode) {
        int lo = 0;
        int hi = jumlah - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            int c = data[mid].getKodeBuku().compareTo(kode);
            if (c == 0) {
                return mid;
            } else if (c < 0) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return -(lo + 1);
    }

    /** Menyisipkan buku dengan tetap terurut. Prasyarat: tidak penuh, kode belum ada. */
    public boolean insert(Buku buku) {
        if (buku == null) {
            System.out.println("[ADT] Buku tidak boleh null.");
            return false;
        }
        if (isFull()) {
            System.out.println("[ADT] Daftar penuh (kapasitas " + kapasitas + "). Buku tidak ditambahkan.");
            return false;
        }
        int idx = cariIndeks(buku.getKodeBuku());
        if (idx >= 0) {
            System.out.println("[ADT] Kode buku " + buku.getKodeBuku() + " sudah ada.");
            return false;
        }
        int pos = -(idx + 1);
        // Geser elemen yang lebih besar ke kanan
        for (int i = jumlah; i > pos; i--) {
            data[i] = data[i - 1];
        }
        data[pos] = buku;
        jumlah++;
        return true;
    }

    /** Menghapus buku berdasarkan kode. @return buku yang dihapus, atau null. */
    public Buku remove(String kodeBuku) {
        if (isEmpty()) {
            System.out.println("[ADT] Daftar kosong, tidak ada yang dihapus.");
            return null;
        }
        int idx = cariIndeks(normalisasi(kodeBuku));
        if (idx < 0) {
            System.out.println("[ADT] Buku dengan kode " + kodeBuku + " tidak ditemukan.");
            return null;
        }
        Buku hapus = data[idx];
        for (int i = idx; i < jumlah - 1; i++) {
            data[i] = data[i + 1];
        }
        data[--jumlah] = null;
        return hapus;
    }

    /** Binary search berdasarkan kode. @return buku atau null. */
    public Buku search(String kodeBuku) {
        if (isEmpty()) {
            return null;
        }
        int idx = cariIndeks(normalisasi(kodeBuku));
        return idx >= 0 ? data[idx] : null;
    }

    /** Linear search berdasarkan judul (tidak peka huruf besar/kecil). */
    public Buku searchByJudul(String judul) {
        if (judul == null) {
            return null;
        }
        for (int i = 0; i < jumlah; i++) {
            if (data[i].getJudul().equalsIgnoreCase(judul.trim())) {
                return data[i];
            }
        }
        return null;
    }

    /** Mengambil buku pada indeks. @return buku atau null jika di luar batas. */
    public Buku get(int index) {
        if (index < 0 || index >= jumlah) {
            System.out.println("[ADT] Indeks " + index + " di luar batas (jumlah data: " + jumlah + ").");
            return null;
        }
        return data[index];
    }

    public void tampilkan() {
        if (isEmpty()) {
            System.out.println("[ADT] Daftar buku kosong.");
            return;
        }
        for (int i = 0; i < jumlah; i++) {
            System.out.print("[" + i + "] ");
            data[i].tampilkanInfo();
        }
    }

    private String normalisasi(String kode) {
        return kode == null ? "" : kode.trim().toUpperCase();
    }
}