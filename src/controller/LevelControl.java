package controller;

/**
 * This is interface for level's controller
 * This interface has method to level up and restart the level
 * @author i16012 - Apsari Ayusya Cantika
 * @since 
 * @version 
 */
public interface LevelControl {
    /**
     * Method to level up the character
     */
    void levelUp();
    
    /**
     * Method to restart the character's level
     */
    void restartLevel();
}
