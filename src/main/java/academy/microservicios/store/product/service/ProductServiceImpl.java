package academy.microservicios.store.product.service;

import academy.microservicios.store.product.entity.Category;
import academy.microservicios.store.product.entity.Product;
import academy.microservicios.store.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    //En vez de inyectarse con autowired se inyecta por constructor, por eso se usa RequiredArgsConstructor
    //Se debe declarar final para que el valor sea asignado en el constructor creado de manera interna
    //Es pasado por parametro el productRepository
    private final ProductRepository productRepository;

    @Override
    public List<Product> listAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        //Me permite ejecutar un condicional por si no encuentra el producto.
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        product.setStatus("CREATED");
        product.setCreateAt(new Date());

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product productdb = getProduct(product.getId());
        if(productdb==null)
            return null;
        productdb.setName(product.getName());
        productdb.setDescription(product.getDescription());
        productdb.setPrice(product.getPrice());
        productdb.setCategory(product.getCategory());

        return productRepository.save(productdb);
    }

    @Override
    public Product deleteProduct(Long id) {
        Product productdb = getProduct(id);
        if(productdb==null)
            return null;
        //Los datos no deben ser eliminados de la base de datos sino a través de estados
        productdb.setStatus("DELETED");
        return productRepository.save(productdb);
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product updateStock(Long id, Double quantity) {
        Product productdb = getProduct(id);
        if(productdb==null)
            return null;
        Double stock = productdb.getStock() + quantity;
        productdb.setStock(stock);
        return productRepository.save(productdb);
    }
}
