package com.juggist.baseandroid.eventbus;

/**
 * @author juggist
 * @date 2018/12/11 5:30 PM
 */
public class DiscountCardEvent {
    public static class UseDiscountCard{
        private String unlimit;
        private int position;

        public UseDiscountCard(String unlimit,int position) {
            this.unlimit = unlimit;
            this.position = position;
        }

        public int getPosition() {
            return position;
        }

        public String getUnlimit() {
            return unlimit;
        }
    }
}
