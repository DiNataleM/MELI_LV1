package com.meli.lv1.service;

import com.meli.lv1.utils.CountHelper;

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * Level-1
 * En donde recibirás como parámetro un array de Strings que representan cada fila de una tabla
 * de (NxN) con la secuencia del ADN. Las letras de los Strings solo pueden ser: (A,T,C,G), las
 * cuales representa cada base nitrogenada del ADN
 * <p>
 * Sabrás si un humano es mutante, si encuentras más de una secuencia de cuatro letras
 * iguales, de forma oblicua, horizontal o vertical
 */
public class MutantChecker {
    public static final int MAX_SEQUENCE = 4;
    public static final int SEQUENCES_TO_FIND = 2;

    private CountHelper hCount;
    private CountHelper vCount;
    private CountHelper oDRightCount;
    private CountHelper oDLeftCount;
    private CountHelper oTRightCount;
    private CountHelper oTLeftCount;

    public MutantChecker() {
        hCount = new CountHelper(MAX_SEQUENCE);
        vCount = new CountHelper(MAX_SEQUENCE);
        oDRightCount = new CountHelper(MAX_SEQUENCE);
        oDLeftCount = new CountHelper(MAX_SEQUENCE);
        oTRightCount = new CountHelper(MAX_SEQUENCE);
        oTLeftCount = new CountHelper(MAX_SEQUENCE);
    }

    /**
     * Return true if the dna is from a mutant.
     *
     * @param dna
     * @return
     */
    public boolean isMutant(String[] dna) {
        validateDnaParameter(dna);

        // Dna is NXN. so, it is a square. i can use same size.
        int size = dna.length;

        // In this case can not exist MAX_SEQUENCE letters in sequence.
        if (size < MAX_SEQUENCE) {
            return false;
        }

        for (int c = 0; c < size; c++) {//Each c
            resetLoop();
            for (int l = 0; l < size; l++) {

                search(dna, c, l);

                if (getTotalCoincidence() >= SEQUENCES_TO_FIND) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * search in all directions.
     *
     * @param dna
     * @param column
     * @param letter
     */
    private void search(String[] dna, int column, int letter) {
        horizontalSearch(dna, column, letter);
        verticalSearch(dna, column, letter);
        obliqueToDownSearch(dna, column, letter);
        obliqueToUpSearch(dna, column, letter);

    }

    /**
     * The horizontal search get each column and search in all string, it doesn't repeat letter.
     * <p>
     * → H H H H
     * → H H H H
     * → H H H H
     * → H H H H
     *
     * @param dna
     * @param col
     * @param let
     */
    private void horizontalSearch(String[] dna, int col, int let) {
        hCount.check(dna[col].charAt(let));
    }

    /**
     * The vertical Search use each column and search in all string, it doesn't repeat letter.
     * ↓ ↓ ↓ ↓
     * V V V V
     * V V V V
     * V V V V
     * V V V V
     *
     * @param dna
     * @param col
     * @param let
     */
    private void verticalSearch(String[] dna, int col, int let) {
        vCount.check(dna[let].charAt(col));
    }

    /**
     * The oblique to Down search, start in the first row and go in oblique direction to down,
     * one for Right (R) and one for Left (L).
     * Like it start to the top it is going to travel all possibility.
     * <p>
     * 🡦 🡦 🡦 🡦 🡦    🡧 🡧 🡧 🡧 🡧
     * R R R R R R | L L L L L L
     * . R R R R R | L L L L L .
     * . . R R R R | L L L L . .
     * . . . R R R | L L L . . .
     * . . . . R R | L L . . . .
     * . . . . . R | L . . . . .
     *
     * @param dna
     * @param col
     * @param let
     */
    private void obliqueToDownSearch(String[] dna, int col, int let) {
        int size = dna.length;

        if (col + let < size) { // Check Right
            oDRightCount.check(dna[let].charAt(col + let));
        }

        if (col - let >= 0) { // Check Left
            oDLeftCount.check(dna[let].charAt(col - let));
        }

    }

    /**
     * The oblique to Up search, will compete the sequence that obliqueToDownSearch method could not see.
     * It start in the last row and go in oblique direction to Up, one for Right (R) and one for Left (L).
     * <p>
     * . . . . . . | . . . . . .
     * . . . . . R | L . . . . .
     * . . . . R R | L L . . . .
     * . . . R R R | L L L . . .
     * . . R R R R | L L L L . .
     * . R R R R . | . L L L L .
     * 🡥 🡥 🡥 🡥 🡥    🡤 🡤 🡤 🡤 🡤
     *
     * @param dna
     * @param col
     * @param let
     */
    private void obliqueToUpSearch(String[] dna, int col, int let) {
        // the first and the last was checked for the obliqueToDownSearch method
        if (col == 0 || col == (dna.length - 1)) {
            return;
        }

        int size = dna.length;
        if (col + let < size) { // Check right
            oTRightCount.check(dna[size - 1 - let].charAt(col + let));
        }

        if (col - let >= 0) { // Check Left
            oTLeftCount.check(dna[size - 1 - let].charAt(col - let));
        }
    }

    /**
     * Return add the coincidence.
     *
     * @return
     */
    private int getTotalCoincidence() {
        return hCount.getCoincidence() + vCount.getCoincidence() +
                oTRightCount.getCoincidence() + oTLeftCount.getCoincidence() +
                oDRightCount.getCoincidence() + oDLeftCount.getCoincidence();
    }

    private void resetLoop() {
        hCount.resetLoop();
        vCount.resetLoop();
        oDRightCount.resetLoop();
        oDLeftCount.resetLoop();
        oTRightCount.resetLoop();
        oTLeftCount.resetLoop();
    }

    /**
     * Check if dna is not empty|null and if it is NXN
     *
     * @param dna
     */
    private void validateDnaParameter(String[] dna) {

        if (isEmpty(dna)) {
            throw new IllegalArgumentException("The dna parameter is empty.");
        }

        if (dna.length != dna[0].length()) {
            throw new IllegalArgumentException("The dna parameter is not NXN.");
        }
    }

}
