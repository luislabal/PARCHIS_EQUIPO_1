package MODELO;

public class Jugador {

    private final int id;
    private final String nombre;
    private String avatar;
    private String color;
    private boolean esAnfitrion;
    private boolean preparado;

    public Jugador(int id, String nombre, String avatar) {
        this.id = id;
        this.nombre = nombre;
        this.avatar = avatar;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public boolean isEsAnfitrion() { return esAnfitrion; }
    public void setEsAnfitrion(boolean esAnfitrion) { this.esAnfitrion = esAnfitrion; }
    public boolean isPreparado() { return preparado; }
    public void setPreparado(boolean preparado) { this.preparado = preparado; }

    @Override
    public boolean equals(Object o) {
        return o instanceof Jugador && ((Jugador) o).id == id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return nombre;
    }
}
