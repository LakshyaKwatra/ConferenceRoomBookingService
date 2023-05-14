package cleartrip.repository;

import cleartrip.entities.Building;

import java.util.HashMap;

public class BuildingDAO {
    private HashMap<String, Building> buildingData;
    private static BuildingDAO instance;

    private BuildingDAO() {
        this.buildingData = new HashMap<>();
    }

    public void addBuilding(Building building) {
        if(!buildingData.containsKey(building.getBuildingId())) {
            buildingData.put(building.getBuildingId(), building);
            System.out.println("Added building " + building.getBuildingId());
        }
    }

    public Building getById(String id) {
        return buildingData.get(id);
    }

    public HashMap<String, Building> getBuildingData() {
        return buildingData;
    }

    public static BuildingDAO getInstance() {
        if(instance == null) {
            return new BuildingDAO();
        }
        return instance;
    }
}
