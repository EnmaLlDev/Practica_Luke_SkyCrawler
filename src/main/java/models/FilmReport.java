package models;

import java.util.List;

public class FilmReport {
    private String titulo;
    private List<Planet> planetas;
    private List<Species> especies;
    private List<CharacterWithVehicles> personajes;

    public FilmReport(String titulo, List<Planet> planetas, List<Species> especies, List<CharacterWithVehicles> personajes) {
        this.titulo = titulo;
        this.planetas = planetas;
        this.especies = especies;
        this.personajes = personajes;
    }

    // Getters
    public String getTitulo() { return titulo; }
    public List<Planet> getPlanetas() { return planetas; }
    public List<Species> getEspecies() { return especies; }
    public List<CharacterWithVehicles> getPersonajes() { return personajes; }

    // Métodos
    public void print() {
        System.out.println("\n=== INFORME DE LA PELÍCULA: " + titulo + " ===");

        System.out.println("\nPlanetas(" + planetas.size() + "): ");
        for (Planet planeta : planetas) {
            System.out.println("  - " + planeta.getName() +
                " | Clima: " + planeta.getClimate() +
                " | Terreno: " + planeta.getTerrain() +
                " | Población: " + planeta.getPopulation());
        }

        System.out.println("\nEspecies(" + especies.size() + "): ");
        for (Species especie : especies) {
            System.out.println("  - " + especie.getName() +
                " | Clasificación: " + especie.getClassification() +
                " | Color de pelo: " + especie.getHair_colors() +
                " | Lenguaje: " + especie.getLanguage());
        }

        System.out.println("\nPersonajes (" + personajes.size() + "): ");
        for (CharacterWithVehicles personaje : personajes) {
            System.out.println("  Personaje: " + personaje.getCharacter().getName());

            if (!personaje.getStarships().isEmpty()) {
                System.out.println("    Naves: " + String.join(", ",
                    personaje.getStarships().stream().map(Starship::getName).toList()));
            } else {
                System.out.println("    Naves: Ninguna");
            }

            if (!personaje.getVehicles().isEmpty()) {
                System.out.println("    Vehículos: " + String.join(", ",
                    personaje.getVehicles().stream().map(Vehicle::getName).toList()));
            } else {
                System.out.println("    Vehículos: Ninguno");
            }
        }
    }

    // Clase auxiliar para personajes con sus vehículos y naves
    public static class CharacterWithVehicles {
        private Character character;
        private List<Starship> starships;
        private List<Vehicle> vehicles;

        public CharacterWithVehicles(Character character, List<Starship> starships, List<Vehicle> vehicles) {
            this.character = character;
            this.starships = starships;
            this.vehicles = vehicles;
        }

        public Character getCharacter() { return character; }
        public List<Starship> getStarships() { return starships; }
        public List<Vehicle> getVehicles() { return vehicles; }
    }
}