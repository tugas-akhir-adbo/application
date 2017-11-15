/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import model.GameObject;
import model.Player;

/**
 *
 * @author i16072
 */
public class MyCharacterControl extends CharacterControl {

    GameObject objRep;

    /**
     *
     * @param shape
     * @param stepHeight
     */
    public MyCharacterControl(CollisionShape shape, float stepHeight) {
        super(shape, stepHeight);
    }

    /**
     *
     */
    @Override
    public void jump() {
        objRep.setVy(jumpSpeed);
//        }
    }

    @Override
    public void update(float tpf) {
        if (this.objRep.getVy() != 0) {
            this.objRep.setVy(this.objRep.getVy() - getGravity() * tpf);
            this.objRep.setY(this.objRep.getY() + this.objRep.getVy() * tpf);
        }

        if (this.spatial.getLocalTranslation().y < this.objRep.getHeight()/2) {
            this.objRep.setY((this.objRep.getHeight()/2)+7);
            this.objRep.setVy(0);

            ((Player) objRep).setJumping(false);
        }

        this.spatial.setLocalTranslation(this.objRep.getX(), this.objRep.getY(), this.objRep.getZ());
    }

    /**
     *
     * @param obj
     */
    public void setPlayer(GameObject obj) {
        this.objRep = obj;
    }

}
