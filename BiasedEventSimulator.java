package Solution;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class BiasedEventSimulator {
    private Map<Object, Integer> outcomesCount = new HashMap<>();
    private Object[] outcomes;
    private int[] probabilities;

    public BiasedEventSimulator(Object[][] outcomes) {
        validateProbabilities(outcomes);
        this.outcomes = new Object[outcomes.length];
        this.probabilities = new int[outcomes.length];

        for (int i = 0; i < outcomes.length; i++) {
            this.outcomes[i] = outcomes[i][0];
            this.probabilities[i] = (int) outcomes[i][1];
        }
    }

    private void validateProbabilities(Object[][] outcomes) {
        int totalProbability = 0;
        for (Object[] outcome : outcomes) {
            totalProbability += (int) outcome[1];
        }

        if (totalProbability != 100 && totalProbability != 1) {
            throw new IllegalArgumentException("Total probability should be 100% or 1.");
        }
    }

    public Map<Object, Integer> simulateEvent(int numOccurrences) {
        outcomesCount.clear();

        for (int i = 0; i < numOccurrences; i++) {
            Object outcome = generateOutcome();
            outcomesCount.put(outcome, outcomesCount.getOrDefault(outcome, 0) + 1);
        }

        return outcomesCount;
    }

    private Object generateOutcome() {
        Random random = new Random();
        int randomValue = random.nextInt(100) + 1;
        int cumulativeProbability = 0;

        for (int i = 0; i < outcomes.length; i++) {
            cumulativeProbability += probabilities[i];
            if (randomValue <= cumulativeProbability) {
                return outcomes[i];
            }
        }

        // Should not reach here, but return the last outcome as a fallback
        return outcomes[outcomes.length - 1];
    }

    public static void main(String[] args) {
        // Example: Flipping a biased coin
        Object[][] coinOutcomes =  {{1, 10}, {2, 30}, {3, 15}, {4, 15}, {5, 30}, {6, 0}};
        BiasedEventSimulator coinSimulator = new BiasedEventSimulator(coinOutcomes);

        // Simulate event
        int numOccurrences = 1000;
        Map<Object, Integer> observedDistribution = coinSimulator.simulateEvent(numOccurrences);

        // Display observed distribution
        System.out.println("Simulating the event " + numOccurrences + " times:");
        for (Map.Entry<Object, Integer> entry : observedDistribution.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " times");
        }
    }
}

