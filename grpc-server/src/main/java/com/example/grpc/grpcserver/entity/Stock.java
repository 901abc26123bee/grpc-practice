package com.example.grpc.grpcserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "stock")
public class Stock {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private int id;

  @Column(name = "product_name")
  private String productName;

  @Column(name = "price")
  private double price;

  @Column(name = "offer_number")
  private int offerNumber;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getOfferNumber() {
    return offerNumber;
  }

  public void setOfferNumber(int offerNumber) {
    this.offerNumber = offerNumber;
  }
}
