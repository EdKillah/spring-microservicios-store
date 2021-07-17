package academy.microservicios.customer.service;

import academy.microservicios.customer.entity.Customer;
import academy.microservicios.customer.entity.Region;
import academy.microservicios.customer.repository.CustomerRepository;
import academy.microservicios.customer.repository.RegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Override
    public List<Customer> findCustomerAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findCustomerByRegion(Region region) {
        return customerRepository.findByRegion(region);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Customer customerDB = getCustomer(customer.getId());
        if(customerDB == null){
            if(!regionRepository.findById(customer.getRegion().getId()).isPresent()){
                Region regionDB = regionRepository.save(customer.getRegion());
            }
            customer.setState("CREATED");
            return customerRepository.save(customer);
        }else{
            return customerDB;
        }

    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer customerDB = getCustomer(customer.getId());
        if(customerDB == null){
            return null;
        }
        customerDB.setFirstName(customer.getFirstName());
        customerDB.setLastName(customer.getLastName());
        customerDB.setEmail(customer.getEmail());
        customerDB.setPhotoUrl(customer.getPhotoUrl());

        return customerRepository.save(customerDB);
    }

    @Override
    public Customer deleteCustomer(Customer customer) {
        Customer customerDB = getCustomer(customer.getId());
        if(customerDB == null){
            return null;
        }
        customer.setState("DELETED");
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }
}
