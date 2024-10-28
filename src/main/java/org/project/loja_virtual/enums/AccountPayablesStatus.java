package org.project.loja_virtual.enums;

public enum AccountPayablesStatus {

    CHARGE("Pagar"),
    OVERDUE("Vencido"),
    OPENED("Aberto"),
    PAID("Pago"),
    NEGOTIATED("Negociado");

    private String description;

    AccountPayablesStatus(String description) {
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
