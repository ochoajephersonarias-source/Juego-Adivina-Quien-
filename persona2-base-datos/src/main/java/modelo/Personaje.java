package modelo;

/** DTO con la misma firma de datos que modelo.Personaje del proyecto del juego. */
public class Personaje {
    private final int idPersonaje;
    private final String nombre;
    private final String colorOjos;
    private final String tipoCabello;
    private final String colorCabello;
    private final String accesorios;
    private final boolean usaLentes;
    private final boolean tieneSonrisa;
    private final boolean tieneBarba;
    private final String rutaImagen;
    private final String colorPiel;

    public Personaje(int idPersonaje, String nombre, String colorOjos, String tipoCabello,
                     String colorCabello, String accesorios, boolean usaLentes,
                     boolean tieneSonrisa, boolean tieneBarba, String rutaImagen, String colorPiel) {
        this.idPersonaje = idPersonaje;
        this.nombre = nombre;
        this.colorOjos = colorOjos;
        this.tipoCabello = tipoCabello;
        this.colorCabello = colorCabello;
        this.accesorios = accesorios;
        this.usaLentes = usaLentes;
        this.tieneSonrisa = tieneSonrisa;
        this.tieneBarba = tieneBarba;
        this.rutaImagen = rutaImagen;
        this.colorPiel = colorPiel;
    }

    public int getIdPersonaje() { return idPersonaje; }
    public String getNombre() { return nombre; }
    public String getColorOjos() { return colorOjos; }
    public String getTipoCabello() { return tipoCabello; }
    public String getColorCabello() { return colorCabello; }
    public String getAccesorios() { return accesorios; }
    public boolean isUsaLentes() { return usaLentes; }
    public boolean isTieneSonrisa() { return tieneSonrisa; }
    public boolean isTieneBarba() { return tieneBarba; }
    public String getRutaImagen() { return rutaImagen; }
    public String getColorPiel() { return colorPiel; }
}
