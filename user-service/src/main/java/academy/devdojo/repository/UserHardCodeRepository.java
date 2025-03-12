package academy.devdojo.repository;

import academy.devdojo.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserHardCodeRepository {

    private final UserData userData;

    public List<User> findAll() {
        return userData.getUsers();
    }

    public Optional<User> findById(Long id) {
        return userData.getUsers().stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public List<User> findByName(String firstName) {
        return userData.getUsers().stream().filter(user -> user.getFirstName().equalsIgnoreCase(firstName)).toList();
    }
}
