package App.Web.Service;

import App.Web.Model.DTO.Shop;
import App.Web.Repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceIm implements ShopService {
    private final ShopRepository shopRepository;

    @Autowired
    public ShopServiceIm(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public Shop getShopByID(int id) {
        return shopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shop not found by id: " + id));
    }

    @Override
    public Shop authenticate(String email, String password) {
        Shop shop = shopRepository.findByEmail(email);
        if (shop != null && Crypto.compare(shop.getPassword(), password)) {
            return shop;
        }
        return null;
    }
}