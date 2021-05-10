package com.epam.esm.audit;

public enum OperationType {
        PERSIST("PERSIST"),
        UPDATE("UPDATE"),
        REMOVE("REMOVE");

        private final String type;

        OperationType(String type) {
                this.type = type;
        }

        public String getType() {
                return type;
        }
}
