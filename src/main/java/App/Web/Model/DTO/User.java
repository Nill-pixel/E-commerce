package App.Web.Model.DTO;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "client")
public class User {

    @ManyToOne
    @JoinColumn(name = "ID_Address")
    private Address address;
    @Column(name = "number")
    private int number;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "Nome")
    private String username;

    @Column(name = "Email")
    private String email;
    @Column(name = "Password")
    private String password;

    @Column(name = "Role")
    private String role;
}
