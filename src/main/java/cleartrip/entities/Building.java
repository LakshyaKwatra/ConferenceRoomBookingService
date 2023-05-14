package cleartrip.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class Building {
    private String buildingId;
    private HashMap<Integer,Floor> floors;

    public Building(String buildingId) {
        this.buildingId = buildingId;
        this.floors = new HashMap<>();
    }

    public HashMap<Integer, Floor> getFloors() {
        return floors;
    }
}
