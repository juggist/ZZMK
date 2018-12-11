package com.juggist.baseandroid.eventbus;

/**
 * @author juggist
 * @date 2018/12/11 5:30 PM
 */
public class DiscountCardEvent {
    public static class UseDiscountCard{
        private String unlimit;

        public UseDiscountCard(String unlimit) {
            this.unlimit = unlimit;
        }

        public String getUnlimit() {
            return unlimit;
        }
    }
}
