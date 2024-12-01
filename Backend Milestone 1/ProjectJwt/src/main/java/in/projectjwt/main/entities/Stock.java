package in.projectjwt.main.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String symbol;
    private String name;
    private Double price;
    private Double changePercentage;
    private Double holdings;
    private Double avgBuyPrice;
    private String imageUrl; // New field
    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getChangePercentage() {
		return changePercentage;
	}

	public void setChangePercentage(Double changePercentage) {
		this.changePercentage = changePercentage;
	}

	public Double getHoldings() {
		return holdings;
	}

	public void setHoldings(Double holdings) {
		this.holdings = holdings;
	}

	public Double getAvgBuyPrice() {
		return avgBuyPrice;
	}

	public void setAvgBuyPrice(Double avgBuyPrice) {
		this.avgBuyPrice = avgBuyPrice;
	}

	public Portfolio getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

	
    // Getters and Setters
}