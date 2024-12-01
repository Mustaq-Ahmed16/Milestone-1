package in.projectjwt.main.repositories;
import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.projectjwt.main.entities.Stock;


@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findByPortfolioId(Long portfolioId);
}
