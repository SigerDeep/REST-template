package Service;

import lombok.extern.slf4j.Slf4j;
import model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
public class ResponseServiceImpl implements ResponseService{

    private final static RestTemplate restTemplate = new RestTemplate();

    @Override
    public ResponseEntity<List<User>> getAllUsers(String url) {
        ResponseEntity<List<User>> allUserResponse = restTemplate
                .exchange(url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<User>>() {
                        });

        if (allUserResponse.getStatusCode().is2xxSuccessful()){
            return allUserResponse;
        }
        else {
            log.info(allUserResponse.getStatusCode().toString());
            return new ResponseEntity<>(allUserResponse.getStatusCode());
        }
    }

    @Override
    public ResponseEntity<String> saveUser(String url, User user, String sessionId) {
        return this.getUserResponse(url, user, sessionId, HttpMethod.POST);
    }

    @Override
    public ResponseEntity<String> updateUser(String url, User user, String sessionId) {
        return this.getUserResponse(url, user, sessionId, HttpMethod.PUT);
    }

    @Override
    public ResponseEntity<String> deleteUser(String url, User user, String sessionId) {
        return this.getUserResponse(url, user, sessionId, HttpMethod.DELETE);
    }

    @Override
    public ResponseEntity<String> getUserResponse(String url, User user, String sessionId, HttpMethod httpMethod) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", sessionId);

        HttpEntity<User> request = new HttpEntity<>(user, headers);

        ResponseEntity<String> response = restTemplate
                .exchange(url,
                        httpMethod,
                        request,
                        String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        } else {
            log.info(response.getStatusCode().toString());
            return new ResponseEntity<>(response.getStatusCode());
        }
    }
}
