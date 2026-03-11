import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AnalyticsDashboard {

    private final ConcurrentHashMap<String, AtomicInteger> pageViews = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Set<String>> uniqueVisitors = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, AtomicInteger> trafficSources = new ConcurrentHashMap<>();
    public void processEvent(String url, String userId, String source) {
        pageViews.computeIfAbsent(url, k -> new AtomicInteger(0)).incrementAndGet();
        uniqueVisitors.computeIfAbsent(url, k -> ConcurrentHashMap.newKeySet()).add(userId);
        trafficSources.computeIfAbsent(source, k -> new AtomicInteger(0)).incrementAndGet();
    }


    public void getDashboard() {
        System.out.println("\n--- REAL-TIME DASHBOARD (Updated) ---");

        PriorityQueue<Map.Entry<String, AtomicInteger>> topPagesHeap = new PriorityQueue<>(
            Comparator.comparingInt(e -> e.getValue().get())
        );

        for (Map.Entry<String, AtomicInteger> entry : pageViews.entrySet()) {
            topPagesHeap.offer(entry);
            if (topPagesHeap.size() > 10) {
                topPagesHeap.poll(); // Remove the smallest to keep only top 10
            }
        }

        // Convert heap to sorted list for display
        List<Map.Entry<String, AtomicInteger>> topList = new ArrayList<>(topPagesHeap);
        topList.sort((e1, e2) -> e2.getValue().get() - e1.getValue().get());

        System.out.println("Top Pages:");
        for (int i = 0; i < topList.size(); i++) {
            String url = topList.get(i).getKey();
            int views = topList.get(i).getValue().get();
            int unique = uniqueVisitors.get(url).size();
            System.out.printf("%d. %s - %d views (%d unique)%n", (i + 1), url, views, unique);
        }

        System.out.println("\nTraffic Sources:");
        int totalViews = trafficSources.values().stream().mapToInt(AtomicInteger::get).sum();
        trafficSources.forEach((source, count) -> {
            double percentage = (count.get() / (double) totalViews) * 100;
            System.out.printf("%s: %.1f%%%n", source, percentage);
        });
    }

    public static void main(String[] args) {
        AnalyticsDashboard dashboard = new AnalyticsDashboard();

        dashboard.processEvent("/article/breaking-news", "user_1", "google");
        dashboard.processEvent("/article/breaking-news", "user_2", "facebook");
        dashboard.processEvent("/article/breaking-news", "user_1", "google");
        dashboard.processEvent("/sports/final", "user_3", "direct");
        dashboard.processEvent("/tech/iphone-review", "user_4", "google");

        dashboard.getDashboard();
    }
}
