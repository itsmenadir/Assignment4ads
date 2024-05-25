import java.util.*;

public class DijkstraSearch<V> implements Search<V> {

    @Override
    public void search(Vertex<V> start) {
        Map<Vertex<V>, Double> distances = new HashMap<>();
        Map<Vertex<V>, Vertex<V>> previous = new HashMap<>();
        PriorityQueue<Vertex<V>> priorityQueue = new PriorityQueue<>(Comparator.comparing(distances::get));


        for (Vertex<V> vertex : getAllVertices(start)) {
            distances.put(vertex, Double.MAX_VALUE);
        }
        distances.put(start, 0.0);
        priorityQueue.add(start);

        while (!priorityQueue.isEmpty()) {
            Vertex<V> current = priorityQueue.poll();

            for (Map.Entry<Vertex<V>, Double> neighborEntry : current.getAdjacentVertices().entrySet()) {
                Vertex<V> neighbor = neighborEntry.getKey();
                double weight = neighborEntry.getValue();
                double distanceThroughCurrent = distances.get(current) + weight;

                if (distanceThroughCurrent < distances.get(neighbor)) {
                    priorityQueue.remove(neighbor);
                    distances.put(neighbor, distanceThroughCurrent);
                    previous.put(neighbor, current);
                    priorityQueue.add(neighbor);
                }
            }
        }


        for (Map.Entry<Vertex<V>, Double> entry : distances.entrySet()) {
            System.out.println("Distance from " + start.getData() + " to " + entry.getKey().getData() + " is " + entry.getValue());
        }
    }


    private Set<Vertex<V>> getAllVertices(Vertex<V> start) {
        Set<Vertex<V>> allVertices = new HashSet<>();
        Queue<Vertex<V>> queue = new LinkedList<>();
        allVertices.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            Vertex<V> current = queue.poll();

            for (Vertex<V> neighbor : current.getAdjacentVertices().keySet()) {
                if (!allVertices.contains(neighbor)) {
                    allVertices.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return allVertices;
    }
}