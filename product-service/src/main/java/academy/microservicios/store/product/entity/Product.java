package academy.microservicios.store.product.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Entity
@Table(name ="tbl_products")
@AllArgsConstructor @NoArgsConstructor @Builder
@Data
public class Product {

    //AllArgsConstructor me crea un constructor con todos los argumentos
    //Builder me permite crear nuevas instancias de la entidad
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "El nombre no debe ser vacio")
    private String name;
    private String description;
    @Positive(message = "El stock debe ser mayor a 0")
    private Double stock;
    private Double price;
    private String status;

    @Column(name="create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    //Lazy se carga unicamente cuando se requieran
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    //JsonIgnoreProperties me permite excluir problemas de fetch lazy, (buscar más qué hace JsonIgnoreProperties)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    @NotNull(message = "La categoria no puede ser vacia")
    private Category category;

}
