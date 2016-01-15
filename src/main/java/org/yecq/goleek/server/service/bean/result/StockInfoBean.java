package org.yecq.goleek.server.service.bean.result;

/**
 *
 * @author yecq
 */
public class StockInfoBean {

    private String id;
    private String code;
    private String name;
    private String exchange;
    private String interest;

    public StockInfoBean() {
    }

    public StockInfoBean(String id, String code, String name, String exchange, String interest) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.exchange = exchange;
        this.interest = interest;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
}
