package me.nilsb20.navigationcompass;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class PlayerActionBar {

    private final Player p;
    private final String positiveCharacters = "\uDB00\uDCD2"; // 210 positive sized characters
    private Location loc = null;
    private int currentCompassSateNumber = 0;

    public PlayerActionBar(@NotNull Player p) {
        this.p = p;
    }

    /**
     * Make a compass navigate to a certain location
     * @param loc   the locatoin to navigate to
     */
    public void navigateToLocation(Location loc) {
        this.loc = loc;
    }

    /**
     * Show a updated compass to the player
     */
    public void update() {
        p.sendActionBar(getActionBarString());
    }

    /**
     * Get the string that should be send to the player
     * @return  the string
     */
    private String getActionBarString() {
        return positiveCharacters + getCurrentCompassState().getCode();
    }

    /**
     * Get the current state the compass should be in
     * @return  the compassState
     */
    private CompassState getCurrentCompassState() {
        // If there isn't a location the compass should spin
        if(loc == null) {
            currentCompassSateNumber += 1;
            if(currentCompassSateNumber >= 14) {
                currentCompassSateNumber = -13;
            }
            return CompassState.getStade(currentCompassSateNumber);
        }

        // If compass should point in a direction
        Location playerLoc = p.getLocation().clone();
        Location navigateLoc = loc.clone();

        playerLoc.setY(0);
        playerLoc.setPitch(0);
        navigateLoc.setY(0);
        navigateLoc.setPitch(0);

        Vector playerDirection = playerLoc.getDirection().normalize();
        Vector directionToLook = navigateLoc.toVector().subtract(playerLoc.toVector()).normalize();

        float angle = (float) Math.toDegrees(playerDirection.angle(directionToLook));

        if(checkIfVectorIsToRightOrLeft(playerDirection, directionToLook)) {
            angle = -angle;
        }

        return CompassState.getCompassFromAngle(angle);
    }

    /**
     * Check if a vector is to the right or the left of a other vector
     * @param v1    the first vector
     * @param v2    the second vector
     * @return      a boolean
     */
    private boolean checkIfVectorIsToRightOrLeft(Vector v1, Vector v2) {
        Vector crossProduct = v1.crossProduct(v2);
        double dotProduct = crossProduct.getY();
        return dotProduct > 0;
    }

    public Player getP() {
        return p;
    }

    public Location getLoc() {
        return loc;
    }
}
