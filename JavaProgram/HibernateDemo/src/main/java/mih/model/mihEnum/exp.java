package mih.model.mihEnum;

public enum exp {

    be(1, "be"),
    af1(2, "af1"),
    af2(3, "af2"),
    af3(4, "af3"),
    af4(5, "af4"),
    ef(6, "ef"),
    cs6(7, "cs6"),
    cs7(8, "cs7");

    private int value;
    private String schemaName;

    exp(int value, String schemaName) {
        this.value = value;
        this.schemaName = schemaName;
    }

    public static exp of(int value) {
        switch (value) {
            case 1:
                return exp.be;
            case 2:
                return exp.af1;
            case 3:
                return exp.af2;
            case 4:
                return exp.af3;
            case 5:
                return exp.af4;
            case 6:
                return exp.ef;
            case 7:
                return exp.cs6;
            case 8:
                return exp.cs7;
        }
        return null;
    }

    public static exp of(String schemaName) {
        switch (schemaName) {
            case "be":
                return exp.be;

            case "af1":
                return exp.af1;
            case "af2":
                return exp.af2;
            case "af3":
                return exp.af3;
            case "af4":
                return exp.af4;
            case "ef":
                return exp.ef;
            case "cs6":
                return exp.cs6;
            case "cs7":
                return exp.cs7;

        }
        return null;
    }

    @Override
    public String toString(){
        return schemaName;
    }
}
