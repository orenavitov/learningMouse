package mih.model.mihEnum;

public enum AdminSatus {
    admin_up(1, "amin_up"),
    admin_down(2, "admin_down");

    private int value;
    private String schemaName;

    AdminSatus(int value, String schemaName) {
        this.value = value;
        this.schemaName = schemaName;
    }

    public static AdminSatus of(int value) {
        switch (value) {
            case 1:
                return AdminSatus.admin_up;
            case 2:
                return AdminSatus.admin_down;
        }
        return null;
    }

    public static AdminSatus of(String schemaName) {
        switch (schemaName) {
            case "admin_up":
                return AdminSatus.admin_up;

            case "admin_down":
                return AdminSatus.admin_down;
        }
        return null;
    }

    @Override
    public String toString(){
        return schemaName;
    }
}
