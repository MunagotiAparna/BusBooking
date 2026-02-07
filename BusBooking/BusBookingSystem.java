import java.util.Scanner;

class BusTicketBooking {

    private int availableSeats = 5; 

    
    public synchronized void bookSeat(String passengerName, int seatsRequested) {

        System.out.println(passengerName + " is trying to book " + seatsRequested + " seat(s)");

        if (availableSeats >= seatsRequested) {
            System.out.println("Seats available. Booking in progress for " + passengerName);

            try {
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            availableSeats -= seatsRequested;
            System.out.println("✅ Booking successful for " + passengerName);
            System.out.println("🪑 Seats left: " + availableSeats);
        } else {
            System.out.println("❌ Sorry " + passengerName + ", not enough seats available.");
        }

        System.out.println("----------------------------------");
    }
}

// Passenger Thread
class Passenger extends Thread {

    BusTicketBooking booking;
    String passengerName;
    int seats;

    Passenger(BusTicketBooking booking, String passengerName, int seats) {
        this.booking = booking;
        this.passengerName = passengerName;
        this.seats = seats;
    }

    public void run() {
        booking.bookSeat(passengerName, seats);
    }
}

// Main Class
public class BusBookingSystem {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BusTicketBooking booking = new BusTicketBooking();

        System.out.print("Enter number of passengers: ");
        int n = sc.nextInt();
        sc.nextLine(); 

        Passenger[] passengers = new Passenger[n];

        for (int i = 0; i < n; i++) {
            System.out.println("\nPassenger " + (i + 1));

            System.out.print("Enter passenger name: ");
            String name = sc.nextLine();

            System.out.print("Enter number of seats to book: ");
            int seats = sc.nextInt();
            sc.nextLine(); 

            passengers[i] = new Passenger(booking, name, seats);
        }

        System.out.println("\n🚍 Booking Started...\n");

        // start all threads
        for (Passenger p : passengers) {
            p.start();
        }

        sc.close();
    }
}