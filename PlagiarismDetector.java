import java.util.*;

public class PlagiarismDetector {
    private Map<String, Set<String>> ngramIndex = new HashMap<>();
    private Map<String, Integer> docTotalNgrams = new HashMap<>();
    
    private final int N = 5; // Using 5-grams as per hints

    public void addDocument(String docId, String content) {
        List<String> ngrams = generateNgrams(content);
        docTotalNgrams.put(docId, ngrams.size());

        for (String gram : ngrams) {
            ngramIndex.computeIfAbsent(gram, k -> new HashSet<>()).add(docId);
        }
    }


    public void analyzeDocument(String content) {
        List<String> inputNgrams = generateNgrams(content);
        Map<String, Integer> matchCounts = new HashMap<>();

        for (String gram : inputNgrams) {
            if (ngramIndex.containsKey(gram)) {
                for (String existingDocId : ngramIndex.get(gram)) {
                    matchCounts.put(existingDocId, matchCounts.getOrDefault(existingDocId, 0) + 1);
                }
            }
        }

        System.out.println("Analysis Results:");
        matchCounts.forEach((docId, count) -> {
            double similarity = (count.doubleValue() / inputNgrams.size()) * 100;
            String status = similarity > 50 ? "PLAGIARISM DETECTED" : (similarity > 10 ? "SUSPICIOUS" : "CLEAN");
            System.out.printf("-> Found %d matching n-grams with %s | Similarity: %.1f%% (%s)%n", 
                              count, docId, similarity, status);
        });
    }


    private List<String> generateNgrams(String text) {
        String[] words = text.toLowerCase().replaceAll("[^a-zA-Z ]", "").split("\\s+");
        List<String> ngrams = new ArrayList<>();
        
        for (int i = 0; i <= words.length - N; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < N; j++) {
                sb.append(words[i + j]).append(j < N - 1 ? " " : "");
            }
            ngrams.add(sb.toString());
        }
        return ngrams;
    }

    public static void main(String[] args) {
        PlagiarismDetector detector = new PlagiarismDetector();

    
        detector.addDocument("essay_089.txt", "the quick brown fox jumps over the lazy dog");
        detector.addDocument("essay_092.txt", "java is a high level class based object oriented programming language");

        String newSubmission = "java is a high level class based programming language and more";
        System.out.println("Analyzing submission...");
        detector.analyzeDocument(newSubmission);
    }
}
