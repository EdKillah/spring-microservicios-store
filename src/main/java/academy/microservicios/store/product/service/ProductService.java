package academy.microservicios.store.product.service;

import academy.microservicios.store.product.entity.Category;
import academy.microservicios.store.product.entity.Product;

import java.util.List;

public interface ProductService {

    public List<Product> listAllProduct();

    public Product getProduct(Long id);

    public Product createProduct(Product product);

    public Product updateProduct(Product product);

    public Product deleteProduct(Long id);

    public List<Product> findByCategory(Category category);

    public Product updteStock(Long id, Double quantity);



}
