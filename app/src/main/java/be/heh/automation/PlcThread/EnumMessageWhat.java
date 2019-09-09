package be.heh.automation.PlcThread;

public enum EnumMessageWhat {
    SWITCH(1),
    TEXTVIEW(2);

    private int i;

    private EnumMessageWhat(int i){
        this.i = i;
    }

    public int getNumber(){
        return i;
    }
}
