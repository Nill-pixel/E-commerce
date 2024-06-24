package App.Web.Controller;

import App.Web.Model.DTO.Address;
import App.Web.Model.DTO.Shop;
import App.Web.Repository.AddressRepository;
import App.Web.Repository.ProductRepository;
import App.Web.Repository.ShopRepository;
import App.Web.Service.Crypto;
import App.Web.Upload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Controller
public class AdminController {

    public static final String UPLOAD_DIR = "D:\\E-commerce\\src\\main\\resources\\static\\images\\upload\\shop";
    public final ShopRepository shopRepository;
    public final AddressRepository addressRepository;
    public final ProductRepository productRepository;

    @Autowired
    public AdminController(ShopRepository shopRepository, AddressRepository addressRepository, ProductRepository productRepository) {
        this.shopRepository = shopRepository;
        this.addressRepository = addressRepository;
        this.productRepository = productRepository;
    }


    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("shopCount", shopRepository.countBy());
        model.addAttribute("productCount", productRepository.countBy());
        return "admin/index";
    }

    @GetMapping("formShop")
    public String formShop(Model model) {
        Shop shop = new Shop();
        model.addAttribute("address", addressRepository.findAll());
        model.addAttribute("shop", shop);
        return "admin/formShop";
    }

    @GetMapping("listShop")
    public String listShop(Model model) {
        model.addAttribute("shops", shopRepository.findAll());
        return "admin/listShop";
    }

    @PostMapping("saveShop")
    public String saveShop(Shop shop, @RequestParam("image") MultipartFile multipartFile,
            RedirectAttributes redirectAttributes) throws IOException {
        boolean success = true;
        Address address = addressRepository.findById(shop.getAddress().getId()).orElseThrow();
        shop.setPassword(Crypto.apply(shop.getPassword()));
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            shop.setAddress(address);
            shop.setPhotos(fileName);
            shopRepository.save(shop);
            redirectAttributes.addFlashAttribute("success", success);

            Path uploadPath = Paths.get(UPLOAD_DIR);
            FileUpload.saveFile(String.valueOf(uploadPath), fileName, multipartFile);
        } else {
            if (shop.getPhotos().isEmpty()) {
                shop.setPhotos(null);
                shop.setAddress(address);
                shopRepository.save(shop);
                redirectAttributes.addFlashAttribute("success", success);
            }
        }
        redirectAttributes.addFlashAttribute("success", success);
        shop.setAddress(address);
        shopRepository.save(shop);
        return "redirect:/formLoja";
    }
}
