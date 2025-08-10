public class Horus extends GamePiece {

    /**
     * Constructeur par défaut pour une pièce Horus neutre (sans équipe, orientée à 0°).
     */
    public Horus() {
        super("Horus", "neutral", 0, 0, 0);
        isReflective = true; // Le miroir semi-réfléchissant est actif.
        isVulnerable = true; // L'unité Horus peut être détruite.
    }

    /**
     * Constructeur pour une pièce Horus avec une équipe et une orientation spécifique.
     * 
     * @param team      L'équipe de la pièce ("red" ou "yellow").
     * @param direction L'orientation initiale de la pièce (0°, 90°, 180°, 270°).
     */
    public Horus(String team, int direction) {
        super("Horus", team, direction, 0, 0);
        isReflective = true;
        isVulnerable = true;
    }

    /**
     * Détermine la nouvelle direction du laser après interaction avec Horus.
     * 
     * @param laserDirection La direction d'entrée du laser (0°, 90°, 180°, 270°).
     * @return La nouvelle direction du laser ou -1 si le laser est absorbé.
     */
    @Override
    public int getNewDirection(int laserDirection) {
        // Vérifie si la direction du laser est invalide.
        if (laserDirection < 0 || laserDirection > 270 || laserDirection % 90 != 0) {
            return -1; // Absorbé si direction invalide.
        }

        // Détermine la nouvelle direction en fonction de l'orientation et de la direction du laser.
        switch (this.direction) {
            case 0:
                if (laserDirection == 180) {
                    return 90; // Réflexion à gauche.
                } else if (laserDirection == 270) {
                    return 0;  // Réflexion à droite.
                }
                break;

            case 90:
                if (laserDirection == 270) {
                    return 180; // Réflexion à gauche.
                } else if (laserDirection == 0) {
                    return 90; // Réflexion à droite.
                }
                break;

            case 180:
                if (laserDirection == 0) {
                    return 270; // Réflexion à gauche.
                } else if (laserDirection == 90) {
                    return 180; // Réflexion à droite.
                }
                break;

            case 270:
                if (laserDirection == 90) {
                    return 0; // Réflexion à gauche.
                } else if (laserDirection == 180) {
                    return 270; // Réflexion à droite.
                }
                break;

            default:
                // Orientation invalide (ne devrait pas arriver).
                break;
        }

        // Si aucune réflexion n'est applicable, retourne -1 pour indiquer une absorption.
        return -1;
    }
}
