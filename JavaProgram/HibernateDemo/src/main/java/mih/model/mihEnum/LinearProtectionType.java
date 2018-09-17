package mih.model.mihEnum;

public enum LinearProtectionType {

    unprotected(1, "unprotected"),
    path_protection_1_to_1(2, "path_protection_1_to_1"),
    path_protection_1_plus_1(3, "path_protection_1_plus_1");

    private int value;
    private String schemaName;

    LinearProtectionType(int value, String schemaName) {
        this.value = value;
        this.schemaName = schemaName;
    }

    public static LinearProtectionType of(int value) {
        switch (value) {
            case 1:
                return LinearProtectionType.unprotected;
            case 2:
                return LinearProtectionType.path_protection_1_to_1;
            case 3:
                return LinearProtectionType.path_protection_1_plus_1;
        }
        return null;
    }

    public static LinearProtectionType of(String schemaName) {
        switch (schemaName) {
            case "unprotected":
                return LinearProtectionType.unprotected;

            case "path_protection_1_to_1":
                return LinearProtectionType.path_protection_1_to_1;
            case "path_protection_1_plus_1":
                return LinearProtectionType.path_protection_1_plus_1;
        }
        return null;
    }

    @Override
    public String toString(){
        return schemaName;
    }

}
