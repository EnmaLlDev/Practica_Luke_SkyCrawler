package crawler;

import com.google.gson.Gson;
import models.FilmReport;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HTTP_Request {

    private static final String URI_STAR_WARS = "https://swapi.dev/api";
    private final Gson gson = new Gson();

    public static void main(String[] args) {
        try {
            new HTTP_Request().run();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() throws IOException, InterruptedException {
        HttpResponse<String> responseGet = metodoGet("/films/1/");
        if (responseGet == null) {
            System.out.println("Respuesta nula");
            return;
        }

        FilmReport infoApi = gson.fromJson(responseGet.body(), FilmReport.class);

        System.out.println("Código: " + responseGet.statusCode());
        System.out.println("-------------------------------------------------");

        // Ajusta estas líneas según los campos reales de FilmReport
        if (infoApi != null) {
            System.out.println("Parsed FilmReport: " + infoApi);
            // Si FilmReport tiene una lista, por ejemplo getResults():
            // infoApi.getResults().forEach(System.out::println);
        } else {
            System.out.println("No se pudo parsear la respuesta a FilmReport");
        }
    }

    static HttpResponse<String> metodoGet(String uriPath) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URI_STAR_WARS + uriPath))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("-------------------------------------------------");
        System.out.println("Metodo: " + request.method());
        System.out.println("Código: " + response.statusCode());
        System.out.println("-------------------------------------------------");
        System.out.println("Respuesta: " + response.body());

        return response;
    }
}
