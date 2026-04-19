package app.api;

import java.net.HttpURLConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class VehicleControllerTest extends BaseControllerTest {

    @Test
    @DisplayName("Should return 500 when an unexpected error occurs during vehicle creation")
    void shouldReturn500OnUnexpectedError() throws Exception {
        Mockito.when(mockAdapter.createVehicle(Mockito.any()))
            .thenThrow(new RuntimeException("unexpected error"));

        final var result = post("/vehicles", "{\"type\":\"CAR\",\"state\":\"NEW\"}");

        assertThat(result.statusCode()).isEqualTo(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }
}
