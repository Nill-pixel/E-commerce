package App.Web.Controller;

import App.Web.Global.GlobalData;
import App.Web.Model.DTO.Category;
import App.Web.Model.DTO.Product;
import App.Web.Model.DTO.Shop;
import App.Web.Repository.CategoryRepository;
import App.Web.Repository.ProductRepository;
import App.Web.Service.ProductService;
import App.Web.Service.ShopService;
import App.Web.Upload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Controller
public class ShopController {
    private static final String UPLOAD_DIR = "D:\\E-commerce\\src\\main\\resources\\static\\images\\upload\\product";

    private final ProductRepository productRepository;
    private final ShopService shopService;
    private final CategoryRepository categoryRepository;
    private final ProductService productService;

    @Autowired
    public ShopController(ProductRepository productRepository, ShopService shopService,
            CategoryRepository categoryRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.shopService = shopService;
        this.categoryRepository = categoryRepository;
        this.productService = productService;
    }

    @GetMapping("/shop")
    public String showShopPage(Model model) {
        if (!GlobalData.shops.isEmpty()) {
            var shop = shopService.getShopByID(GlobalData.shops.get(0).getId());
            model.addAttribute("allProduct", productRepository.allProduct(shop.getId()));
            model.addAttribute("shop", shop);
            return "shop/index";
        } else {
            return "shop/shopLogin";
        }

    }

    @GetMapping("/productList")
    public String listProducts(Model model) {
        if (!GlobalData.shops.isEmpty()) {
            var shop = shopService.getShopByID(GlobalData.shops.get(0).getId());

            model.addAttribute("shop", shop);
            model.addAttribute("products", productRepository.findAllProductShop(shop.getId()));
            return "shop/listProduct";
        } else {
            return "shop/shopLogin";
        }
    }

    @GetMapping("/addProductForm")
    public String showAddProductForm(Model model) {
        if (!GlobalData.shops.isEmpty()) {
            Product product = new Product();
            var categories = categoryRepository.findAll();

            model.addAttribute("shop", GlobalData.shops.get(0));
            model.addAttribute("categories", categories);
            model.addAttribute("product", product);
            return "shop/formProduct";
        } else {
            return "shop/shopLogin";
        }
    }

    @GetMapping("/shopLogin")
    public String formLogin() {
        return "shop/shopLogin";
    }

    @PostMapping("/shopLogin")
    public String shopLogin(@RequestParam String email, @RequestParam String password,
            RedirectAttributes redirectAttributes) {
        Shop shop = shopService.authenticate(email, password);
        if (shop == null) {
            redirectAttributes.addFlashAttribute("error", true);
            return "redirect:/shopLogin";
        }
        if (GlobalData.shops.isEmpty()) {
            GlobalData.shops.add(shop);
            return "redirect:/shop";
        } else {
            GlobalData.shops.clear();
        }
        return "redirect:shopLogin";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(Product product, @RequestParam("image") MultipartFile multipartFile,
            RedirectAttributes redirectAttributes) throws IOException {
        Category category = categoryRepository.findById(product.getCategory().getId()).orElseThrow();
        product.setCategory(category);
        product.setShop(GlobalData.shops.get(0));
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            product.setPhotos(fileName);
            productRepository.save(product);

            Path uploadPath = Paths.get(UPLOAD_DIR);
            FileUpload.saveFile(String.valueOf(uploadPath), fileName, multipartFile);
        } else {
            product.setPhotos(null);
            productRepository.save(product);
        }

        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/addProductForm";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable(value = "id") int id, Model model) {
        var product = productService.getProductById(id);
        var categories = categoryRepository.findAll();

        model.addAttribute("shop", GlobalData.shops.get(0));
        model.addAttribute("categories", categories);
        model.addAttribute("product", product);
        model.addAttribute("product", product);
        return "shop/editProduct";
    }

    @GetMapping("logout")
    public String logout() {
        GlobalData.shops.clear();
        return "redirect:loginLoja";
    }
}