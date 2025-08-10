public class Pyramid extends GamePiece {

    /**
     * Constructeur par défaut pour une Pyramide neutre (sans équipe, orientée à 0°).
     */
    public Pyramid() {
        super("Pyramid", "neutral", 0, 0, 0);
        isReflective = true; // Le miroir est actif.
        isVulnerable = true; // La pyramide peut être détruite.
    }

    /**
     * Constructeur pour une Pyramide avec une équipe et une orientation spécifique.
     * 
     * @param team      L'équipe de la pièce ("red" ou "yellow").
     * @param direction L'orientation initiale de la pièce (0°, 90°, 180°, 270°).
     */
    public Pyramid(String team, int direction) {
        super("Pyramid", team, direction, 0, 0);
        isReflective = true;
        isVulnerable = true;
    }

    /**
     * Détermine la nouvelle direction du laser après interaction avec la Pyramide.
     * Si le laser frappe une face non-réfléchissante, la méthode retourne -1.
     * 
     * @param laserDirection La direction d'entrée du laser (0°, 90°, 180°, 270°).
     * @return La nouvelle direction du laser (0°, 90°, 180°, 270°) ou -1 si la pyramide est détruite.
     */
    @Override
    public int getNewDirection(int laserDirection) {
        // Vérifie si la direction du laser est invalide.
        if (laserDirection < 0 || laserDirection > 270 || laserDirection % 90 != 0) {
            return -1; // Laser invalide.
        }

        // Table de correspondance : gère les réflexions en fonction de l'orientation de la pyramide
        // et de la direction d'entrée du laser.
        int[][] redirectionMatrix = {
            // {0° entry, 90° entry, 180° entry, 270° entry}
            {-1,  0,  90, -1},  // Pyramide orientée à 0°
            { 90, -1, -1, 180}, // Pyramide orientée à 90°
            {270, 180, -1, -1}, // Pyramide orientée à 180°
            {-1,  0, 270, -1}   // Pyramide orientée à 270°
        };

        // Retourne la nouvelle direction selon la matrice.
        return redirectionMatrix[direction / 90][laserDirection / 90];
    }

    /**
     * Représentation textuelle de la Pyramide.
     * 
     * @return Une chaîne décrivant la pyramide.
     */
    @Override
    public String toString() {
        return name + " (" + direction + "°) - Équipe : " + team + " à [" + xPos + ", " + yPos + "]";
    }
}
