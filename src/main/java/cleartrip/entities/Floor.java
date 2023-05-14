package cleartrip.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class Floor {
    private String buildingId;
    private int floorNumber;
    HashMap<String, ConferenceRoom> conferenceRooms;

    public Floor(String buildingId, int floorNumber) {
        this.buildingId = buildingId;
        this.floorNumber = floorNumber;
        this.conferenceRooms = new HashMap<>();
    }
}
