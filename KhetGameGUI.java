import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;

public class KhetGameGUI extends JFrame {
    private JPanel boardPanel;
    private JLabel currentPlayerLabel;
    private JButton endTurnButton;
    private JButton fireLaserButton;
    private JButton rotateButton; // Bouton pour rotation
    private final int ROWS = 8; // Nombre de lignes
    private final int COLS = 10; // Nombre de colonnes
    private String currentPlayer = "Red"; // Joueur actuel
    private JButton[][] boardButtons; // Tableau des boutons du plateau
    private HashMap<Point, GamePiece> pieces; // Positions des pièces sur le plateau
    private Point selectedPiece = null; // Pièce actuellement sélectionnée

    public KhetGameGUI() {
        setTitle("Khet Game");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        pieces = new HashMap<>(); // Initialiser les pièces

        // Panneau supérieur
        JPanel topPanel = new JPanel();
        currentPlayerLabel = new JLabel("Current Player: " + currentPlayer);
        topPanel.add(currentPlayerLabel);

        // Panneau inférieur
        JPanel bottomPanel = new JPanel();
        endTurnButton = new JButton("End Turn");
        fireLaserButton = new JButton("Fire Laser");
        rotateButton = new JButton("Rotate"); // Bouton pour rotation
        bottomPanel.add(endTurnButton);
        bottomPanel.add(fireLaserButton);
        bottomPanel.add(rotateButton);

        // Plateau
        boardPanel = new JPanel(new GridLayout(ROWS, COLS));
        boardButtons = new JButton[ROWS][COLS];
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                JButton cellButton = new JButton();
                cellButton.setBackground(Color.LIGHT_GRAY);
                cellButton.setFocusPainted(false);
                cellButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                boardButtons[row][col] = cellButton;
                boardPanel.add(cellButton);

                // Gestion des clics
                int finalRow = row;
                int finalCol = col;
                cellButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleCellClick(finalRow, finalCol);
                    }
                });
            }
        }

        // Ajout des panneaux
        add(topPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Initialisation des pièces
        initializePieces();

        // Gestion des actions
        endTurnButton.addActionListener(e -> switchPlayer());
        fireLaserButton.addActionListener(e -> fireLaser());
        rotateButton.addActionListener(e -> rotateSelectedPiece());

        refreshBoard();
        setVisible(true);
    }

    /**
     * Initialise les pièces sur le plateau.
     */
    private void initializePieces() {
        // Pharaons
        pieces.put(new Point(0, 4), new Pharaon("Red", 0, 0, 4));
        pieces.put(new Point(7, 4), new Pharaon("White", 180, 7, 4));
    
        // Pyramides rouges
        pieces.put(new Point(0, 3), new Pyramid("Red", 90));
        pieces.put(new Point(0, 5), new Pyramid("Red", 270));
        pieces.put(new Point(1, 2), new Pyramid("Red", 90));
        pieces.put(new Point(1, 3), new Pyramid("Red", 90));
        pieces.put(new Point(1, 5), new Pyramid("Red", 270));
        pieces.put(new Point(1, 6), new Pyramid("Red", 90));
    
        // Pyramides blanches
        pieces.put(new Point(7, 3), new Pyramid("White", 90));
        pieces.put(new Point(7, 5), new Pyramid("White", 270));
        pieces.put(new Point(6, 2), new Pyramid("White", 90));
        pieces.put(new Point(6, 3), new Pyramid("White", 90));
        pieces.put(new Point(6, 5), new Pyramid("White", 270));
        pieces.put(new Point(6, 6), new Pyramid("White", 90));
    
        // Djeds rouges
        pieces.put(new Point(0, 2), new djed("Red", 0, 0, 2));
        pieces.put(new Point(0, 6), new djed("Red", 0, 0, 6));
    
        // Djeds blancs
        pieces.put(new Point(7, 2), new djed("White", 180, 7, 2));
        pieces.put(new Point(7, 6), new djed("White", 180, 7, 6));
    
        // Obélisques rouges
        pieces.put(new Point(2, 3), new Obelisk("Red", 0, 2, 3, false));
        pieces.put(new Point(2, 5), new Obelisk("Red", 0, 2, 5, false));
    
        // Obélisques blancs
        pieces.put(new Point(5, 3), new Obelisk("White", 0, 5, 3, false));
        pieces.put(new Point(5, 5), new Obelisk("White", 0, 5, 5, false));
    
        // Horus rouges et blancs
        pieces.put(new Point(1, 1), new Horus("Red", 90));
        pieces.put(new Point(6, 8), new Horus("White", 270));
    
        refreshBoard();
    }
    

    /**
     * Rafraîchit l'affichage.
     */
    private void refreshBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                JButton button = boardButtons[row][col];
                Point point = new Point(row, col);
                if (pieces.containsKey(point)) {
                    GamePiece piece = pieces.get(point);
                    button.setText(""); // Vide le texte
                    button.setIcon(getPieceIcon(piece)); // Charge l'icône
                    button.setBackground(piece.getTeam().equals("Red") ? Color.PINK : Color.LIGHT_GRAY);
                } else {
                    button.setText(""); // Pas de texte
                    button.setIcon(null); // Pas d'icône
                    button.setBackground(Color.LIGHT_GRAY); // Couleur par défaut
                }
            }
        }
    }
    

    /**
     * Permet de faire tourner une pièce sélectionnée.
     */
    private void rotateSelectedPiece() {
        if (selectedPiece != null) {
            GamePiece piece = pieces.get(selectedPiece);
            if (piece != null && piece.getTeam().equals(currentPlayer)) {
                piece.direction = (piece.direction + 90) % 360; // Tourne de 90°
                refreshBoard();
                JOptionPane.showMessageDialog(this, "Piece rotated to " + piece.direction + "°");
            } else {
                JOptionPane.showMessageDialog(this, "You can only rotate your own pieces.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No piece selected.");
        }
    }

    /**
     * Gère les clics sur le plateau.
     */
 /**
 * Gère les clics sur le plateau pour sélectionner ou déplacer une pièce.
 */
private void handleCellClick(int row, int col) {
    Point targetPoint = new Point(row, col);

    if (selectedPiece == null) {
        // Sélectionner une pièce
        if (pieces.containsKey(targetPoint)) {
            GamePiece piece = pieces.get(targetPoint);
            if (piece.getTeam().equals(currentPlayer)) {
                selectedPiece = targetPoint;
                JOptionPane.showMessageDialog(this, "Piece selected at (" + row + ", " + col + ").");
            } else {
                JOptionPane.showMessageDialog(this, "You can only select your own pieces.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No piece on this cell.");
        }
    } else {
        // Déplacer une pièce sélectionnée
        GamePiece selected = pieces.get(selectedPiece);

        if (isMoveValid(selectedPiece, targetPoint)) {
            // Déplacement valide : déplacer la pièce
            pieces.remove(selectedPiece);
            pieces.put(targetPoint, selected);
            selected.setYPos(row);
            selected.setXPos(col);
            selectedPiece = null; // Réinitialiser la sélection
            JOptionPane.showMessageDialog(this, "Piece moved to (" + row + ", " + col + ").");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid move. Select a valid cell.");
        }
    }

    refreshBoard();
}

/**
 * Vérifie si un mouvement est valide.
 */
private boolean isMoveValid(Point from, Point to) {
    // Vérifier que la case cible est dans les limites du plateau
    if (to.x < 0 || to.x >= ROWS || to.y < 0 || to.y >= COLS) {
        return false;
    }

    // Vérifier que la case cible est vide ou ne contient pas une pièce alliée
    if (pieces.containsKey(to)) {
        GamePiece targetPiece = pieces.get(to);
        return !targetPiece.getTeam().equals(currentPlayer); // Doit être une pièce adverse ou vide
    }

    return true;
}

    /**
     * Simule le tir du laser et gère les collisions.
     */
    private void fireLaser() {
        // Définir la position de départ et la direction en fonction du joueur actuel
        Point start;
        int direction;
    
        if (currentPlayer.equals("Red")) {
            start = new Point(0, 0); // Coin supérieur gauche pour le laser rouge
            direction = 90; // Laser rouge tire vers la droite
        } else {
            start = new Point(7, 9); // Coin inférieur droit pour le laser blanc
            direction = 270; // Laser blanc tire vers la gauche
        }
    
        // Debug : Afficher la position et direction initiales
        System.out.println("Laser starting at: (" + start.x + ", " + start.y + "), direction: " + direction);
    
        // Créer un laser pour le joueur actuel
        Laser laser = new Laser(start.y, start.x, direction); // Note : inverser x et y pour correspondre aux indices du tableau
    
        // Exécuter la simulation du laser dans un thread séparé
        new Thread(() -> {
            String result = laser.simulate(pieces, ROWS, COLS, boardButtons);
    
            // Mise à jour de l'interface utilisateur sur le thread principal
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this, result);
    
                // Vérifier si le Pharaon d'un joueur a été touché
                if (result.contains("Pharaon")) {
                    JOptionPane.showMessageDialog(this, currentPlayer + " wins!");
                    System.exit(0);
                }
    
                // Passer au prochain joueur après avoir tiré le laser
                switchPlayer();
    
                // Rafraîchir l'état du plateau pour refléter les changements
                refreshBoard();
            });
        }).start();
    }
    
        

    /**
     * Change le joueur actif.
     */
    private void switchPlayer() {
        currentPlayer = currentPlayer.equals("Red") ? "White" : "Red";
        currentPlayerLabel.setText("Current Player: " + currentPlayer);
    }

    /**
     * Récupère l'icône pour une pièce.
     */
    private ImageIcon getPieceIcon(GamePiece piece) {
        String basePath = "images/";
        String filePath = "";

        switch (piece.getName()) {
            case "djed":
                filePath = basePath + (piece.getTeam().equals("Red") ? "djedred" : "djedgrey");
                filePath += (piece.direction / 90 + 1) + ".png";
                break;
            case "Horus":
                filePath = basePath + (piece.getTeam().equals("Red") ? "horusred" : "horusgrey");
                filePath += (piece.direction / 90 + 1) + ".png";
                break;
            case "Pyramid":
                filePath = basePath + (piece.getTeam().equals("Red") ? "pyramidred" : "pyramidgrey");
                filePath += (piece.direction / 90 + 1) + ".png";
                break;
            case "Pharaon":
                filePath = basePath + (piece.getTeam().equals("Red") ? "pharaonred.png" : "pharaongrey.png");
                break;
            case "Obelisk":
                filePath = basePath + (piece.getTeam().equals("Red") ? "obeliskred.png" : "obeliskgrey.png");
                break;
            case "DoubleObelisk":
                filePath = basePath + (piece.getTeam().equals("Red") ? "doubleobeliskred.png" : "doubleobeliskgrey.png");
                break;
            default:
                return null;
        }

        return new ImageIcon(filePath);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(KhetGameGUI::new);
    }
}