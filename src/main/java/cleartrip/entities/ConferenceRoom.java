package cleartrip.entities;

import cleartrip.IntervalOperations;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ConferenceRoom {
    String conferenceRoomId;
    private String buildingId;
    int floorNumber;
    List<Interval> availableSlots;
    List<Interval> bookings;

    public ConferenceRoom(String conferenceRoomId, String buildingId, int floorNumber) {
        this.conferenceRoomId = conferenceRoomId;
        this.buildingId = buildingId;
        this.floorNumber = floorNumber;
        this.availableSlots = new ArrayList<>(Arrays.asList(IntervalOperations.createInterval(1,24)));
        this.bookings = new ArrayList<>();
    }
}
