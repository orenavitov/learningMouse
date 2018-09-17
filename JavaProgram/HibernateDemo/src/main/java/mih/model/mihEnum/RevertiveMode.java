package mih.model.mihEnum;

public enum RevertiveMode {

    no_revertive(1, "no_revertive"),
    revertive(2, "revertive");

    private int value;
    private String schemaName;

    RevertiveMode(int value, String schemaName) {
        this.value = value;
        this.schemaName = schemaName;
    }

    public static RevertiveMode of(int value) {
        switch (value) {
            case 1:
                return RevertiveMode.no_revertive;
            case 2:
                return RevertiveMode.revertive;
        }
        return null;
    }

    public static RevertiveMode of(String schemaName) {
        switch (schemaName) {
            case "no_revertive":
                return RevertiveMode.no_revertive;

            case "revertive":
                return RevertiveMode.revertive;
        }
        return null;
    }

    @Override
    public String toString(){
        return schemaName;
    }
}
