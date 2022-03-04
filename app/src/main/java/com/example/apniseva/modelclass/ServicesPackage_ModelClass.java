package com.example.apniseva.modelclass;

public class ServicesPackage_ModelClass {

    String servicesId,servicescategoryId,servicesName,servicesPrice,servicesDescription;
    private boolean isSelected = false;

    public ServicesPackage_ModelClass(String servicesId, String servicescategoryId, String servicesName,
                                      String servicesPrice, String servicesDescription) {
        this.servicesId = servicesId;
        this.servicescategoryId = servicescategoryId;
        this.servicesName = servicesName;
        this.servicesPrice = servicesPrice;
        this.servicesDescription = servicesDescription;
    }

    public String getServicesId() {
        return servicesId;
    }

    public void setServicesId(String servicesId) {
        this.servicesId = servicesId;
    }

    public String getServicescategoryId() {
        return servicescategoryId;
    }

    public void setServicescategoryId(String servicescategoryId) {
        this.servicescategoryId = servicescategoryId;
    }

    public String getServicesName() {
        return servicesName;
    }

    public void setServicesName(String servicesName) {
        this.servicesName = servicesName;
    }

    public String getServicesPrice() {
        return servicesPrice;
    }

    public void setServicesPrice(String servicesPrice) {
        this.servicesPrice = servicesPrice;
    }

    public String getServicesDescription() {
        return servicesDescription;
    }

    public void setServicesDescription(String servicesDescription) {
        this.servicesDescription = servicesDescription;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
