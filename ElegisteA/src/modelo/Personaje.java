package modelo;

public class Personaje implements Adivinable {
    private int idPersonaje;
    private String colorOjos;
    private String nombre;
    private String tipoCabello;
    private String colorCabello;
    private String accesorios;
    private boolean usaLentes;
    private boolean tieneSonrisa;
    private boolean tieneBarba;
    private String rutaImagen;
    private String colorPiel;
    private String genero;

    public Personaje(int idPersonaje, String nombre, String colorOjos, String tipoCabello,
                     String colorCabello, String accesorios, boolean usaLentes,
                     boolean tieneSonrisa, boolean tieneBarba, String rutaImagen,
                     String colorPiel, String genero) {
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
       this.genero =genero;
}
    @Override
    public boolean esCandidatoValido(Personaje candidato) {
        if (candidato == null) {
            return false;
        }
        return this.idPersonaje == candidato.idPersonaje;
    }

    public int getIdPersonaje() {
        return idPersonaje;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public String getColorOjos() {
        return colorOjos;
    }

    public String getColorCabello() {
        return colorCabello;
    }

    public String getTipoCabello() {
        return tipoCabello;
    }

    public String getColorPiel() {
        return colorPiel;
    }

    public String getAccesorios() {
        return accesorios;
    }

    public String getGenero() {return genero; }

    public boolean isUsaLentes() {
        return usaLentes;
    }

    public boolean isTieneSonrisa() {
        return tieneSonrisa;
    }

    public boolean isTieneBarba() {
        return tieneBarba;
    }
}