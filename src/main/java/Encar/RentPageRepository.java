package Encar;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RentPageRepository extends CrudRepository<RentPage, Long> {
    List<RentPage> findByRentId(Long rentId);
}