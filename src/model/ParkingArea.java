package model;

public class ParkingArea {
    private int areaId;
    private String location;
    private int totalSlots;

    public ParkingArea() {}

    public ParkingArea(int areaId, String location, int totalSlots) {
        this.areaId = areaId;
        this.location = location;
        this.totalSlots = totalSlots;
    }

    public int getAreaId() { return areaId; }
    public void setAreaId(int areaId) { this.areaId = areaId; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public int getTotalSlots() { return totalSlots; }
    public void setTotalSlots(int totalSlots) { this.totalSlots = totalSlots; }

    @Override
    public String toString() { return location + " (" + totalSlots + ")"; }
}
