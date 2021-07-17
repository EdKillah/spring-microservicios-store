package academy.microservicios.customer.repository;

import academy.microservicios.customer.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
}
