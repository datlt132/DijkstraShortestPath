package gui;

import models.Edge;
import models.Node;
import utils.MathUtils;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DrawUtils {
    private static final float OVERLAP_RADIUS = 25;
    private static final int EDGE_WIDTH = 6;
    private static final int RADIUS = 20;
    private final Graphics2D graphic;

    private final String NODE_COLOR = "#9C27B0";
    private final String INNER_NODE_COLOR = "#E1BEE7";
    private final String SOURCE_NODE_COLOR = "#00BCD4";
    private final String INNER_SOURCE_NODE_COLOR = "#B2EBF2";
    private final String DESTINATION_NODE_COLOR = "#F44336";
    private final String INNER_DESTINATION_NODE_COLOR = "#FFCDD2";
    private final String HOVER_NODE_COLOR = "#E91E63";

    private final String SHORTEST_PATH_COLOR = "#00BCD4";
    private final String HOVER_EDGE_COLOR = "#E1BEE7";
    private final String EDGE_COLOR = "#555555";
    private final String WEIGHT_COLOR = "#CCCCCC";


    public DrawUtils(Graphics2D graphics2D) {
        graphic = graphics2D;
    }

    /**
     * Check if mouse click within bounds of point P (with RADIUS around P)
     * Input: MouseEvent e, Point p
     * Output: boolean
     */
    public static boolean isWithinBounds(MouseEvent e, Point p) {
        int x = e.getX();
        int y = e.getY();

        int boundX = (int) p.getX();
        int boundY = (int) p.getY();

        return (x <= boundX + RADIUS && x >= boundX - RADIUS) && (y <= boundY + RADIUS && y >= boundY - RADIUS);
    }

    /**
     * Check if the mouse click coincides with point P (with OVERLAP_RADIUS around P)
     * Input: MouseEvent e, Point p
     * Output: boolean
     */
    public static boolean isOverlapping(MouseEvent e, Point p) {
        int x = e.getX();
        int y = e.getY();

        int boundX = (int) p.getX();
        int boundY = (int) p.getY();

        return (x <= boundX + OVERLAP_RADIUS && x >= boundX - OVERLAP_RADIUS) && (y <= boundY + OVERLAP_RADIUS && y >= boundY - OVERLAP_RADIUS);
    }

    /**
     * Check if the mouse click on edge (with EDGE_WIDTH)
     * Input: MouseEvent e, Edge edge
     * Output: boolean
     */
    public static boolean isOnEdge(MouseEvent e, Edge edge) {
        double dist = MathUtils.getDistToSegment(e.getPoint(),
                edge.getNodeOne().getCoord(),
                edge.getNodeTwo().getCoord());
        return dist < EDGE_WIDTH;
    }

    public static Color parseColor(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }

    public void drawShortestPath(List<Node> path) {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < path.size() - 1; i++) {
            edges.add(new Edge(path.get(i), path.get(i + 1)));
        }

        for (Edge edge : edges) {
            drawPath(edge);
        }
    }

    public void drawPath(Edge edge) {
        graphic.setColor(parseColor(SHORTEST_PATH_COLOR));
        drawBoldEdge(edge);
    }

    public void drawHoveredEdge(Edge edge) {
        graphic.setColor(parseColor(HOVER_EDGE_COLOR));
        drawBoldEdge(edge);
    }

    private void drawBoldEdge(Edge edge) {
        drawBaseEdge(edge, 8);
        drawWeight(edge, 26);
    }

    public void drawEdge(Edge edge) {
        graphic.setColor(parseColor(EDGE_COLOR));
        drawBaseEdge(edge, 3);
        drawWeight(edge, 20);
    }

    private void drawBaseEdge(Edge edge, int width) {
        Point from = edge.getNodeOne().getCoord();
        Point to = edge.getNodeTwo().getCoord();
        graphic.setStroke(new BasicStroke(width));
        graphic.drawLine(from.x, from.y, to.x, to.y);
    }

    public void drawWeight(Edge edge, int RADIUS) {
        Point from = edge.getNodeOne().getCoord();
        Point to = edge.getNodeTwo().getCoord();
        int x = (from.x + to.x) / 2;
        int y = (from.y + to.y) / 2;

        int rad = RADIUS / 2;
        graphic.fillOval(x - rad, y - rad, RADIUS, RADIUS);
        drawWeightText(String.valueOf(edge.getWeight()), x, y);
    }

    private void drawWeightText(String text, int x, int y) {
        graphic.setColor(parseColor(WEIGHT_COLOR));
        FontMetrics fm = graphic.getFontMetrics();
        double tWidth = fm.getStringBounds(text, graphic).getWidth();
        graphic.drawString(text, (int) (x - tWidth / 2), (y + fm.getMaxAscent() / 2));
    }

    public void drawHalo(Node node) {
        graphic.setColor(parseColor(HOVER_NODE_COLOR));
        int outerRadius = RADIUS + 5;
        graphic.fillOval(node.getX() - outerRadius, node.getY() - outerRadius, 2 * outerRadius, 2 * outerRadius);
    }

    public void drawNormalNode(Node node) {
        drawNode(node, NODE_COLOR, INNER_NODE_COLOR);
    }

    public void drawSourceNode(Node node) {
        drawNode(node, SOURCE_NODE_COLOR, INNER_SOURCE_NODE_COLOR);
    }

    public void drawDestinationNode(Node node) {
        drawNode(node, DESTINATION_NODE_COLOR, INNER_DESTINATION_NODE_COLOR);
    }

    private void drawNode(Node node, String nodeColor, String innerNodeColor) {
        graphic.setColor(parseColor(nodeColor));
        graphic.fillOval(node.getX() - RADIUS, node.getY() - RADIUS, 2 * RADIUS, 2 * RADIUS);

        int innerNodeRadius = RADIUS - 5;
        graphic.setColor(parseColor(innerNodeColor));
        graphic.fillOval(node.getX() - innerNodeRadius, node.getY() - innerNodeRadius, 2 * innerNodeRadius, 2 * innerNodeRadius);

        graphic.setColor(parseColor(nodeColor));
        drawNodeId(String.valueOf(node.getId()), node.getX(), node.getY());
    }

    private void drawNodeId(String text, int x, int y) {
        FontMetrics fm = graphic.getFontMetrics();
        double tWidth = fm.getStringBounds(text, graphic).getWidth();
        graphic.drawString(text, (int) (x - tWidth / 2), (y + fm.getMaxAscent() / 2));
    }

}
