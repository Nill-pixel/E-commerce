package App.Web.Repository;

import App.Web.Model.DTO.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    int countBy();
    @Query("SELECT p FROM Product p INNER JOIN p.shop l INNER JOIN l.address e")
    List<Product> findAllByStoreAddressCity();

    @Query("SELECT COUNT(p) FROM Product p WHERE p.shop.id = :id")
    int allProduct(@Param("id")int id);

    @Query("SELECT p FROM Product p WHERE p.shop.id = :id")
    List<Product>findAllProductShop(@Param("id")int id);
}
