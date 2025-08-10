class Pharaon extends GamePiece {

    // Constructeur par défaut
    public Pharaon() {
        super("Pharaon", "Red", 0, 0, 0); // Nom par défaut, équipe neutre, direction 0, position (0,0)
        isReflective = false; // Le Pharaon ne réfléchit pas le laser
        isVulnerable = true;  // Le Pharaon est vulnérable
    }

    // Constructeur avec direction, position et équipe
    public Pharaon(String team, int direction, int x, int y) {
        super("Pharaon", team, direction, x, y);
        isReflective = false; // Le Pharaon ne réfléchit pas le laser
        isVulnerable = true;  // Le Pharaon est vulnérable
    }

    /**
     * Détermine la nouvelle direction du laser.
     * Si le Pharaon est touché, il est détruit.
     *
     * @param laserDirection direction d'entrée du laser
     * @return -1 car le laser détruit le Pharaon
     */
    @Override
    public int getNewDirection(int laserDirection) {
        return -1; // Le Pharaon est détruit par le laser
    }

    @Override
    public String toString() {
        return name + " (" + direction + "°) at [" + xPos + ", " + yPos + "] - Team: " + team;
    }
}
