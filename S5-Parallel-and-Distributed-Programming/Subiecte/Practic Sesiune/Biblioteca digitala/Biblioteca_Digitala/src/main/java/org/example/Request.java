package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Request { // Cerere
    private final int id;
    private final int resourceType;
    private int status;
    private String timestamp;

    public Request(int id, int resourceType) {
        this.id = id;
        this.resourceType = resourceType;
        this.status = -1;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.timestamp = LocalDateTime.now().format(formatter);
    }

    public int getId() {
        return id;
    }

    public int getResourceType() {
        return resourceType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

//    @Override
//    public String toString() {
//        return "Request{" +
//                "id='" + id + '\'' +
//                ", resourceType=" + resourceType +
//                ", status=" + status +
//                ", timestamp='" + timestamp + '\'' +
//                '}';
//    }
}
