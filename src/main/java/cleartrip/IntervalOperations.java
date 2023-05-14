package cleartrip;

import cleartrip.entities.Interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IntervalOperations {
    public static List<Interval> mergeAllIntervals(List<Interval> intervals) {
        if(intervals.size() <= 1) {
            return intervals;
        }
        Collections.sort(intervals, (i1, i2) -> {
            if(i1.getStartTime() == i2.getStartTime()) {
                return Integer.compare(i1.getStartTime(),i2.getStartTime());
            }
            return Integer.compare(i1.getEndTime(),i2.getEndTime());
        });
        List<Interval> merged = new ArrayList<>();
        Interval current = intervals.get(0);
        for(int i = 1; i < intervals.size(); i++) {
            if(intervals.get(i).getStartTime() <= current.getEndTime()) {
                current.setEndTime(Math.max(current.getEndTime(), intervals.get(i).getEndTime()));
            } else {
                merged.add(current);
                current = intervals.get(i);
            }
        }
        merged.add(current);
        return merged;
    }

    public static List<Interval> deleteInterval(List<Interval> intervals, int startTime, int endTime) {
        List<Interval> result = new ArrayList<>();
        for(Interval interval: intervals) {
            int intervalStartTime = interval.getStartTime();
            int intervalEndTime = interval.getEndTime();
            if(intervalStartTime >= endTime || intervalEndTime <= startTime) { //non overlapping case
                result.add(interval);
            } else if(intervalStartTime < startTime && intervalEndTime > endTime) { //fully overlaps
                result.add(createInterval(intervalStartTime, startTime));
                result.add(createInterval(endTime, intervalEndTime));
            } else if(startTime <= intervalStartTime && endTime < intervalEndTime) {
                result.add(createInterval(endTime, intervalEndTime));
            } else if(endTime >= interval.getEndTime() && startTime > intervalStartTime) {
                result.add(createInterval(interval.getStartTime(), startTime));
            }
        }
        return mergeAllIntervals(result);
    }

    List<Interval> addInterval(List<Interval> intervals, int startTime, int endTime) {
        intervals.add(createInterval(startTime,endTime));
        return mergeAllIntervals(intervals);
    }

    public static boolean isIntervalPresent(List<Interval> intervals, int startTime, int endTime) {
        for(Interval interval: intervals) {
            if(startTime >= interval.getStartTime() && endTime <= interval.getEndTime()) {
                return true;
            }
        }
        return false;
    }

    public static Interval createInterval(int startTime, int endTime) {
        return new Interval(startTime, endTime);
    }

    public static String intervalsToString(List<Interval> intervals) {
        String res = "[";
        for(int i = 0; i < intervals.size(); i++) {
            res += intervals.get(i).toString();
            if(i != intervals.size() - 1) {
                res += ", ";
            }
        }
        res+= "]";
        return res;
    }

}
