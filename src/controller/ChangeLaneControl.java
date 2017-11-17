/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 * Interface for change lane's controller
 * @author i16012
 */
public interface ChangeLaneControl {
    /**
     * Method to control the character to change lane
     * @param lane the lane the player wants
     */
    void changeLane(byte lane);
}
