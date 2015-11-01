/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataStrucutres;

/**
 *
 * @author Lorenzo
 */
public class Update {

    State state;
    Info info;

    /**
     *
     * @param state
     * @param info
     */
    public Update(State state, Info info) {
        this.state = state;
        this.info = info;
    }

    public State getState() {
        return state;
    }

    public Info getInfo() {
        return info;
    }

}
