package mih.model.mihEnum;

public enum Type {

    pe_pe(1, "pe_pe"),
    pe_p(2, "pe_p"),
    p_p(3, "p_p"),
    p_pe(4, "p_pe");

    private int value;
    private String schemaName;

    Type(int value, String schemaName) {
        this.value = value;
        this.schemaName = schemaName;
    }

    public static Type of(int value) {
        switch (value) {
            case 1:
                return Type.pe_pe;
            case 2:
                return Type.pe_p;
            case 3:
                return Type.p_p;
            case 4:
                return Type.p_pe;

        }
        return null;
    }

    public static Type of(String schemaName) {
        switch (schemaName) {
            case "pe_pe":
                return Type.pe_pe;

            case "pe_p":
                return Type.pe_p;
            case "p_p":
                return Type.p_p;
            case "p_pe":
                return Type.p_pe;
        }
        return null;
    }

    @Override
    public String toString(){
        return schemaName;
    }

}
