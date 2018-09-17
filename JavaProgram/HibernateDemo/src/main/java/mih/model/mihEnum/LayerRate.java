package mih.model.mihEnum;

public enum LayerRate {
    lsp(1, "lsp"),
    pw(2, "pw");

    private int value;
    private String schemaName;

    LayerRate(int value, String schemaName) {
        this.value = value;
        this.schemaName = schemaName;
    }

    public static LayerRate of(int value) {
        switch (value) {
            case 1:
                return LayerRate.lsp;
            case 2:
                return LayerRate.pw;
        }
        return null;
    }

    public static LayerRate of(String schemaName) {
        switch (schemaName) {
            case "lsp":
                return LayerRate.lsp;

            case "pw":
                return LayerRate.pw;
        }
        return null;
    }

    @Override
    public String toString(){
        return schemaName;
    }
}
