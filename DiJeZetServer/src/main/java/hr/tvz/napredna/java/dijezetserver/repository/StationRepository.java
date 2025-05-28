package hr.tvz.napredna.java.dijezetserver.repository;

import hr.tvz.napredna.java.dijezetserver.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface StationRepository extends JpaRepository<Station, BigInteger> {
}
