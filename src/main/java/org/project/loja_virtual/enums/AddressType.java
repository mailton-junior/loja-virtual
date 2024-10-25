package org.project.loja_virtual.enums;

public enum AddressType {

    COLLECTION("Cobrança"),
    DELIVERY("Entrega");

    private String description;

    private AddressType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
