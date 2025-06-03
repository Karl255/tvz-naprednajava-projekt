package hr.tvz.napredna.java.dijezetserver.repository;

import hr.tvz.napredna.java.dijezetserver.model.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PinRepository extends JpaRepository<Pin, Long> {
}
