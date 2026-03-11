High-Scale System Design Challenges
This repository showcases Java-based solutions to 10 real-world system design problems. Each challenge emphasizes high-performance data structures, constant-time operations (O(1)), and the ability to support millions of concurrent users.

🛠 Project Goals
- Performance: Sub-millisecond response times.
- Scalability: Efficient handling of datasets with 10M+ entries.
- Concurrency: Safe execution under thousands of simultaneous requests.

📂 Problem Index
1. Social Media Username Checker
- Scenario: Registration system for 10M users.
- Key Focus: O(1) lookups, attempt tracking, and smart suggestions.
- Tech: HashMap, frequency counting.
2. E-commerce Flash Sale Manager
- Scenario: 50,000 users competing for 100 items.
- Key Focus: Thread safety, oversell prevention, FIFO waiting lists.
- Tech: ConcurrentHashMap, LinkedHashMap, atomic operations.
3. DNS Cache with TTL
- Scenario: Reduce DNS lookup latency from 100ms to <1ms.
- Key Focus: Cache expiration, LRU eviction, hit/miss ratios.
- Tech: Custom Entry class, background cleanup threads.
4. Plagiarism Detection System
- Scenario: Compare essays against 100,000 documents.
- Key Focus: Document fingerprinting, similarity indexing.
- Tech: N-Grams, string hashing, set intersections.
5. Real-Time Analytics Dashboard
- Scenario: 1M page views/hour with live updates.
- Key Focus: Multi-dimensional frequency counting, Top-N stats.
- Tech: LinkedHashMap, PriorityQueue.
6. Distributed Rate Limiter
- Scenario: API Gateway for 100,000 clients (1k req/hr limit).
- Key Focus: Token Bucket algorithm, burst traffic handling.
- Tech: Sliding windows, atomic counters.
7. Autocomplete Search System
- Scenario: 10M queries with prefix matching.
- Key Focus: Low-latency (<50ms) suggestions.
- Tech: Trie + HashMap hybrid, Min-Heaps.
8. Parking Lot Management
- Scenario: 500 spots with license plate tracking.
- Key Focus: Open addressing, collision resolution.
- Tech: Linear probing, custom hash functions.
9. Financial Fraud Detection
- Scenario: Real-time suspicious transaction detection.
- Key Focus: Two-Sum variants, time-window filtering.
- Tech: Complement lookup via hash tables.
10. Multi-Level Cache System
- Scenario: Netflix-style tiered storage (L1 Memory, L2 SSD, L3 DB).
- Key Focus: Data promotion, cache invalidation.
- Tech: Tiered hash tables, LRU strategy.
