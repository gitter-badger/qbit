package io.advantageous.qbit.metrics;

import io.advantageous.qbit.queue.QueueCallBackHandler;
import io.advantageous.qbit.util.Timer;
import io.advantageous.qbit.metrics.support.MinuteStat;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by rhightower on 1/28/15.
 */
public class StatService  implements QueueCallBackHandler {
    private final StatRecorder recorder;
    private long now;
    private long startMinute;
    private Map<String, MinuteStat> statMap;
    private final StatReplicator replica;

    public StatService(final StatRecorder recorder, final StatReplicator replica) {
        this.recorder = recorder;
        this.statMap = new ConcurrentHashMap<>(100);
        now = Timer.timer().now();
        startMinute = now;
        this.replica = replica;
    }


    public void recordCount(String name, int count) {
        recordWithTime(name, count, now);
    }



    public int currentMinuteCount(String name) {
        return oneMinuteOfStats(name).getTotalCount();
    }

    public int currentSecondCount(String name) {
        return oneMinuteOfStats(name).countThisSecond(now);
    }

    public int lastSecondCount(String name) {
        return oneMinuteOfStats(name).countLastSecond(now);
    }

    public void recordWithTime(String name, int count, long now) {
        oneMinuteOfStats(name).changeBy(count, now);
        replica.recordCount(name, count, now);
    }

    public void recordAllCounts(final long timestamp,
                                final String[] names,
                                final int[] counts) {
        for (int index = 0; index < names.length; index++) {
            String name = names[index];
            int count = counts[index];
            recordWithTime(name, count, timestamp);
        }
    }

    public void recordAllCountsWithTimes(
            final String[] names,
            final int[] counts,
            final long[] times) {

        for (int index = 0; index < names.length; index++) {
            String name = names[index];
            int count = counts[index];
            long now = times[index];
            recordWithTime(name, count, now);
        }
    }


    private MinuteStat oneMinuteOfStats(String name) {
        MinuteStat oneMinuteOfStats = this.statMap.get(name);
        if (oneMinuteOfStats==null) {
            oneMinuteOfStats = new MinuteStat(now, name);
            this.statMap.put(name, oneMinuteOfStats);
        }
        return oneMinuteOfStats;
    }

    public void queueLimit() {
        now = Timer.timer().now();
        process();
    }

    public void queueEmpty() {
        now = Timer.timer().now();
        process();
    }

    //For testing
    void time(long time) {
        now = time;
    }

    void process() {
        long duration = (now - startMinute)/1_000;
        if ( duration > 60 ) {
            startMinute = now;

            final ArrayList<MinuteStat> stats = new ArrayList<>(this.statMap.values());
            this.recorder.record(stats);
            this.statMap = new ConcurrentHashMap<>(100);

        }
    }
}
