package org.yecq.goleek.server.service.core;

/**
 * 针对sae中不能使用视图的情况，建立视图名称与sql语句的替换
 *
 * @author yecq
 */
public final class SaeViewSubstitute {

    public static final String v_position_futures = "(select d.contract, d.direct, count(d.id) as lot, round(avg(d.open_price),2) as open_price, "
            + "	p.action, round(p.quit_price,2) as quit_price , d.account, min(d.open_date) as open_date, p.id "
            + "from position_detail_futures as pd "
            + "join position_futures as p on p.id=pd.position_futures_id "
            + "join detail_futures as d on d.id = pd.detail_futures_id "
            + "where d.status='持' "
            + "group by contract,direct,account "
            + "order by min(d.open_date), p.id) as vpf";

    public static final String v_position_stock = "(select d.code, d.name, count(d.id) as lot, round(avg(d.open_price),2) as open_price, "
            + "	p.action, round(p.quit_price,2) as quit_price , d.account, min(d.open_date) as open_date, p.id "
            + "from position_detail_stock as pd "
            + "join position_stock as p on p.id = pd.position_stock_id "
            + "join detail_stock as d on d.id = pd.detail_stock_id "
            + "where d.status='持' "
            + "group by account, code "
            + "order by min(d.open_date), p.id) as vps";

}
