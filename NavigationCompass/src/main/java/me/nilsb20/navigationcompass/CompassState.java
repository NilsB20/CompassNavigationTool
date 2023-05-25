package me.nilsb20.navigationcompass;

import org.jetbrains.annotations.NotNull;

public enum CompassState {

    C_0('\uE00E',0),
    C_1('\uE00F',1),
    C_2('\uE010',2),
    C_3('\uE011',3),
    C_4('\uE012',4),
    C_5('\uE013',5),
    C_6('\uE014',6),
    C_7('\uE015',7),
    C_8('\uE016',8),
    C_9('\uE017',9),
    C_10('\uE018',10),
    C_11('\uE019',11),
    C_12('\uE01A',12),
    C_13('\uE01B',13),
    C_14('\uE000',14),
    C_15('\uE001',-13),
    C_16('\uE002',-12),
    C_17('\uE003',-11),
    C_18('\uE004',-10),
    C_19('\uE005',-9),
    C_20('\uE006',-8),
    C_21('\uE007',-7),
    C_22('\uE008',-6),
    C_23('\uE009',-5),
    C_24('\uE00A',-4),
    C_25('\uE00B',-3),
    C_26('\uE00C',-2),
    C_27('\uE00D',-1),
    ;

    private final Character code;
    private final float angle;
    private final int number;

    CompassState(Character code, int number) {
        this.code = code;
        this.angle = 12.85714285714286F*number;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public Character getCode() {
        return code;
    }

    public float getAngle() {
        return angle;
    }

    /**
     * Get a certain compass state
     * @param stade the state
     * @return  the compasssate
     */
    public static @NotNull CompassState getStade(int stade) {
        for(CompassState compassState : CompassState.values()) {
            if(compassState.getNumber() == stade) {
                return compassState;
            }
        }
        return CompassState.C_0;
    }

    /**
     * Get the compasssate that is the closest to a angle
     * @param angle the angle
     * @return  the compassate
     */
    public static @NotNull CompassState getCompassFromAngle(float angle) {
        CompassState returnState = null;
        float closest = 500;

        for(CompassState state : CompassState.values()) {
            // Check which should be subtracted from who
            float closeTo0;
            if(state.getAngle() > angle) {
                closeTo0 = state.getAngle()-angle;
            } else {
                closeTo0 = angle - state.getAngle();
            }

            // Check if the oldest or the new value is closer to 0
            if(closeTo0 < closest) {
                returnState = state;
                closest = closeTo0;
            }
        }
        if(returnState == null) {
            returnState = CompassState.C_0;
        }

        return returnState;
    }
}
