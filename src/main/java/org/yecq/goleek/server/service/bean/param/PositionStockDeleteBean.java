package org.yecq.goleek.server.service.bean.param;

/**
 *
 * @author yecq
 */
public class PositionStockDeleteBean {

    private String id;

    public PositionStockDeleteBean() {
    }

    public PositionStockDeleteBean(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
