package com.badlogic.angrybirds;

public class Constants {
    public static final short CATEGORY_BIRD = 0x0004;
    public static final short CATEGORY_CATAPULT = 0x0002;
    public static final short MASK_BIRD = ~CATEGORY_CATAPULT; // Collide with everything except catapult
    public static final short MASK_CATAPULT = ~CATEGORY_BIRD; // Collide with everything except bird
}
