package cleartrip;

import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        ConferenceRoomBookingService conferenceRoomBookingService = new ConferenceRoomBookingService();
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String command = scanner.nextLine();
            String[] commandSplit = command.split(" ");
            String commandType = commandSplit[0];

            switch (commandType) {
                case "ADD":
                    switch (commandSplit[1]) {
                        case "BUILDING":
                            conferenceRoomBookingService.addBuilding(commandSplit[2]);
                            break;
                        case "FLOOR":
                            conferenceRoomBookingService.addFloor(commandSplit[2], Integer.parseInt(commandSplit[3]));
                            break;
                        case "CONFROOM":
                            conferenceRoomBookingService.addConferenceRoom(commandSplit[2], Integer.parseInt(commandSplit[3]), commandSplit[4]);
                            break;
                    }
                    break;
                case "LIST":
                    switch (commandSplit[1]) {
                        case "BOOKINGS":
                            conferenceRoomBookingService.listBookings();
                            break;
                        case "ROOMS":
                            conferenceRoomBookingService.listRooms();
                            break;
                    }
                    break;
                case "BOOK":
                    conferenceRoomBookingService.book(Integer.parseInt(commandSplit[1]),Integer.parseInt(commandSplit[2]), commandSplit[3], Integer.parseInt(commandSplit[4]), commandSplit[5]);
                    break;
                case "CANCEL":
                    conferenceRoomBookingService.cancel(Integer.parseInt(commandSplit[1]),Integer.parseInt(commandSplit[2]), commandSplit[3], Integer.parseInt(commandSplit[4]), commandSplit[5]);
                    break;
                case "SUGGEST":
                    conferenceRoomBookingService.suggest(Integer.parseInt(commandSplit[1]),Integer.parseInt(commandSplit[2]), commandSplit[3], Integer.parseInt(commandSplit[4]), commandSplit[5]);
                    break;
            }
        }
    }
}