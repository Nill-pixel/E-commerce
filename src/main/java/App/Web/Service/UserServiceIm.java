package App.Web.Service;

import App.Web.Model.DTO.User;
import App.Web.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceIm implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceIm(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && Crypto.compare(user.getPassword(), password)) {
            return user;
        }
        return null;
    }
}