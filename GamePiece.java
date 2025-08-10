public abstract class GamePiece {
    protected String name;         // Nom de la pièce (e.g., "Pyramid", "Pharaoh")
    protected String team;         // Équipe ("Red" ou "White")
    protected int direction;       // Direction de la pièce (0, 90, 180, 270)
    protected int xPos, yPos;      // Coordonnées actuelles sur le plateau
    protected boolean isReflective; // Indique si la pièce réfléchit le laser
    protected boolean isVulnerable; // Indique si la pièce peut être détruite

    // Constructeur générique
    public GamePiece(String name, String team, int direction, int xPos, int yPos) {
        this.name = name;
        this.team = team;
        this.direction = direction % 360; // Direction entre 0 et 359
        this.xPos = xPos;
        this.yPos = yPos;
    }

    // Méthodes pour la rotation
    public void rotateLeft() {
        direction = (direction + 270) % 360; // Tourne à gauche (-90°)
    }

    public void rotateRight() {
        direction = (direction + 90) % 360; // Tourne à droite (+90°)
    }

    // Getters pour les coordonnées
    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    // Setters pour les coordonnées
    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    // Getters pour l'équipe et le nom
    public String getTeam() {
        return team;
    }

    public String getName() {
        return name;
    }

    // Getter pour la direction
    public int getDirection() {
        return direction;
    }

    // Setter pour la direction
    public void setDirection(int direction) {
        this.direction = direction % 360;
    }

    // Méthode pour savoir si la pièce est réfléchissante
    public boolean isReflective() {
        return isReflective;
    }

    // Méthode pour savoir si la pièce est vulnérable
    public boolean isVulnerable() {
        return isVulnerable;
    }

    // Méthode abstraite pour calculer la nouvelle direction du laser
    public abstract int getNewDirection(int laserDirection);

    // Méthode pour représenter la pièce
    @Override
    public String toString() {
        return name + " (" + team + ") at [" + xPos + ", " + yPos + "] facing " + direction + "°";
    }
}
