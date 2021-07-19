package academy.microservicios.shopping.service.impl;

import academy.microservicios.shopping.client.CustomerClient;
import academy.microservicios.shopping.client.ProductClient;
import academy.microservicios.shopping.entity.Invoice;
import academy.microservicios.shopping.entity.InvoiceItem;
import academy.microservicios.shopping.model.Customer;
import academy.microservicios.shopping.model.Product;
import academy.microservicios.shopping.repository.InvoiceItemRepository;
import academy.microservicios.shopping.repository.InvoiceRepository;
import academy.microservicios.shopping.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    public InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceItemRepository invoiceItemRepository;

    @Autowired
    public CustomerClient customerClient;

    @Autowired
    public ProductClient productClient;

    @Override
    public List<Invoice> findInvoiceAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice createInvoice(Invoice invoice) {
        System.out.println("\n Entrando en createInvoice Service \n");
        Invoice invoiceDB = invoiceRepository.findByNumberInvoice(invoice.getNumberInvoice());
        if(invoiceDB == null){
            invoice.setState("CREATED");
            invoiceDB = invoiceRepository.save(invoice);
            invoiceDB.getItems().forEach(invoiceItem -> {
                System.out.println("INVOICE ITEM: "+invoiceItem+ " | quantity: "+invoiceItem.getQuantity());
                productClient.updateStockProduct(invoiceItem.getProductId(), invoiceItem.getQuantity() * -1);
            });
        }
        return invoiceDB;
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) {
        Invoice invoiceDB = getInvoice(invoice.getId());
        if(invoiceDB == null){
            return null;
        }
        invoiceDB.setCustomerId(invoice.getCustomerId());
        invoiceDB.setDescription(invoice.getDescription());
        invoiceDB.setNumberInvoice(invoice.getNumberInvoice());
        invoiceDB.getItems().clear();
        invoiceDB.setItems(invoice.getItems());
        return invoiceRepository.save(invoiceDB);
    }

    @Override
    public Invoice deleteInvoice(Invoice invoice) {
        Invoice invoiceDB = getInvoice(invoice.getId());
        if(invoiceDB == null){
            return null;
        }
        invoiceDB.setState("DELETED");
        return invoiceRepository.save(invoiceDB);
    }

    @Override
    public Invoice getInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElse(null);
        if(invoice!=null){
            Customer customer = customerClient.getCustomer(invoice.getCustomerId()).getBody();
            invoice.setCustomer(customer);
            List<InvoiceItem> listItems = invoice.getItems().stream().map(invoiceItem ->{
                Product product = productClient.getProduct(invoiceItem.getProductId()).getBody();
                invoiceItem.setProduct(product);
                return invoiceItem;
            }).collect(Collectors.toList());
            invoice.setItems(listItems);
        }
        return invoice;
    }
}
