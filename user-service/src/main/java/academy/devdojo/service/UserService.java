package academy.devdojo.service;

import academy.devdojo.domain.User;
import academy.devdojo.repository.UserHardCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserHardCodeRepository repository;

    public List<User> findAll(String firstName) {
        return firstName == null ? repository.findAll() : repository.findByName(firstName);
    }

}
