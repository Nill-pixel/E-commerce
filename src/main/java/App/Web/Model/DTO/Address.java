package App.Web.Model.DTO;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "endereco")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "neighborhood")
    private String neighborhood;
    @Column(name = "country")
    private String country;
    @Column(name = "province")
    private String province;
}
