package mih.model.mihEnum;

public enum Direction {
    unidirection(1, "unidirection"),
    bidirection(2, "bidirection");

    private int value;
    private String schemaName;

    Direction(int value, String schemaName) {
        this.value = value;
        this.schemaName = schemaName;
    }

    public static Direction of(int value) {
        switch (value) {
            case 1:
                return Direction.unidirection;
            case 2:
                return Direction.bidirection;
        }
        return null;
    }

    public static Direction of(String schemaName) {
        switch (schemaName) {
            case "admin_up":
                return Direction.unidirection;

            case "admin_down":
                return Direction.bidirection;
        }
        return null;
    }

    @Override
    public String toString(){
        return schemaName;
    }
}
