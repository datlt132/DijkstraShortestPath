package algo;

import models.Edge;
import models.Graph;
import models.Node;

import java.util.*;

public class DijkstraAlgorithm {
    private final Graph graph;
    private final Map<Node, Node> predecessors = new HashMap<>();
    private final Map<Node, Integer> distances = new HashMap<>();
    private final HashSet<Node> visited = new HashSet<>();
    private final PriorityQueue<Node> unvisited = new PriorityQueue<>(new NodeComparator());

    public DijkstraAlgorithm(Graph graph) {
        this.graph = graph;

        // set all distance to infinity
        for (Node node : graph.getNodes()) {
            distances.put(node, Integer.MAX_VALUE);
        }
    }

    private void validateGraph() {
        if (graph.getSource() == null) {
            throw new IllegalStateException("Source must be present in the graph");
        }

        if (graph.getDestination() == null) {
            throw new IllegalStateException("Destination must be present in the graph");
        }

        for (Node node : graph.getNodes()) {
            if (!graph.isNodeReachable(node)) {
                throw new IllegalStateException("Graph contains unreachable nodes");
            }
        }
    }

    public void run() throws IllegalStateException {
        validateGraph();

        // set source node distance to 0 and add source node to visited
        Node source = graph.getSource();
        distances.put(source, 0);
        visited.add(source);

        // get all neighbor edge of source node
        for (Edge neighbor : getNeighbors(source)) {
            Node adjacent = getAdjacent(neighbor, source);
            if (adjacent == null)
                continue;

            distances.put(adjacent, neighbor.getWeight());
            predecessors.put(adjacent, source);
            unvisited.add(adjacent);
        }

        while (!unvisited.isEmpty()) {
            // update distance and add node to visited
            Node current = unvisited.poll();
            updateDistance(current);
            unvisited.remove(current);
            visited.add(current);
        }

        for (Node node : graph.getNodes()) {
            node.setPath(getPath(node));
        }

        graph.setSolved(true);

    }

    private void updateDistance(Node node) {
        int distance = distances.get(node);

        for (Edge neighbor : getNeighbors(node)) {
            Node adjacent = getAdjacent(neighbor, node);

            // continue if node has already visited
            if (visited.contains(adjacent))
                continue;

            int currentDist = distances.get(adjacent);
            int newDist = distance + neighbor.getWeight();

            // update distance map and predecessors if newDist < currentDist, add adjacent to unvisited list
            if (newDist < currentDist) {
                distances.put(adjacent, newDist);
                predecessors.put(adjacent, node);
                unvisited.add(adjacent);
            }
        }
    }

    private Node getAdjacent(Edge edge, Node node) {
        if (edge.getNodeOne() != node && edge.getNodeTwo() != node)
            return null;

        return node == edge.getNodeTwo() ? edge.getNodeOne() : edge.getNodeTwo();
    }

    private List<Edge> getNeighbors(Node node) {
        List<Edge> neighbors = new ArrayList<>();

        for (Edge edge : graph.getEdges()) {
            if (edge.hasNode(node))
                neighbors.add(edge);
        }

        return neighbors;
    }

    public List<Node> getDestinationPath() {
        return getPath(graph.getDestination());
    }

    public List<Node> getPath(Node node) {
        List<Node> path = new ArrayList<>();

        Node current = node;
        path.add(current);
        while (current != graph.getSource()) {
            current = predecessors.get(current);
            path.add(current);
        }

        Collections.reverse(path);

        return path;
    }

    public class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node node1, Node node2) {
            return distances.get(node1) - distances.get(node2);
        }
    }

}
