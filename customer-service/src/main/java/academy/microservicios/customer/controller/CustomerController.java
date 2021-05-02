package academy.microservicios.customer.controller;


import academy.microservicios.customer.entity.Customer;
import academy.microservicios.customer.entity.Region;
import academy.microservicios.customer.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RequestMapping("/customers")
@RestController
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    //---------------------------------------Retrieve All Customers --------------------------------------

    @GetMapping
    public ResponseEntity<List<Customer>> listAllCustomers(@RequestParam(name= "regionId", required = false) Long regionId){
        List<Customer> customers = new ArrayList<>();
        if(regionId == null){
            customers = customerService.findCustomerAll();
            if(customers.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }else{
            Region region = new Region();
            region.setId(regionId);
            customers = customerService.findCustomerByRegion(region);
            if(customers == null){
                log.error("Customers with Region id {} not found.", regionId);
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(customers);
    }


    //---------------------------------- Retrieve Single Customer -------------------------------

    @GetMapping(value = "/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id){
        log.info("Fetching Customer with id {} ",id);
        Customer customer = customerService.getCustomer(id);
        if(customer == null){
            log.error("Customer with id {} not found. ",id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    //--------------------------------- Create a customer -------------------------------------------

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer, BindingResult result){
        log.info("Creating customer: {}",customer);
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Customer customerDB = customerService.createCustomer(customer);

        return ResponseEntity.status(HttpStatus.CREATED).body(customerDB);
    }

    //----------------------------------- Update Customer ------------------------------------

    @PutMapping(value="/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer){
        log.info("Updating customer with id {}.", id);
        Customer customerDB = customerService.getCustomer(id);
        if(customerDB == null){
            log.error("Unable to update. Customer with id {} not found.",id);
            return ResponseEntity.notFound().build();
        }
        //No se bien por qué se le asigna el id acá.
        customer.setId(id);
        customerDB = customerService.updateCustomer(customer);
        log.info("Customer with id {} was updated successfully.");
        return ResponseEntity.ok(customerDB);

    }


    //------------------------------------Delete Customer ------------------------------------------

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") Long id){
        log.info("Deleting Customer with id {}. ",id);
        Customer customerDB = customerService.getCustomer(id);
        if(customerDB == null){
            log.error("Unable to delete. Customer with id {} not found.",id);
            return ResponseEntity.notFound().build();
        }
        customerDB = customerService.deleteCustomer(customerDB);
        return ResponseEntity.ok(customerDB);
    }


    private String formatMessage(BindingResult result){
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                  Map<String,String> error = new HashMap<>();
                  error.put(err.getField(), err.getDefaultMessage());
                  return error;
                }).collect(Collectors.toList());

        //Practicar esto de builder de loombok porque no lo domino aún
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();

        //Que es esto del object mapper?
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
          jsonString = mapper.writeValueAsString(errorMessage);
        } catch(JsonProcessingException e){
            e.printStackTrace();
        }
        return jsonString;
    }



}














