package com.green.webstoremodels.dto;

import java.time.LocalDateTime;

public class OrderTrackingDto {
    private Integer id;
    private Integer orderId;
    private String status;
    private String description;
    private String location;
    private LocalDateTime eventTime;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public LocalDateTime getEventTime() { return eventTime; }
    public void setEventTime(LocalDateTime eventTime) { this.eventTime = eventTime; }
}
