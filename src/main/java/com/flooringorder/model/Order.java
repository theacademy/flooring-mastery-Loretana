package com.flooringorder.model;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;


public class Order {

    private int orderId;
    private String customerName;
    private String state;
    private String productType;
    private BigDecimal taxRate;
    private BigDecimal area;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;
    private LocalDate date;

    public Order(LocalDate date, int orderId) {
        this.date = date;
        this.orderId = orderId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getState() {
        return state;
    }

    public String getProductType() {
        return productType;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public BigDecimal getArea() {
        return area;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", state='" + state + '\'' +
                ", productType='" + productType + '\'' +
                ", taxRate=" + taxRate +
                ", area=" + area +
                ", costPerSquareFoot=" + costPerSquareFoot +
                ", laborCostPerSquareFoot=" + laborCostPerSquareFoot +
                ", customerName='" + customerName + '\'' +
                ", materialCost=" + materialCost +
                ", laborCost=" + laborCost +
                ", tax=" + tax +
                ", total=" + total +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId && Objects.equals(customerName, order.customerName) && Objects.equals(state, order.state) && Objects.equals(productType, order.productType) && Objects.equals(taxRate, order.taxRate) && Objects.equals(area, order.area) && Objects.equals(costPerSquareFoot, order.costPerSquareFoot) && Objects.equals(laborCostPerSquareFoot, order.laborCostPerSquareFoot) && Objects.equals(materialCost, order.materialCost) && Objects.equals(laborCost, order.laborCost) && Objects.equals(tax, order.tax) && Objects.equals(total, order.total) && Objects.equals(date, order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, customerName, state, productType, taxRate, area, costPerSquareFoot, laborCostPerSquareFoot, materialCost, laborCost, tax, total, date);
    }
}
