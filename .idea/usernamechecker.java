import java.util.*;

public class UsernameChecker {
    private Map<String, Integer> userMap;
    private Map<String, Integer> attemptFrequency;

    public UsernameChecker() {
        userMap = new HashMap<>();
        attemptFrequency = new HashMap<>();
    }

    public void registerUser(String username, int userId) {
        userMap.put(username, userId);
    }

    public boolean checkAvailability(String username) {
        attemptFrequency.put(username, attemptFrequency.getOrDefault(username, 0) + 1);
        return !userMap.containsKey(username);
    }

    public List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();
        if (userMap.containsKey(username)) {
            suggestions.add(username + "1");
            suggestions.add(username + "2");
            suggestions.add(username.replace("_", "."));
        }
        return suggestions;
    }

    public String getMostAttempted() {
        String mostAttempted = null;
        int maxAttempts = 0;
        for (Map.Entry<String, Integer> entry : attemptFrequency.entrySet()) {
            if (entry.getValue() > maxAttempts) {
                maxAttempts = entry.getValue();
                mostAttempted = entry.getKey();
            }
        }
        return mostAttempted + " (" + maxAttempts + " attempts)";
    }

    public static void main(String[] args) {
        UsernameChecker checker = new UsernameChecker();
        checker.registerUser("john_doe", 1);
        checker.registerUser("admin", 2);
        System.out.println("checkAvailability(\"john_doe\") → " + checker.checkAvailability("john_doe"));
        System.out.println("checkAvailability(\"jane_smith\") → " + checker.checkAvailability("jane_smith"));
        System.out.println("suggestAlternatives(\"john_doe\") → " + checker.suggestAlternatives("john_doe"));
        for (int i = 0; i < 10543; i++) {
            checker.checkAvailability("admin");
        }
        System.out.println("getMostAttempted() → " + checker.getMostAttempted());
    }
}