package org.project.loja_virtual.enums;

public enum AccountReceivableType {

    CHARGE("Pagar"),
    OVERDUE("Vencido"),
    OPENED("Aberto"),
    PAID("Pago");

    private String description;

    AccountReceivableType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "AccountReceivableType{" +
                "description='" + description + '\'' +
                '}';
    }
}
