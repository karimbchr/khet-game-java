class djed extends GamePiece {
    private static final int[][] DIRECTION_MAP = {
        {270, 180, 90, 0},  // Direction = 0°
        {90, 0, 270, 180},  // Direction = 90°
        {270, 180, 90, 0},  // Direction = 180°
        {90, 0, 270, 180}   // Direction = 270°
    };

    // Constructeur par défaut
    public djed() {
        super("Djed", "Red", 0, 0, 0); // Par défaut, équipe rouge, position (0,0)
        isReflective = true;
        isVulnerable = false;
    }

    // Constructeur avec équipe, direction, et position
    public djed(String team, int direction, int x, int y) {
        super("Djed", team, direction, x, y);
        isReflective = true;
        isVulnerable = false;
    }

    @Override
    public int getNewDirection(int laserDirection) {
        if (laserDirection < 0 || laserDirection > 270 || laserDirection % 90 != 0) {
            return -1; // Valeur invalide
        }
        return DIRECTION_MAP[direction / 90][laserDirection / 90];
    }

    @Override
    public String toString() {
        return name + " (" + direction + "°) at [" + xPos + ", " + yPos + "] - Team: " + team;
    }
}
