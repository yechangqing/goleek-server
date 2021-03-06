package org.yecq.goleek.server.service.bean.param;

/**
 *
 * @author yecq
 */
public class FuturesAddBean {

    private String code;
    private String name;
    private double margin;
    private int unit;
    private double min;
    private String exchange;

    public FuturesAddBean() {
    }

    public FuturesAddBean(String code, String name, double margin, int unit, double min, String exchange) {
        this.code = code;
        this.name = name;
        this.margin = margin;
        this.unit = unit;
        this.min = min;
        this.exchange = exchange;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
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

    public double getMargin() {
        return margin;
    }

    public void setMargin(double margin) {
        this.margin = margin;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

}
