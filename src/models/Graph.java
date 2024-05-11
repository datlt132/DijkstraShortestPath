package models;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    private int count = 1;
    private List<Node> nodes = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();

    private Node source;
    private Node destination;

    private boolean solved = false;

    public boolean isNodeReachable(Node node) {
        for (Edge edge : edges) {
            if (edge.hasNode(node)) {
                return true;
            }
        }

        return false;
    }

    public boolean isSource(Node node) {
        return node == source;
    }

    public boolean isDestination(Node node) {
        return node == destination;
    }

    public void addNode(Point coord) {
        Node node = new Node(coord);
        node.setId(count++);
        nodes.add(node);
        if (node.getId() == 1) {
            source = node;
        }
    }

    public void addEdge(Edge newEdge) {
        if (!edges.contains(newEdge)) {
            edges.add(newEdge);
        }
    }

    public void deleteNode(Node node) {
        List<Edge> delete = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.hasNode(node)) {
                delete.add(edge);
            }
        }
        edges.removeAll(delete);
        nodes.remove(node);
    }

    public void clear() {
        count = 1;
        nodes.clear();
        edges.clear();
        solved = false;

        source = null;
        destination = null;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }


    public Node getSource() {
        return source;
    }

    public void setSource(Node node) {
        if (nodes.contains(node))
            source = node;
    }

    public Node getDestination() {
        return destination;
    }

    public void setDestination(Node node) {
        if (nodes.contains(node))
            destination = node;
    }

}
