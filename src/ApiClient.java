import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

public class ApiClient {
    private static final HttpClient HTTP = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    private static String baseUrl() {
        String url = System.getProperty("LIB_API_URL");
        if (url == null || url.isBlank()) {
            url = System.getenv().getOrDefault("LIB_API_URL", "http://localhost:8080");
        }
        if (url.endsWith("/")) url = url.substring(0, url.length() - 1);
        return url;
    }

    public static ApiResponse postForm(String path, Map<String, String> form) throws IOException, InterruptedException {
        String body = formEncode(form);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl() + path))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> res = HTTP.send(req, HttpResponse.BodyHandlers.ofString());
        return new ApiResponse(res.statusCode(), res.body() == null ? "" : res.body());
    }

    public static ApiResponse get(String path, Map<String, String> query) throws IOException, InterruptedException {
        String qs = formEncode(query);
        String url = baseUrl() + path + (qs.isBlank() ? "" : ("?" + qs));
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();
        HttpResponse<String> res = HTTP.send(req, HttpResponse.BodyHandlers.ofString());
        return new ApiResponse(res.statusCode(), res.body() == null ? "" : res.body());
    }

    public static Map<String, String> parseKeyValueBody(String body) {
        Map<String, String> m = new LinkedHashMap<>();
        if (body == null) return m;
        for (String line : body.split("\\R")) {
            int idx = line.indexOf('=');
            if (idx <= 0) continue;
            String k = line.substring(0, idx).trim();
            String v = line.substring(idx + 1).trim();
            m.put(k, v);
        }
        return m;
    }

    private static String formEncode(Map<String, String> form) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> e : form.entrySet()) {
            if (sb.length() > 0) sb.append('&');
            sb.append(URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8));
            sb.append('=');
            sb.append(URLEncoder.encode(e.getValue() == null ? "" : e.getValue(), StandardCharsets.UTF_8));
        }
        return sb.toString();
    }

    public record ApiResponse(int status, String body) {}
}

