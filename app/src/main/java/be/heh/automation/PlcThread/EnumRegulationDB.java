package be.heh.automation.PlcThread;

public enum EnumRegulationDB implements IEnumPlc {
    BTNVALVE1(0,1),
    BTNVALVE2(0,2),
    BTNVALVE3(0,3),
    BTNVALVE4(0,4),
    BTNMANUAL(0,5),
    LOCALDISTANT(0,6),
    VALVE1(1,1),
    VALVE2(1,2),
    VALVE3(1,3),
    VALVE4(1,4),
    LIQUIDLEVEL(16,0),
    SETPOINT(18,0),
    MANUAL(20,0),
    PILOTWORD(22,0),
    DB2(2,0),
    DB3(3,0),
    DB24(24,0),
    DB26(26,0),
    DB28(28,0),
    DB30(30,0);

    private int db;
    private int dbb;

    private EnumRegulationDB(int db, int dbb){
        this.db = db;
        this.dbb = dbb;
    }


    public int getDb() {
        return db;
    }

    public int getDbb() {
        return dbb;
    }
}
