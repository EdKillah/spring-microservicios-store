package academy.microservicios.store.product.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tbl_categories")
//@Setter @Getter Esto me genera los getter y setter usando Lombok deben ponerse ambas anotaciones
@Data
public class Category {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;


}
