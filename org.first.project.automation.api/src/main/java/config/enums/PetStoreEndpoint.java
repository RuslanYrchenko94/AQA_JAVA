package config.enums;

public enum PetStoreEndpoint {
    // Define enum values
    STORE_INVENTORY("/store/inventory"),
    STORE_ORDER("/store/order/"),
    PET("/pet"),
    PET_FIND_BY_STATUS("/pet/findByStatus"),
    USER("/user/");
    // Declare variable
    private String endpoint;
    // Declare constructor
    PetStoreEndpoint(String endpoint){this.endpoint = endpoint;}
    // Declare getter
    public String getEndpoint(){return this.endpoint;}
    // method for converting the Enum to String
    @Override
    public String toString() {return endpoint;}
}
