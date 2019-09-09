package be.heh.automation.domain;

public class Plc {

    private String ip;
    private int rack;
    private int slot;

    public Plc(String ip, int rack, int slot) {
        this.ip = ip;
        this.rack = rack;
        this.slot = slot;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getRack() {
        return rack;
    }

    public void setRack(int rack) {
        this.rack = rack;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
