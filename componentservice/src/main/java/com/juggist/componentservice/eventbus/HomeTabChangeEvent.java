package com.juggist.componentservice.eventbus;

/**
 * @author juggist
 * @date 2018/11/12 11:44 AM
 */
public class HomeTabChangeEvent {

    public static class TabChange{
        int position;
        public TabChange(int position){
            this.position = position;
        }

        public int getPosition() {
            return position;
        }
    }

}
