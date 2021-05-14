package com.epam.esm.event;

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
