package App.Web.Model.DTO;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "shop")
public class Shop {
    @ManyToOne
    @JoinColumn(name = "ID_address")
    private Address address;
    @Column(name = "number")
    private int number;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Nome")
    private String name;
    @Column(name = "Password")
    private String password;
    @Column(name = "Description")
    private String description;

    @Column(name = "Email")
    private String email;
    @Column(length = 64)
    private String photos;
}
