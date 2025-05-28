package hr.tvz.napredna.java.dijezetserver.repository;

import hr.tvz.napredna.java.dijezetserver.model.Line;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface LineRepository extends JpaRepository<Line, BigInteger> {
}
