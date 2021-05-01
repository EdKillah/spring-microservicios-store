package academy.microservicios.store.product.controller;


import academy.microservicios.store.product.entity.Category;
import academy.microservicios.store.product.entity.Product;
import academy.microservicios.store.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public ResponseEntity<List<Product>> getProducts(){
        List<Product> products = productService.listAllProduct();
        if(products.isEmpty()) {
            //Se retorna una respuesta 203 que realizó de manera correcta la consulta pero sin contenido
            return ResponseEntity.noContent().build();
        }
        //Se retorna el status ok junto con el contenido que es la lista de productos
        return ResponseEntity.ok(products);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Product>> getProductsByCategory(@RequestParam(name="categoryId", required = false)  Long categoryId){
        List<Product> products = new ArrayList<>();
        if(categoryId == null){
            products = productService.listAllProduct();
            if(products.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        } else {
            products = productService.findByCategory(Category.builder().id(categoryId).build());
            if(products.isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.ok(products);

    }



    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id){
        Product product = productService.getProduct(id);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }


    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product productCreate = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreate);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody  Product product){
        product.setId(id);
        System.out.println("\nPRODUCT FROM CLIENT: "+ product +"\n");
        Product productDB = productService.getProduct(id);
        System.out.println("PRODUCTDB from database: "+productDB);
        if(productDB == null){
            return ResponseEntity.notFound().build();
        }
        System.out.println("\n PRODUCTO ACTUALIZADO: "+productService.updateProduct(product));
        return ResponseEntity.ok(productService.updateProduct(product));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable ("id") Long id){
        Product productDelete = productService.deleteProduct(id);
        if(productDelete == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDelete);
    }


    @GetMapping(value = "/{id}/stock")
    public ResponseEntity<Product> updateStockProduct(@PathVariable ("id") Long id,@RequestParam(name="quantity", required = true) Double quantity){
        Product product = productService.updateStock(id, quantity);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);

    }


}
