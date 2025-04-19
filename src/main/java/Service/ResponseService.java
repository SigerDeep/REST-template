package Service;

import model.User;
import org.springframework.http.*;

import java.util.List;

public interface ResponseService {
    ResponseEntity<List<User>> getAllUsers(String url);
    ResponseEntity<String> saveUser(String url, User user, String sessionId);
    ResponseEntity<String> updateUser(String url, User user, String sessionId);
    ResponseEntity<String> deleteUser(String url, User user, String sessionId);
    ResponseEntity<String> getUserResponse(String url, User user, String sessionId, HttpMethod httpMethod);
}
