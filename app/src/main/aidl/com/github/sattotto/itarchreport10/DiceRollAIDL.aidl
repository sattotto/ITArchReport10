// DiceRollAIDL.aidl
package com.github.sattotto.itarchreport10;

// Declare any non-default types here with import statements

interface DiceRollAIDL {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    int getDiceRoll();

    void setDiceType(int count, int type);

    int[] getDiceRollResult();

    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
