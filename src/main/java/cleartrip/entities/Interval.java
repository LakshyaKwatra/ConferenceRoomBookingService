package cleartrip.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Interval {
    private int startTime;
    private int endTime;

    @Override
    public String toString() {
        return "{" + startTime + ":" + endTime + "}";
    }

}
