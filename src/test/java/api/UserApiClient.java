package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import dto.UserCredentials;
import dto.UserRegistrationData;
import io.restassured.response.Response;

public class UserApiClient {

    /**
     * Создает пользователя через API
     */
    public Response createUser(UserRegistrationData userData) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .baseUri(AppConfig.BASE_URL)
                .body(userData)
                .when()
                .post(AppConfig.USER_CREATE_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    /**
     * Получает access token для пользователя
     */
    public String getAccessToken(UserRegistrationData userData) {
        UserCredentials credentials = new UserCredentials(
                userData.getEmail(),
                userData.getPassword()
        );

        // Пробуем получить чистый токен без "Bearer "
        String response = RestAssured.given()
                .contentType(ContentType.JSON)
                .baseUri(AppConfig.BASE_URL)
                .body(credentials)
                .when()
                .post(AppConfig.LOGIN_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .path("accessToken");

        // Проверяем, есть ли в токене "Bearer "
        if (response != null && response.startsWith("Bearer ")) {
            // Убираем "Bearer " из начала токена
            return response.substring(7);
        }
        return response;
    }

    /**
     * Удаляет пользователя через API
     */
    public void deleteUser(UserRegistrationData userData) {
        String accessToken = getAccessToken(userData);

        if (accessToken == null || accessToken.trim().isEmpty()) {
            throw new RuntimeException("Не удалось получить токен для удаления пользователя");
        }

        System.out.println("Токен для удаления: " +
                (accessToken.length() > 20 ? accessToken.substring(0, 20) + "..." : accessToken));

        // Сначала пробуем с Bearer, так как логи показывают, что это работает
        String[] authAttempts = {"Bearer " + accessToken, accessToken};

        for (String authHeader : authAttempts) {
            try {
                Response response = RestAssured.given()
                        .header("Authorization", authHeader)
                        .contentType(ContentType.JSON)
                        .baseUri(AppConfig.BASE_URL)
                        .when()
                        .delete(AppConfig.USER_DELETE_ENDPOINT);

                System.out.println("DELETE запрос. Статус код: " + response.getStatusCode());
                System.out.println("Ответ: " + response.getBody().asString());

                // Проверяем только 202, так как по логам это единственный успешный код
                if (response.getStatusCode() == 202) {
                    System.out.println("Пользователь успешно удален");
                    return;
                }

            } catch (Exception e) {
                System.err.println("Ошибка при DELETE: " + e.getMessage());
            }
        }

        throw new RuntimeException("Не удалось удалить пользователя");
    }

    /**
     * Создает тестового пользователя с рандомными данными
     */
    public UserRegistrationData createTestUser() {
        long timestamp = System.currentTimeMillis();
        String name = "TestUser_" + timestamp;
        String email = "test_" + timestamp + "@example.com";
        String password = "TestPass123";

        UserRegistrationData userData = new UserRegistrationData(email, password, name);
        Response response = createUser(userData);

        // Логируем ответ
        System.out.println("Пользователь создан. Ответ: " + response.getBody().asString());

        return userData;
    }

    /**
     * Упрощенный метод - просто логируем информацию о пользователе
     */
    public void logUserInfo(UserRegistrationData userData) {
        System.out.println("Для ручного удаления:");
        System.out.println("   Email: " + userData.getEmail());
        System.out.println("   Пароль: " + userData.getPassword());
    }
}