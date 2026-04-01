package model;

import java.util.Date;

public class Booking {
    private int bookingId;
    private int userId;
    private int slotId;
    private Date bookingTime;
    private Date exitTime;

    public Booking() {}

    public Booking(int bookingId, int userId, int slotId, Date bookingTime, Date exitTime) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.slotId = slotId;
        this.bookingTime = bookingTime;
        this.exitTime = exitTime;
    }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getSlotId() { return slotId; }
    public void setSlotId(int slotId) { this.slotId = slotId; }
    public Date getBookingTime() { return bookingTime; }
    public void setBookingTime(Date bookingTime) { this.bookingTime = bookingTime; }
    public Date getExitTime() { return exitTime; }
    public void setExitTime(Date exitTime) { this.exitTime = exitTime; }

    @Override
    public String toString() { return "Booking#"+bookingId+" slot="+slotId; }
}
