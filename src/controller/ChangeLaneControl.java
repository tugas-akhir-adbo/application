package controller;

/**
 * This is interface for change lane's controller
 * This interface has method for changing lane
 * @author i16012 - Apsari Ayusya Cantika
 * @since
 * @version 
 */
public interface ChangeLaneControl {
   /**
     * Method to control the character to change lane
     * @param lane the lane the player wants
     */
    void changeLane(byte lane);
}
