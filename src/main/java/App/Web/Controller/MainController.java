package App.Web.Controller;

import App.Web.Enums.Categories;
import App.Web.Global.GlobalData;
import App.Web.Model.DTO.Product;
import App.Web.Repository.ProductRepository;
import App.Web.Repository.ShopRepository;
import App.Web.Service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {
  private final ProductRepository productRepository;
  private final ProductService productService;
  private final ShopRepository shopRepository;

  public MainController(ProductRepository productRepository, ProductService productService,
      ShopRepository shopRepository) {
    this.productRepository = productRepository;
    this.productService = productService;
    this.shopRepository = shopRepository;
  }

  @GetMapping("/")
  public String index(Model model) {
    var products = productRepository.findAll();

    var burger = products.stream().filter(e -> e.getCategory().getId() == (Categories.Burger.getValue())).limit(4);
    var pizza = products.stream().filter(e -> e.getCategory().getId() == (Categories.Pizza.getValue())).limit(4);
    var pasta = products.stream().filter(e -> e.getCategory().getId() == (Categories.Pasta.getValue())).limit(4);
    var fries = products.stream().filter(e -> e.getCategory().getId() == (Categories.Fries.getValue())).limit(4);

    model.addAttribute("burger", burger);
    model.addAttribute("pizza", pizza);
    model.addAttribute("pasta", pasta);
    model.addAttribute("fries", fries);

    return "index";
  }

  @GetMapping("menu")
  public String menu(Model model) {
    var products = productRepository.findAll();

    var burger = products.stream().filter(e -> e.getCategory().getId() == (Categories.Burger.getValue()));
    var pizza = products.stream().filter(e -> e.getCategory().getId() == (Categories.Pizza.getValue()));
    var pasta = products.stream().filter(e -> e.getCategory().getId() == (Categories.Pasta.getValue()));
    var fries = products.stream().filter(e -> e.getCategory().getId() == (Categories.Fries.getValue()));

    model.addAttribute("burger", burger);
    model.addAttribute("pizza", pizza);
    model.addAttribute("pasta", pasta);
    model.addAttribute("fries", fries);
    model.addAttribute("all", products);

    model.addAttribute("cartCount", GlobalData.cart.size());
    model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
    model.addAttribute("cart", GlobalData.cart);

    return "menu";
  }

  @GetMapping("shops")
  public String shop(Model model) {
    var shops = shopRepository.findAll();
    model.addAttribute("shop", shops);
    return "shop";
  }

  @GetMapping("addToCart/{id}")
  public String addToCart(@PathVariable(value = "id") int id, RedirectAttributes redirectAttributes) {
    if (GlobalData.users.isEmpty()){
      return "redirect:/login";
    }
    var success = true;
    var productToCart = productService.getProductById(id);
    GlobalData.cart.add(productToCart);

    redirectAttributes.addFlashAttribute("success", success);
    return "redirect:/menu";
  }

  @GetMapping("clearCart")
  public String clearCart( RedirectAttributes redirectAttributes){
    GlobalData.cart.clear();
    redirectAttributes.addFlashAttribute("addCart", true);
    return "redirect:/menu";
  }

}
