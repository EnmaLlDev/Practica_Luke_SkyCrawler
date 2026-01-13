package crawler;

import com.google.gson.Gson;
import models.*;
import models.Character;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class SWCrawler {
    private static final String BASE_URL = "https://swapi.dev/api";
    private final HttpClient client;
    private final Gson gson;

    public SWCrawler() {
        this.client = createHttpClient();
        this.gson = new Gson();
    }

    // Crear un HttpClient que ignore certificados SSL inválidos (solo para desarrollo)
    private HttpClient createHttpClient() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
            };

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            return HttpClient.newBuilder()
                    .sslContext(sslContext)
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            System.err.println("Error creando HttpClient: " + e.getMessage());
            return HttpClient.newHttpClient();
        }
    }

    // Método para crawlear una película
    public CompletableFuture<FilmReport> crawlFilm(int filmId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("Iniciando crawler para película ID: " + filmId);

                // 1. Obtener información de la película
                Film film = getFilm(filmId);
                if (film == null) {
                    throw new RuntimeException("No se pudo obtener la película");
                }

                System.out.println("Película obtenida: " + film.getTitle());

                // 2. Crawlear planetas
                List<Planet> planets = crawlPlanets(film.getPlanets());
                System.out.println("Planetas crawleados: " + planets.size());

                // 3. Crawlear especies
                List<Species> species = crawlSpecies(film.getSpecies());
                System.out.println("Especies crawleadas: " + species.size());

                // 4. Crawlear personajes con sus vehículos y naves
                List<FilmReport.CharacterWithVehicles> characters = crawlCharacters(film.getCharacters());
                System.out.println("Personajes crawleados: " + characters.size());

                return new FilmReport(film.getTitle(), planets, species, characters);

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error al crawlear la película: " + e.getMessage());
            }
        });
    }

    private Film getFilm(int filmId) throws IOException, InterruptedException {
        String url = BASE_URL + "/films/" + filmId + "/";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return gson.fromJson(response.body(), Film.class);
        }
        return null;
    }

    private List<Planet> crawlPlanets(List<String> planetUrls) {
        if (planetUrls == null || planetUrls.isEmpty()) {
            return new ArrayList<>();
        }

        return planetUrls.stream()
                .map(url -> {
                    try {
                        return fetchResource(url, Planet.class);
                    } catch (Exception e) {
                        System.err.println("Error al obtener planeta: " + url);
                        return null;
                    }
                })
                .filter(planet -> planet != null)
                .collect(Collectors.toList());
    }

    private List<Species> crawlSpecies(List<String> speciesUrls) {
        if (speciesUrls == null || speciesUrls.isEmpty()) {
            return new ArrayList<>();
        }

        return speciesUrls.stream()
                .map(url -> {
                    try {
                        return fetchResource(url, Species.class);
                    } catch (Exception e) {
                        System.err.println("Error al obtener especie: " + url);
                        return null;
                    }
                })
                .filter(species -> species != null)
                .collect(Collectors.toList());
    }

    private List<FilmReport.CharacterWithVehicles> crawlCharacters(List<String> characterUrls) {
        if (characterUrls == null || characterUrls.isEmpty()) {
            return new ArrayList<>();
        }

        return characterUrls.stream()
                .map(url -> {
                    try {
                        Character character = fetchResource(url, Character.class);
                        if (character == null) return null;

                        // Obtener naves del personaje
                        List<Starship> starships = new ArrayList<>();
                        if (character.getStarships() != null) {
                            starships = character.getStarships().stream()
                                    .map(starshipUrl -> {
                                        try {
                                            return fetchResource(starshipUrl, Starship.class);
                                        } catch (Exception e) {
                                            return null;
                                        }
                                    })
                                    .filter(s -> s != null)
                                    .collect(Collectors.toList());
                        }

                        // Obtener vehículos del personaje
                        List<Vehicle> vehicles = new ArrayList<>();
                        if (character.getVehicles() != null) {
                            vehicles = character.getVehicles().stream()
                                    .map(vehicleUrl -> {
                                        try {
                                            return fetchResource(vehicleUrl, Vehicle.class);
                                        } catch (Exception e) {
                                            return null;
                                        }
                                    })
                                    .filter(v -> v != null)
                                    .collect(Collectors.toList());
                        }

                        return new FilmReport.CharacterWithVehicles(character, starships, vehicles);

                    } catch (Exception e) {
                        System.err.println("Error al obtener personaje: " + url);
                        return null;
                    }
                })
                .filter(characterWithVehicles -> characterWithVehicles != null)
                .collect(Collectors.toList());
    }

    private <T> T fetchResource(String url, Class<T> clazz) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return gson.fromJson(response.body(), clazz);
        }
        return null;
    }
}
