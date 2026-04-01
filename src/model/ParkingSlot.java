package model;

public class ParkingSlot {
    private int slotId;
    private int areaId;
    private String slotNumber;
    private String status; // Free or Occupied

    public ParkingSlot() {}

    public ParkingSlot(int slotId, int areaId, String slotNumber, String status) {
        this.slotId = slotId;
        this.areaId = areaId;
        this.slotNumber = slotNumber;
        this.status = status;
    }

    public int getSlotId() { return slotId; }
    public void setSlotId(int slotId) { this.slotId = slotId; }
    public int getAreaId() { return areaId; }
    public void setAreaId(int areaId) { this.areaId = areaId; }
    public String getSlotNumber() { return slotNumber; }
    public void setSlotNumber(String slotNumber) { this.slotNumber = slotNumber; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() { return slotNumber + " [" + status + "]"; }
}
