package App.Web.Global;

import App.Web.Model.DTO.Product;
import App.Web.Model.DTO.Shop;
import App.Web.Model.DTO.User;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {
    public static List<Product> cart;
    public static List<User> users;
    public static List<Shop> shops;
    static {
        cart = new ArrayList<>();
    }
    static {
        users = new ArrayList<>();
    }
    static {
        shops = new ArrayList<>();
    }
}
