package reoger.hut.chatinstant.bean;

/**
 * Created by 24540 on 2017/5/26.
 */

public class Firends {
    private String name;
    private TypeState state;


    public Firends(String name, TypeState state) {
        this.name = name;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeState getState() {
        return state;
    }

    public void setState(TypeState state) {
        this.state = state;
    }
}
