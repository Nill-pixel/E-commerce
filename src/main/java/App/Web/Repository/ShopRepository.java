package App.Web.Repository;

import App.Web.Model.DTO.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
    Shop findByEmail(String email);
    int countBy();
}
