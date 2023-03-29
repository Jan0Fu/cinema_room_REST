package cinema;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SeatRepository {

    private List<Seat> availableSeats = new ArrayList<>();

    public void createList() {
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                availableSeats.add(new Seat(i, j));
            }
        }
    }

    public List<Seat> getSeats() {
        if (availableSeats.isEmpty()) {
            createList();
        }
        return availableSeats;
    }
}
