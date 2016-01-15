package org.yecq.goleek.server.service.bean.param;

/**
 *
 * @author yecq
 */
public class PositionFuturesActionsBean {

    private String direct;

    public PositionFuturesActionsBean() {
    }

    public PositionFuturesActionsBean(String direct) {
        this.direct = direct;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

}
