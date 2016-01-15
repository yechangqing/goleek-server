package org.yecq.goleek.server.service.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yecq.baseframework.plain.core.CoreSelector;
import org.yecq.baseframework.plain.core.Root;

/**
 *
 * @author yecq
 */
@Component
public final class ObjectCreator {

    @Autowired
    private CoreSelector sel;

    ObjectCreator() {
    }

    public List<Futures> getFuturesList(String condition) {
        return sel.getList(condition, new String[]{"exchange", "code"}, Futures.class);
    }

    public List<Futures> getFuturesListAll() {
        return getFuturesList(null);
    }

    public List<Futures> getFuturesListInterested() {
        return getFuturesList("interest='y'");
    }

    public String[] getFuturesExchangeNames() {
        return new String[]{"上海期货", "大连商品", "郑州商品"};
    }

    public List<Stock> getStockList(String condition) {
        return sel.getList(condition, new String[]{"exchange", "code"}, Stock.class);
    }

    public List<Stock> getStockListAll() {
        return getStockList(null);
    }

    public List<Stock> getStockListInterested() {
        return getStockList("interest='y'");
    }

    public String[] getStockExchangeNames() {
        return new String[]{"上海证券", "深圳证券"};
    }

    public List<Account> getAccountList(String condition) {
        return sel.getList(condition, new String[]{"type", "company"}, Account.class);
    }

    public List<Account> getAccountListUsed() {
        return getAccountList("used='y'");
    }

    public List<Account> getAccountListFutures() {
        return getAccountList("type='期货'");
    }

    public List<Account> getAccountListFuturesUsed() {
        return getAccountList("type='期货' and used='y'");
    }

    public List<Account> getAccountListStock() {
        return getAccountList("type='股票'");
    }

    public List<Account> getAccountListStockUsed() {
        return getAccountList("type='股票' and used='y'");
    }

    public Setting getDefaultTradeSetting() {
        return Root.getInstance().getBean(Setting.class);
    }

    public List<PositionFutures> getPositionFuturesList(String condition) {
        List<Map<String, Object>> list = sel.getList(condition, new String[]{"id"}, SaeViewSubstitute.v_position_futures);
        List<PositionFutures> ret = new LinkedList();
        int len = list.size();
        for (int i = 0; i < len; i++) {
            String id = list.get(i).get("id") + "";
            ret.add(new PositionFutures(id));
        }
        return ret;
    }

    public List<PositionFutures> getPositionFuturesListAll() {
        return getPositionFuturesList(null);
    }

    public String[] getPositionFuturesActions(String direct) {
        if (direct.equals("多")) {
            return new String[]{"卖出平仓 <=", "卖出平仓 >="};
        } else if (direct.equals("空")) {
            return new String[]{"买入平仓 >=", "买入平仓 <="};
        } else {
            throw new IllegalStateException("错误的方向值");
        }
    }

    public List<PositionStock> getPositionStockList(String condition) {
        List<Map<String, Object>> list = sel.getList(condition, new String[]{"id"}, SaeViewSubstitute.v_position_stock);
        List<PositionStock> ret = new LinkedList();
        int len = list.size();
        for (int i = 0; i < len; i++) {
            String id = list.get(i).get("id") + "";
            ret.add(new PositionStock(id));
        }
        return ret;
    }

    public List<PositionStock> getPositionStockListAll() {
        return getPositionStockList(null);
    }

    public String[] getPositionStockActions() {
        return new String[]{"卖出 <=", "卖出 >="};
    }
}
