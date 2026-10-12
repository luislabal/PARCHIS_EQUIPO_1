package MODELO;

public final class Validaciones {

    private Validaciones() { }

    public static String nombreUsuario(String texto) {
        String t = texto == null ? "" : texto.trim();
        if (t.isEmpty()) return "Escribe un nombre de usuario";
        if (t.length() < 3 || t.length() > 15) return "El nombre debe tener entre 3 y 15 caracteres";
        if (!t.matches("[\\p{L}\\p{N}_]+")) return "Solo se permiten letras, numeros y guion bajo";
        return null;
    }

    public static String nombreSala(String texto) {
        String t = texto == null ? "" : texto.trim();
        if (t.isEmpty()) return "Escribe el nombre de la sala";
        if (t.length() < 3 || t.length() > 20) return "El nombre de la sala debe tener entre 3 y 20 caracteres";
        if (!t.matches("[\\p{L}\\p{N} _-]+")) return "El nombre de la sala solo admite letras, numeros, espacios, _ y -";
        return null;
    }

    public static String claveSala(String texto) {
        String t = texto == null ? "" : texto.trim();
        if (t.isEmpty()) return "Escribe la clave de la sala";
        if (t.length() < 4 || t.length() > 8) return "La clave debe tener entre 4 y 8 caracteres";
        if (!t.matches("[A-Za-z0-9]+")) return "La clave solo admite letras y numeros";
        return null;
    }

    public static String maxJugadores(int max) {
        if (max < 2 || max > 4) return "Elige 2, 3 o 4 jugadores";
        return null;
    }
}
