package mih.model.mihEnum;

public enum Role {

    master(1, "master"),
    slave(2, "slave"),
    restore(3, "restore"),
    master_restore(4, "mastre_restore"),
    slave_restore(5, "slave_restore"),
    escape(6, "escape");

    private int value;
    private String schemaName;

    Role(int value, String schemaName) {
        this.value = value;
        this.schemaName = schemaName;
    }

    public static Role of(int value) {
        switch (value) {
            case 1:
                return Role.master;
            case 2:
                return Role.slave;
            case 3:
                return Role.restore;
            case 4:
                return Role.master_restore;
            case 5:
                return Role.slave_restore;
            case 6:
                return Role.escape;
        }
        return null;
    }

    public static Role of(String schemaName) {
        switch (schemaName) {
            case "master":
                return Role.master;

            case "slave":
                return Role.slave;
            case "restore":
                return Role.restore;
            case "mastre_restore":
                return Role.master_restore;
            case "slave_restore":
                return Role.slave_restore;

            case "escape":
                return Role.escape;
        }
        return null;
    }

    @Override
    public String toString(){
        return schemaName;
    }
}
