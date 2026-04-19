package app.api;

import app.api.controller.VehicleController;
import app.core.usecase.VehicleAdapter;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

/**
 * Base class for controller tests that require a mock-backed Javalin server.
 *
 * <p>Starts one Javalin instance per test class on a random port, injects a
 * {@link VehicleAdapter} mock, and resets the mock before each test method.
 * Subclasses configure the mock and use {@link #get} / {@link #post} to interact
 * with the server, receiving an {@link HttpResult} with status code and response body.
 */
abstract class BaseControllerTest implements WithAssertions {

    /** Mock adapter shared across all test methods within one test class. */
    protected static VehicleAdapter mockAdapter;

    private static Javalin testApp;
    private static int port;

    /**
     * Starts the Javalin server with a mock-backed controller before any test runs.
     */
    @BeforeAll
    static void startServer() {
        mockAdapter = Mockito.mock(VehicleAdapter.class);
        final var controller = new VehicleController(mockAdapter);

        testApp = Javalin.create(config -> {
            config.showJavalinBanner = false;
            config.jsonMapper(new JavalinJackson()
                .updateMapper(mapper ->
                    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)));
        }).start(0);

        port = testApp.port();
        controller.registerRoutes(testApp);
    }

    /**
     * Stops the Javalin server after all tests in the class have run.
     */
    @AfterAll
    static void stopServer() {
        if (testApp != null) {
            testApp.stop();
        }
    }

    /**
     * Resets the mock adapter to a clean state before each test.
     */
    @BeforeEach
    void resetMocks() {
        Mockito.reset(mockAdapter);
    }

    /**
     * Sends a GET request to the given path and returns the response.
     *
     * @param path the request path (e.g. "/health")
     * @return the HTTP result with status code and body
     * @throws IOException if the connection fails
     */
    protected HttpResult get(final String path) throws IOException {
        final var conn = openConnection(path, "GET");
        return readResponse(conn);
    }

    /**
     * Sends a POST request with a JSON body to the given path and returns the response.
     *
     * @param path the request path (e.g. "/vehicles")
     * @param body the JSON request body
     * @return the HTTP result with status code and body
     * @throws IOException if the connection fails
     */
    protected HttpResult post(final String path, final String body) throws IOException {
        final var conn = openConnection(path, "POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        try (final var os = conn.getOutputStream()) {
            os.write(body.getBytes(StandardCharsets.UTF_8));
        }
        return readResponse(conn);
    }

    private HttpURLConnection openConnection(
            final String path, final String method) throws IOException {
        final var url = new URL("http://localhost:" + port + path);
        final var conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        return conn;
    }

    private HttpResult readResponse(final HttpURLConnection conn) throws IOException {
        final int status = conn.getResponseCode();
        final InputStream stream = status >= HttpURLConnection.HTTP_BAD_REQUEST
            ? conn.getErrorStream()
            : conn.getInputStream();
        final String responseBody = stream != null
            ? new String(stream.readAllBytes(), StandardCharsets.UTF_8)
            : "";
        return new HttpResult(status, responseBody);
    }

    /**
     * Holds the HTTP status code and response body from a test request.
     *
     * @param statusCode the HTTP status code
     * @param body       the response body as a string
     */
    record HttpResult(int statusCode, String body) { }
}
