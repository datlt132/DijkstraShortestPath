package gui;

import algo.DijkstraAlgorithm;
import models.Graph;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainWindow extends JPanel {

    private Graph graph;
    private GraphPanel graphPanel;

    public MainWindow() {
        super.setLayout(new BorderLayout());
        setGraphPanel();
    }

    private void setGraphPanel() {
        graph = new Graph();
        graphPanel = new GraphPanel(graph);
        graphPanel.setPreferredSize(new Dimension(9000, 4096));

        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(graphPanel);
        scroll.setPreferredSize(new Dimension(750, 500));
        scroll.getViewport().setViewPosition(new Point(4100, 0));
        add(scroll, BorderLayout.CENTER);
        setButtons();
    }

    private void setButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(setupResetIcon());
        buttonPanel.add(setupRunIcon());
        buttonPanel.add(setupInfoIcon());
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton setupResetIcon() {
        JButton button = setupIcon("reset");
        button.addActionListener(e -> graphPanel.reset());
        return button;
    }

    private JButton setupRunIcon() {
        JButton button = setupIcon("run");
        button.addActionListener(e -> {
            DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(graph);
            try {
                dijkstraAlgorithm.run();
                graphPanel.setPath(dijkstraAlgorithm.getDestinationPath());
            } catch (IllegalStateException ise) {
                JOptionPane.showMessageDialog(null, ise.getMessage());
            }
        });
        return button;
    }

    private JButton setupInfoIcon() {
        JButton button = setupIcon("info");
        button.addActionListener(e -> JOptionPane.showMessageDialog(null,
                "Click on empty space to create new node\n" +
                        "Drag from node to node to create an edge\n" +
                        "Click on edges to set the weight\n\n" +
                        "Combinations:\n" +
                        "Shift + Left Click       :    Set node as source\n" +
                        "Shift + Right Click     :    Set node as destination\n" +
                        "Ctrl  + Drag               :    Reposition Node\n" +
                        "Ctrl  + Click                :    Get Path of Node\n" +
                        "Ctrl  + Shift + Click   :    Delete Node/Edge\n"));
        return button;
    }

    private JButton setupIcon(String img) {
        JButton button = new JButton();
        try {
            Image icon = ImageIO.read(getClass().getResource("/resources/" + img + ".png"));
            ImageIcon imageIcon = new ImageIcon(icon);
            button.setIcon(imageIcon);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return button;
    }

}
