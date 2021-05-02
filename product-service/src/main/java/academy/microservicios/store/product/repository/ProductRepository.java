package academy.microservicios.store.product.repository;

import academy.microservicios.store.product.entity.Category;
import academy.microservicios.store.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//JPARepository tiene unas implementaciones por defecto que pueden ser usadas sin construir las sentencias sql
public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findByCategory(Category category);
}
