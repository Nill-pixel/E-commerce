package App.Web.Service;

import App.Web.Model.DTO.User;

public interface UserService {
        User authenticate(String email, String password);
}
