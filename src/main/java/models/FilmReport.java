package models;

public class FilmReport {
    private static final String TITULO_DE_LA_PELICULA = "" ;
    private static final String NUMERO_TOTAL_DE_PLANETAS = "" ;
    private static final String NUMERO_TOTAL_DE_ESPECIES = "" ;
    private static final String NUMERO_TOTAL_DE_PERSONAJES = "" ;

    // .....

    // Métodos
    public void print() {
        System.out.println("\n=== INFORME DE LA PELÍCULA: " + TITULO_DE_LA_PELICULA + " ===");
        System.out.println("Planetas(" + NUMERO_TOTAL_DE_PLANETAS + "): ");
        // TODO: Printear todos los planetas con una información básica de cada uno (name, cliamte, terrain...)
        System.out.println("Especies(" + NUMERO_TOTAL_DE_ESPECIES + "): ");
        // TODO: Printear todas las especies con una información básica de cada una (name, classification, hair_colors...)
        System.out.println("Personajes (" + NUMERO_TOTAL_DE_PERSONAJES + "): ");
        // TODO: Printear todos los personajes incluyendo los nombres de las naves y vehiculos que pilota
        // Personaje: Luke Skywalker
        //    Naves: X-Wing, Imperial Shuttle
        //    Vehículos: Snowspeeder, Imperial Speeder Bike
    }
}
