package mih.model.mihEnum;

public enum TunnelType {
    sr_te(1, "sr_te"),
    mpls_tp(2, "mpls_tp");

    private int value;
    private String schemaName;

    TunnelType(int value, String schemaName) {
        this.value = value;
        this.schemaName = schemaName;
    }

    public static TunnelType of(int value) {
        switch (value) {
            case 1:
                return TunnelType.sr_te;
            case 2:
                return TunnelType.mpls_tp;
        }
        return null;
    }

    public static TunnelType of(String schemaName) {
        switch (schemaName) {
            case "admin_up":
                return TunnelType.sr_te;

            case "admin_down":
                return TunnelType.mpls_tp;
        }
        return null;
    }

    @Override
    public String toString(){
        return schemaName;
    }
}
