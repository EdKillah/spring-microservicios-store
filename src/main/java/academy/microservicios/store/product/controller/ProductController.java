package academy.microservicios.store.product.controller;


import academy.microservicios.store.product.entity.Product;
import academy.microservicios.store.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(){
        List<Product> products = productService.listAllProduct();
        if(products.isEmpty()) {
            //Se retorna una respuesta 203 que realizó de manera correcta la consulta pero sin contenido
            return ResponseEntity.noContent().build();
        }
        //Se retorna el status ok junto con el contenido que es la lista de productos
        return ResponseEntity.ok(products);
    }
}
