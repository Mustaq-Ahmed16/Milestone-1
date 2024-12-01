package in.projectjwt.main.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import in.projectjwt.main.entities.Portfolio;
import in.projectjwt.main.entities.Stock;
import in.projectjwt.main.services.PortfolioService;

@RestController
@RequestMapping("/auth/portfolios")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;
    
    @PostMapping
    public Portfolio createPortfolio(@RequestBody Portfolio portfolio) {
        return portfolioService.addPortfolio(portfolio);
    }
    @GetMapping("/{userId}")
    public List<Portfolio> getUserPortfolios(@PathVariable Long userId) {
        return portfolioService.getPortfoliosByUser(userId);
    }

   
    @GetMapping("/{portfolioId}/stocks")
    public List<Stock> getPortfolioStocks(@PathVariable Long portfolioId) {
        return portfolioService.getStocksByPortfolio(portfolioId);
    }

    @PostMapping("/{portfolioId}/stocks")
    public Stock addStock(@PathVariable Long portfolioId, @RequestBody Stock stock) {
        return portfolioService.addStockToPortfolio(portfolioId, stock);
    }
    @GetMapping("/{portfolioId}/summary")
    public Map<String, Double> getPortfolioSummary(@PathVariable Long portfolioId) {
        return portfolioService.calculatePortfolioSummary(portfolioId);
    }
}

