package App.Web.Service;

import App.Web.Model.DTO.Shop;

public interface ShopService {
    Shop getShopByID(int id);

    Shop authenticate(String email, String password);
}
