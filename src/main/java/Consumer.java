import model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class Consumer {

    private static String url = "http://94.198.50.185:7081/api/users";
    private static String sessionId;
    private static StringBuilder code = new StringBuilder();

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<User>> allUserResponse = restTemplate
                .exchange(url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<User>>() {
                        });

        sessionId = allUserResponse.getHeaders().getFirst("set-cookie");

        for (User user : allUserResponse.getBody()) {
            System.out.println(user.toString());
        }

        User newUser = new User(3L, "James", "Brown", (byte) 25);
        User updateUser = new User(3L, "Thomas", "Shelby", (byte) 25);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", sessionId);

        HttpEntity<User> request = new HttpEntity<>(newUser, headers);

        ResponseEntity<String> response = restTemplate.
                exchange(url,
                        HttpMethod.POST,
                        request,
                        String.class);

        code.append(response.getBody());

        request = new HttpEntity<>(updateUser, headers);

        response = restTemplate
                .exchange(url,
                        HttpMethod.PUT,
                        request,
                        String.class);

        code.append(response.getBody());

        String urlForDelete = url + "/" + updateUser.getId();

        response = restTemplate
                .exchange(urlForDelete,
                        HttpMethod.DELETE,
                        request,
                        String.class);

        code.append(response.getBody());
        System.out.println("Итоговый код: " + code);
    }
}
