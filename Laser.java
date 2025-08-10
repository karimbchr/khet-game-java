import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class Laser {
    private int xPos, yPos;      // Position actuelle du laser
    private int direction;      // Direction actuelle du laser (en degrés)

    public Laser(int x, int y, int direction) {
        this.xPos = x;
        this.yPos = y;
        this.direction = direction;
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    public int getDirection() {
        return direction;
    }

    public void setPosition(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * Déplace le laser d'une case dans la direction actuelle.
     */
    public void move() {
        switch (direction) {
            case 0: // Haut
                yPos--;
                break;
            case 90: // Droite
                xPos++;
                break;
            case 180: // Bas
                yPos++;
                break;
            case 270: // Gauche
                xPos--;
                break;
        }
    }

    /**
     * Simule le trajet du laser sur le plateau.
     */
    public String simulate(HashMap<Point, GamePiece> pieces, int rows, int cols, JButton[][] boardButtons) {
        while (true) {
            move(); // Déplace le laser

            // Vérifie si le laser sort du plateau
            if (xPos < 0 || xPos >= cols || yPos < 0 || yPos >= rows) {
                clearLaserPath(boardButtons);
                return "Laser exited the board!";
            }

            // Affiche le laser sur le plateau
            updateLaserPosition(boardButtons, xPos, yPos);

            // Pause pour que l'utilisateur puisse voir le déplacement
            try {
                Thread.sleep(300); // Délai pour visualiser le mouvement
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Vérifie si une pièce est touchée
            Point currentPosition = new Point(yPos, xPos); // Position actuelle du laser
            if (pieces.containsKey(currentPosition)) {
                GamePiece piece = pieces.get(currentPosition);

                // Calcule la nouvelle direction du laser en fonction de la pièce touchée
                int newDirection = piece.getNewDirection(direction);

                if (newDirection == -1) { // Laser absorbé
                    clearLaserPath(boardButtons);

                    if (piece instanceof Pharaon) {
                        return "Pharaon " + piece.getTeam() + " was hit!";
                    } else {
                        pieces.remove(currentPosition); // Détruit la pièce
                        return piece.getName() + " was destroyed!";
                    }
                }

                // Laser réfléchi
                direction = newDirection;

                // Affiche les informations de position et direction
                System.out.println("Laser position : (" + xPos + ", " + yPos + "), direction : " + direction);
            }
        }
    }

    /**
     * Met à jour la position du laser sur le plateau.
     */
    private void updateLaserPosition(JButton[][] boardButtons, int x, int y) {
        if (x >= 0 && x < boardButtons[0].length && y >= 0 && y < boardButtons.length) {
            SwingUtilities.invokeLater(() -> {
                boardButtons[y][x].setBackground(Color.RED); // Indique la position du laser
            });
        }
    }

    /**
     * Efface le chemin du laser sur le plateau.
     */
    private void clearLaserPath(JButton[][] boardButtons) {
        SwingUtilities.invokeLater(() -> {
            for (int row = 0; row < boardButtons.length; row++) {
                for (int col = 0; col < boardButtons[0].length; col++) {
                    if (boardButtons[row][col].getBackground() == Color.RED) {
                        boardButtons[row][col].setBackground(Color.LIGHT_GRAY); // Restaure la couleur d'origine
                    }
                }
            }
        });
    }
}
