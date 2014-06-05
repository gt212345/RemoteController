package com.hrw.remotecontroller.activities;

import java.io.Serializable;

public class Choices implements Serializable{
     
    /**
	 * 
	 */
	private int key;
 
    public Choices(int key) {
        super();
        this.key = key;
    }
 
    public int getKey() {
        return key;
    }
 
    public void setKey(int key) {
        this.key = key;
    }
     
}