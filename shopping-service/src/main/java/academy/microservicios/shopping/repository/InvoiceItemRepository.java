package academy.microservicios.shopping.repository;

import academy.microservicios.shopping.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem , Long> {
}
