package in.projectjwt.main.services;


import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.projectjwt.main.entities.Portfolio;
import in.projectjwt.main.entities.Stock;
import in.projectjwt.main.repositories.PortfolioRepository;
import in.projectjwt.main.repositories.StockRepository;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private StockRepository stockRepository;

    public List<Portfolio> getPortfoliosByUser(Long userId) {
        return portfolioRepository.findByUserId(userId);
    }

    // Add a new portfolio
    public Portfolio addPortfolio(Portfolio portfolio) {
        // Perform any required validations here
        if (portfolio.getName() == null || portfolio.getName().isEmpty()) {
            throw new IllegalArgumentException("Portfolio name cannot be empty");
        }
        return portfolioRepository.save(portfolio);
    }

    public List<Stock> getStocksByPortfolio(Long portfolioId) {
        return stockRepository.findByPortfolioId(portfolioId);
    }

    public Stock addStockToPortfolio(Long portfolioId, Stock stock) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
        stock.setPortfolio(portfolio);
        return stockRepository.save(stock);
    }
    public Map<String, Double> calculatePortfolioSummary(Long portfolioId) {
        List<Stock> stocks = stockRepository.findByPortfolioId(portfolioId);
        double totalValue = 0;
        double totalProfitLoss = 0;

        for (Stock stock : stocks) {
            totalValue += stock.getPrice() * stock.getHoldings();
            totalProfitLoss += (stock.getPrice() - stock.getAvgBuyPrice()) * stock.getHoldings();
        }

        Map<String, Double> summary = new HashMap<>();
        summary.put("totalValue", totalValue);
        summary.put("profitLoss", totalProfitLoss);
        return summary;
    }
}

