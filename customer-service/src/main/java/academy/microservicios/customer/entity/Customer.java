package academy.microservicios.customer.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Data
@Table(name="tbl_customers")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El número de documento no puede ser vacío.")
    @Size(min=8, max=8, message="El tamaño del documento debe ser 8")
    @Column(name="number_id", unique = true, length = 8, nullable = false)
    private String numberID;
    @NotEmpty(message="El nombre no puede ser vacio")
    @Column(name="first_name", nullable = false)
    private String firstName;
    @NotEmpty(message = "El apellido no puede ser vacio")
    @Column(name="last_name", nullable = false)
    private String lastName;
    @NotEmpty(message ="El email no puede estar vacio")
    @Email(message = "No es una dirección de correo bien formada")
    @Column(unique = true, nullable = false)
    private String email;
    @Column(name="photo_url")
    private String photoUrl;
    @NotNull(message = "La región no puede ser vacia")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="region_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Region region;

    private String state;
}


















