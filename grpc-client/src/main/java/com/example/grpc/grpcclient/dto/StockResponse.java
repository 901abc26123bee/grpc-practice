package com.example.grpc.grpcclient.dto;

// to avoid --> com.fasterxml.jackson.databind.exc.InvalidDefinitionException: No serializer found for class com.google.protobuf.UnknownFieldSet$Parser and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) (through reference chain: com.example.grpc.StockResponse["unknownFields"]->com.google.protobuf
public class StockResponse {
  private int id;
  private String productName;
  private double price;
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
