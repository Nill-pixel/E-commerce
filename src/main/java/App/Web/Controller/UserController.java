package App.Web.Controller;

import App.Web.Global.GlobalData;
import App.Web.Model.DTO.Address;
import App.Web.Model.DTO.User;
import App.Web.Repository.AddressRepository;
import App.Web.Repository.UserRepository;
import App.Web.Service.Crypto;
import App.Web.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserController(AddressRepository addressRepository, UserRepository userRepository, UserService userService) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login/login";
    }

    @PostMapping("/processLogin")
    public String processLogin(@RequestParam String email, @RequestParam String password,
            RedirectAttributes redirectAttributes) {
        User user = userService.authenticate(email, password);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", true);
            return "redirect:/login";
        }

        GlobalData.users.add(user);

        if (user.getRole().equals("admin")) {
            return "redirect:/admin";
        }

        return "redirect:/";
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        User user = new User();
        model.addAttribute("address", addressRepository.findAll());
        model.addAttribute("user", user);
        return "login/register";
    }

    @PostMapping("/register")
    public String registerUser(User user, RedirectAttributes redirectAttributes) {
        user.setPassword(Crypto.apply(user.getPassword()));
        Address address = addressRepository.findById(user.getAddress().getId()).orElseThrow();
        user.setRole("user");
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/login";
    }
}
