package cleartrip;

import cleartrip.entities.Building;
import cleartrip.entities.ConferenceRoom;
import cleartrip.entities.Floor;
import cleartrip.entities.Interval;
import cleartrip.repository.BuildingDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ConferenceRoomBookingService {
    private final BuildingDAO buildingDAO = BuildingDAO.getInstance();
    public void addBuilding(String buildingId) {
        Building building = new Building(buildingId);
        buildingDAO.addBuilding(building);
    }

    public void addFloor(String buildingId, int floorNumber) {
        Building building = buildingDAO.getById(buildingId);
        if(Objects.isNull(building)) {
            return;
        }
        if(building.getFloors().containsKey(floorNumber)) {
            return;
        }
        building.getFloors().put(floorNumber, new Floor(buildingId, floorNumber));
        System.out.println("Added floor "+ floorNumber +" to building "+buildingId);
    }

    public void addConferenceRoom(String conferenceRoomId, int floorNumber, String buildingId) {
        Building building = buildingDAO.getById(buildingId);
        if(Objects.isNull(building)) {
            return;
        }
        if(!building.getFloors().containsKey(floorNumber)) {
            return;
        }
        HashMap<String, ConferenceRoom> conferenceRoomHashMap = building.getFloors().get(floorNumber).getConferenceRooms();
        if(conferenceRoomHashMap.containsKey(conferenceRoomId)) {
            return;
        }
        ConferenceRoom conferenceRoom = new ConferenceRoom(conferenceRoomId, buildingId, floorNumber);
        building.getFloors().get(floorNumber).getConferenceRooms().put(conferenceRoomId, conferenceRoom);
        System.out.println("Added conference room "+ conferenceRoomId+ " to floor "+ floorNumber +" and building "+buildingId);
    }

    public void listRooms() {
        HashMap<String, Building> buildingData = buildingDAO.getBuildingData();
        for(Building building: buildingData.values()) {
            HashMap<Integer, Floor> floorMap = building.getFloors();
            for(Floor floor: floorMap.values()) {
                HashMap<String, ConferenceRoom> conferenceRoomMap = floor.getConferenceRooms();
                for(ConferenceRoom conferenceRoom: conferenceRoomMap.values()) {
                    System.out.println(String.format("%s %d %s %s",conferenceRoom.getConferenceRoomId(), floor.getFloorNumber(), building.getBuildingId(), IntervalOperations.intervalsToString(conferenceRoom.getAvailableSlots())));
                }
            }
        }
    }

    public void book(int startTime, int endTime, String buildingId, int floorNumber, String conferenceRoomId) {
        if(startTime < 0 || endTime > 24 || endTime - startTime + 1 > 12) {
            return;
        }
        Building building = buildingDAO.getById(buildingId);
        if(Objects.isNull(building)) {
            return;
        }
        Floor floor = building.getFloors().get(floorNumber);
        if(Objects.isNull(floor)) {
            return;
        }
        ConferenceRoom conferenceRoom = floor.getConferenceRooms().get(conferenceRoomId);
        if(Objects.isNull(conferenceRoom)) {
            return;
        }
        if(!IntervalOperations.isIntervalPresent(conferenceRoom.getAvailableSlots(), startTime, endTime)) {
            return;
        }
        conferenceRoom.setAvailableSlots(IntervalOperations.deleteInterval(conferenceRoom.getAvailableSlots(), startTime, endTime));
        conferenceRoom.getBookings().add(IntervalOperations.createInterval(startTime, endTime));
        conferenceRoom.setBookings(IntervalOperations.mergeAllIntervals(conferenceRoom.getBookings()));
        System.out.println("Slot booked");
    }

    public void cancel(int startTime, int endTime, String buildingId, int floorNumber, String conferenceRoomId) {
        if(startTime < 0 || endTime > 24 || endTime - startTime + 1 > 12) {
            return;
        }
        Building building = buildingDAO.getById(buildingId);
        if(Objects.isNull(building)) {
            return;
        }
        Floor floor = building.getFloors().get(floorNumber);
        if(Objects.isNull(floor)) {
            return;
        }
        ConferenceRoom conferenceRoom = floor.getConferenceRooms().get(conferenceRoomId);
        if(Objects.isNull(conferenceRoom)) {
            return;
        }
        if(!IntervalOperations.isIntervalPresent(conferenceRoom.getBookings(), startTime, endTime)) {
            return;
        }
        conferenceRoom.setBookings(IntervalOperations.deleteInterval(conferenceRoom.getBookings(), startTime, endTime));
        conferenceRoom.getAvailableSlots().add(IntervalOperations.createInterval(startTime,endTime));
        conferenceRoom.setAvailableSlots(IntervalOperations.mergeAllIntervals(conferenceRoom.getAvailableSlots()));
        System.out.println("Booking Cancelled");
    }

    public void listBookings() {
        HashMap<String, Building> buildingData = buildingDAO.getBuildingData();
        for(Building building: buildingData.values()) {
            for(Floor floor: building.getFloors().values()) {
                for(ConferenceRoom conferenceRoom: floor.getConferenceRooms().values()) {
                    for(Interval booking: conferenceRoom.getBookings()) {
                        System.out.println(booking.toString()+" "+floor.getFloorNumber()+" "+building.getBuildingId()+" "+conferenceRoom.getConferenceRoomId());
                    }
                }
            }
        }
    }

    public void suggest(int startTime, int endTime, String buildingId, int floorNumber, String conferenceRoomId) {
        if(startTime < 0 || endTime > 24 || endTime - startTime + 1 > 12) {
            return;
        }
        Building building = buildingDAO.getById(buildingId);
        if(Objects.isNull(building)) {
            return;
        }
        Floor floor = building.getFloors().get(floorNumber);
        if(Objects.isNull(floor)) {
            return;
        }
        ConferenceRoom conferenceRoom = floor.getConferenceRooms().get(conferenceRoomId);
        if(Objects.isNull(conferenceRoom)) {
            return;
        }
        if(IntervalOperations.isIntervalPresent(conferenceRoom.getAvailableSlots(), startTime, endTime)) {
            conferenceRoom.setAvailableSlots(IntervalOperations.deleteInterval(conferenceRoom.getAvailableSlots(), startTime, endTime));
            conferenceRoom.getBookings().add(IntervalOperations.createInterval(startTime, endTime));
            conferenceRoom.setBookings(IntervalOperations.mergeAllIntervals(conferenceRoom.getBookings()));
            return;
        }
        List<Interval> suggestions = new ArrayList<>();
        for(Interval interval: conferenceRoom.getAvailableSlots()) {
            if(interval.getStartTime() >= startTime) {
                suggestions.add(interval);
                if(suggestions.size() == 3) {
                    break;
                }
            }
        }
        System.out.println(IntervalOperations.intervalsToString(suggestions));

    }
}
