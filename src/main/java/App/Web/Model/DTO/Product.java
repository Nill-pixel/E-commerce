package App.Web.Model.DTO;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "ID_shop")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "ID_Category")
    private Category category;

    @Column(name = "name")
    private String name;
    @Column(length = 64)
    private String photos;
    @Column(name = "Quantity")
    private int amount;
    @Column(name = "Price")
    private double price;
    @Column(name = "Description")
    private String description;
    public Product() {
    }
}
