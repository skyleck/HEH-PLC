package be.heh.automation.PlcThread;

public enum EnumPillsDB implements IEnumPlc {
    ISRUN(0,0),
    FIVEPILLS(0,1),
    TENPILLS(0,2),
    FIFTEENPILLS(0,3),
    CAPREMPSTATE(0,4),
    CAPBOUCHSTATE(0,5),
    INPLULSPIL(0,6),
    SA0(0,7),
    SA1(1,0),
    RESETCP(1,2),
    GENFLACONS(1,3),
    LOCALDISTANT(1,6),
    DISTRIBPILLS(4,0),
    MOTBANDE(4,1),
    ABOUCH(4,2),
    LAMPFIVEPILLS(4,3),
    LAMPTENPILLS(4,4),
    LAMPFIFTEENPILLS(4,5),
    COUNTPILLS(15,0),
    COUNTBOTTLE(16,0),
    DBB5(5,0),
    DBB6(6,0),
    DBB7(7,0),
    DBB8(8,0),
    DBW18(18,0);

    private int db;
    private int dbb;

    private EnumPillsDB(int db, int dbb){
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
