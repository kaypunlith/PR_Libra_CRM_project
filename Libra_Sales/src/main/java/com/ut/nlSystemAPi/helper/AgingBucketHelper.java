package com.ut.nlSystemAPi.helper;

import java.util.ArrayList;
import java.util.List;

public class AgingBucketHelper {

    public static class AgingBucket {
        private Integer fromDays;
        private Integer toDays;
        private String label;

        public AgingBucket(Integer fromDays, Integer toDays) {
            this.fromDays = fromDays;
            this.toDays = toDays;
            this.label = fromDays + " - " + toDays;
        }

        // Getters
        public Integer getFromDays() { return fromDays; }
        public Integer getToDays() { return toDays; }
        public String getLabel() { return label; }
    }

    /**
     * Generate aging buckets based on interval and through parameters
     * @param intervalDay The interval for each bucket (e.g., 30 days)
     * @param throughDay The maximum days to consider (e.g., 90 days)
     * @return List of aging buckets
     */
    public static List<AgingBucket> getAgingBuckets(Integer intervalDay, Integer throughDay) {
        List<AgingBucket> buckets = new ArrayList<>();

        if (intervalDay == null || throughDay == null || intervalDay <= 0 || throughDay <= 0) {
            return buckets;
        }

        // Calculate number of buckets
        int numBuckets = (int) Math.ceil((double) throughDay / intervalDay);

        // Generate buckets
        for (int i = 0; i < numBuckets; i++) {
            int fromDays = i * intervalDay + 1;
            int toDays = Math.min((i + 1) * intervalDay, throughDay);
            buckets.add(new AgingBucket(fromDays, toDays));
        }

        return buckets;
    }

    /**
     * Get aging bucket labels for display
     */
    public static List<String> getAgingBucketLabels(Integer intervalDay, Integer throughDay) {
        List<String> labels = new ArrayList<>();
        labels.add("Current"); // 0 days

        List<AgingBucket> buckets = getAgingBuckets(intervalDay, throughDay);
        for (AgingBucket bucket : buckets) {
            labels.add(bucket.getLabel());
        }

        labels.add("> " + throughDay); // Over through day
        labels.add("Total");

        return labels;
    }
}