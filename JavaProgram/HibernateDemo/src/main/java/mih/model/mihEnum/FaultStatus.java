package mih.model.mihEnum;

public enum FaultStatus {
    no_effect(1, "no_effect"),
    connection_lost(2, "connection_lost");

    private int value;
    private String schemaName;

    FaultStatus(int value, String schemaName) {
        this.value = value;
        this.schemaName = schemaName;
    }

    public static FaultStatus of(int value) {
        switch (value) {
            case 1:
                return FaultStatus.no_effect;
            case 2:
                return FaultStatus.connection_lost;
        }
        return null;
    }

    public static FaultStatus of(String schemaName) {
        switch (schemaName) {
            case "admin_up":
                return FaultStatus.no_effect;

            case "admin_down":
                return FaultStatus.connection_lost;
        }
        return null;
    }

    @Override
    public String toString(){
        return schemaName;
    }
}
