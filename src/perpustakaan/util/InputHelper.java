package perpustakaan.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/** Utilitas pembacaan input console yang aman (tidak membuat program crash). */
public class InputHelper {
    private static final Scanner SCANNER = new Scanner(System.in);

    private InputHelper() { }

    private static String bacaBaris() {
        if (!SCANNER.hasNextLine()) {
            System.out.println("\nInput berakhir. Program keluar.");
            System.exit(0);
        }
        return SCANNER.nextLine().trim();
    }

    /** Membaca teks tidak kosong. */
    public static String bacaString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = bacaBaris();
            if (!s.isEmpty()) {
                return s;
            }
            System.out.println("[ERROR] Input tidak boleh kosong.");
        }
    }

    /** Membaca bilangan bulat tanpa batas rentang. */
    public static int bacaInt(String prompt) {
        return bacaInt(prompt, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /** Membaca bilangan bulat dalam rentang [min, max]. */
    public static int bacaInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String s = bacaBaris();
            if (s.isEmpty()) {
                System.out.println("[ERROR] Input tidak boleh kosong.");
                continue;
            }
            try {
                int n = Integer.parseInt(s);
                if (n < min || n > max) {
                    System.out.println("[ERROR] Nilai harus antara " + min + " dan " + max + ".");
                    continue;
                }
                return n;
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Masukkan angka yang valid.");
            }
        }
    }

    /** Membaca tanggal format yyyy-MM-dd; kosong = hari ini. */
    public static LocalDate bacaTanggal(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = bacaBaris();
            if (s.isEmpty()) {
                return LocalDate.now();
            }
            try {
                return LocalDate.parse(s);
            } catch (DateTimeParseException e) {
                System.out.println("[ERROR] Format tanggal harus yyyy-MM-dd.");
            }
        }
    }
}