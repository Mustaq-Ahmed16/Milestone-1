import React, { useState, useEffect } from "react";
import "./Market.css";
import Navbar from "../LandingPage/Navbar";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSearch } from "@fortawesome/free-solid-svg-icons";
import { IoMdTrendingUp, IoMdTrendingDown } from 'react-icons/io';

const Market = () => {
  const [cryptoData, setCryptoData] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [filteredData, setFilteredData] = useState([]);

  const fetchData = async () => {
    try {
      const response = await fetch("http://localhost:8005/auth/coins/top50"); // Update to match your backend endpoint
      if (!response.ok) {
        throw new Error("Failed to fetch coins data");
      }
      const data = await response.json();
      setCryptoData(data);
      setFilteredData(data);
    } catch (error) {
      console.error("Error fetching coins data:", error);
    }
  };
  useEffect(() => {
    fetchData();
  }, []);

  // Function to handle search action
  const handleSearch = () => {
    const filtered = cryptoData.filter(
      (crypto) =>
        crypto.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
        crypto.symbol.toLowerCase().includes(searchQuery.toLowerCase())
    );
    setFilteredData(filtered);
  };

  return (
    <div className="container">
      <Navbar />
      <div className="header">
        <h1>Top Cryptocurrencies by Market Cap</h1>
        <div className="search-bar">
          <input
            type="text"
            placeholder="Search by name or symbol..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="search-input"
          />
          <button onClick={handleSearch} className="search-button">
            <FontAwesomeIcon icon={faSearch} />
          </button>
        </div>
      </div>

      <table>
        <thead>
          <tr>
            <th>#</th>
            <th>Name</th>
            <th>Price</th>
            <th>24h</th>
            <th>7d</th>
            <th>Market Cap</th>
            <th>Volume</th>
            <th>Supply</th>
          </tr>
        </thead>
        <tbody>
          {filteredData.length > 0 ? (
            filteredData.map((crypto, index) => (
              <tr key={crypto.id}>
                <td>{index + 1}</td>
                <td>
                  <img
                    src={crypto.image}
                    alt={`${crypto.name} logo`}
                    className="crypto-logo"
                  />
                  {crypto.name}
                </td>
                <td>${crypto.current_price.toLocaleString()}</td>
                <td className={crypto.price_change_percentage_24h > 0 ? 'value-positive' : 'value-negative'}>
                  {crypto.price_change_percentage_24h > 0 ? (
                    <IoMdTrendingUp style={{ color: '#28a745' }} />
                  ) : (
                    <IoMdTrendingDown style={{ color: '#dc3545' }} />
                  )}
                  {crypto.price_change_percentage_24h.toFixed(2)}%
                </td>
                <td className={crypto.ath_change_percentage > 0 ? 'value-positive' : 'value-negative'}>
                  {crypto.ath_change_percentage > 0 ? (
                    <IoMdTrendingUp style={{ color: '#28a745' }} />
                  ) : (
                    <IoMdTrendingDown style={{ color: '#dc3545' }} />
                  )}
                  {crypto.ath_change_percentage.toFixed(2)}%
                </td>
                <td>${crypto.market_cap.toLocaleString()}</td>
                <td>${crypto.total_volume.toLocaleString()}</td>
                <td>${crypto.total_supply?.toLocaleString() || "N/A"}</td>





              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="6" style={{ textAlign: "center" }}>
                No coins found
              </td>
            </tr>
          )}
        </tbody>

      </table>
    </div>
  );
};

export default Market;

