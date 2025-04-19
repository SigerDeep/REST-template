import Service.ResponseService;
import Service.ResponseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
public class Consumer {

    private final static String url = "http://94.198.50.185:7081/api/users";
    private final static ResponseService responseService = new ResponseServiceImpl();

    public static void main(String[] args) {
        StringBuilder code = new StringBuilder();

        ResponseEntity<List<User>> allUserResponse = getAllUsers();
        for (User user : allUserResponse.getBody()) {
            System.out.println(user.toString());
        }

        String sessionId = allUserResponse.getHeaders().getFirst("set-cookie");

        User newUser = new User(3L, "James", "Brown", (byte) 25);
        ResponseEntity<String> saveUserResponse = saveUser(newUser, sessionId);
        code.append(saveUserResponse.getBody());

        User updateUser = new User(3L, "Thomas", "Shelby", (byte) 25);
        ResponseEntity<String> updateUserResponse = updateUser(updateUser, sessionId);
        code.append(updateUserResponse.getBody());

        ResponseEntity<String> deleteUserResponse = deleteUser(updateUser, sessionId);
        code.append(deleteUserResponse.getBody());

        log.info("Итоговый код: " + code);
    }

    public static ResponseEntity<List<User>> getAllUsers() {
        return responseService.getAllUsers(url);
    }

    public static ResponseEntity<String> saveUser(User user, String sessionId) {
        return responseService.saveUser(url, user, sessionId);
    }

    public static ResponseEntity<String> updateUser(User user, String sessionId) {
        return responseService.updateUser(url, user, sessionId);
    }

    public static ResponseEntity<String> deleteUser(User user, String sessionId) {
        String urlForDelete = url + "/" + user.getId();
        return responseService.deleteUser(urlForDelete, user, sessionId);
    }


}
