class Obelisk extends GamePiece {
    private boolean stacked;

    // Constructeur par défaut
    public Obelisk() {
        super("Obelisk", "Red", 0, 0, 0); // Par défaut, équipe neutre, direction 0, position (0, 0)
        this.stacked = false;
        isReflective = false;
        isVulnerable = true;
    }

    // Constructeur avec équipe, direction et position
    public Obelisk(String team, int direction, int x, int y, boolean stacked) {
        super("Obelisk", team, direction, x, y);
        this.stacked = stacked;
        isReflective = false; // Les Obelisks ne réfléchissent pas les lasers
        isVulnerable = true;  // Les Obelisks sont vulnérables par défaut
    }

    // Getter pour savoir si l'Obelisk est empilé
    public boolean isStacked() {
        return stacked;
    }

    // Setter pour modifier l'état d'empilement
    public void setStacked(boolean stacked) {
        this.stacked = stacked;
    }

    /**
     * Détermine la nouvelle direction du laser.
     * Les Obelisks absorbent les lasers sans modifier leur direction.
     *
     * @param laserDirection direction d'entrée du laser
     * @return -1 car le laser est absorbé
     */
    @Override
    public int getNewDirection(int laserDirection) {
        return -1; // Les lasers sont absorbés par l'Obelisk
    }

    @Override
    public String toString() {
        return name + " (" + direction + "°) at [" + xPos + ", " + yPos + "] - Team: " + team
                + (stacked ? " - Stacked" : " - Not Stacked");
    }
}
