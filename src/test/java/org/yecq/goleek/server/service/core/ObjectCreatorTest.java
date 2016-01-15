package org.yecq.goleek.server.service.core;

import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.yecq.baseframework.test.Base;

/**
 *
 * @author yecq
 */
public class ObjectCreatorTest extends Base {

    @Autowired
    private ObjectCreator objs;

    @Test
    public void test_getFuturesList() {
        List<Futures> list = objs.getFuturesList(null);
        assertThat(list.size(), is(3));
    }

    @Test
    public void test1_getFuturesList() {
        List<Futures> list = objs.getFuturesList("code='rb1601'");
        assertThat(list.size(), is(1));
    }

    @Test
    public void test_getFuturesListAll() {
        List<Futures> list = objs.getFuturesListAll();
        assertThat(list.size(), is(3));
        assertThat(list.get(0).getInfo("code") + "", is("rb1601"));
        assertThat(list.get(1).getInfo("code") + "", is("y1605"));
        assertThat(list.get(2).getInfo("code") + "", is("ta1610"));
    }

    @Test
    public void test_getFuturesListInterested() {
        List<Futures> list = objs.getFuturesListInterested();
        assertThat(list.size(), is(2));
        assertThat(list.get(0).getInfo("code") + "", is("y1605"));
        assertThat(list.get(1).getInfo("code") + "", is("ta1610"));
    }

    @Test
    public void test_getFuturesExchangeNames() {
        String[] names = objs.getFuturesExchangeNames();
        assertThat(names.length, is(3));
        assertThat(names[0], is("上海期货"));
        assertThat(names[1], is("大连商品"));
        assertThat(names[2], is("郑州商品"));
    }

    @Test
    public void test_getStockList() {
        List<Stock> list = objs.getStockList(null);
        assertThat(list.size(), is(2));
        assertThat(list.get(0).getInfo("code") + "", is("600399"));
        assertThat(list.get(1).getInfo("code") + "", is("000123"));
    }

    @Test
    public void test1_getStockList() {
        List<Stock> list = objs.getStockList("code='600399'");
        assertThat(list.size(), is(1));
        assertThat(list.get(0).getInfo("code") + "", is("600399"));
    }

    @Test
    public void test_getStockListAll() {
        List<Stock> list = objs.getStockListAll();
        assertThat(list.size(), is(2));
        assertThat(list.get(0).getInfo("code") + "", is("600399"));
        assertThat(list.get(1).getInfo("code") + "", is("000123"));
    }

    @Test
    public void test_getStockListInterested() {
        List<Stock> list = objs.getStockListInterested();
        assertThat(list.size(), is(1));
        assertThat(list.get(0).getInfo("code") + "", is("600399"));
    }

    @Test
    public void test_getStockExchangeNames() {
        String[] names = objs.getStockExchangeNames();
        assertThat(names.length, is(2));
        assertThat(names[0], is("上海证券"));
        assertThat(names[1], is("深圳证券"));
    }

    @Test
    public void test_getAccountList() {
        List<Account> list = objs.getAccountList("used='y'");
        assertThat(list.size(), is(2));
        assertThat(list.get(0).getInfo("code") + "", is("100003109"));
        assertThat(list.get(1).getInfo("code") + "", is("27107470"));
    }

    @Test
    public void test_getAccountListUsed() {
        List<Account> list = objs.getAccountListUsed();
        assertThat(list.size(), is(2));
        assertThat(list.get(0).getInfo("code") + "", is("100003109"));
        assertThat(list.get(1).getInfo("code") + "", is("27107470"));
    }

    @Test
    public void test_getAccountListFutures() {
        List<Account> list = objs.getAccountListFutures();
        assertThat(list.size(), is(2));
    }

    @Test
    public void test_getAccountListFuturesUsed() {
        List<Account> list = objs.getAccountListFuturesUsed();
        assertThat(list.size(), is(1));
        assertThat(list.get(0).getInfo("code") + "", is("100003109"));
    }

    @Test
    public void test_getAccountListStock() {
        List<Account> list = objs.getAccountListStock();
        assertThat(list.size(), is(1));
    }

    @Test
    public void test_getAccountListStockUsed() {
        List<Account> list = objs.getAccountListStock();
        assertThat(list.size(), is(1));
    }

    @Test
    public void test_getDefaultTradeSetting() {
        Setting set = objs.getDefaultTradeSetting();
        assertThat(set.getId(), is("1"));
        assertThat(Double.parseDouble(set.getInfo("open_percent") + ""), closeTo(0.25, 0.001));
        assertThat(Double.parseDouble(set.getInfo("loss_percent") + ""), closeTo(0.02, 0.001));
    }

    @Test
    public void test_getPositionFuturesList() {
        List<PositionFutures> list = objs.getPositionFuturesList("account='100003109'");
        assertThat(list.size(), is(2));
    }

    @Test
    public void test_getPositionFuturesListAll() {
        List<PositionFutures> list = objs.getPositionFuturesListAll();
        assertThat(list.size(), is(3));
    }

    @Test
    public void test_getPositionFuturesActions() {
        String[] act = objs.getPositionFuturesActions("多");
        assertThat(act[0], is("卖出平仓 <="));
        assertThat(act[1], is("卖出平仓 >="));
        act = objs.getPositionFuturesActions("空");
        assertThat(act[0], is("买入平仓 >="));
        assertThat(act[1], is("买入平仓 <="));
    }

    @Test
    public void test_getPositionStockList() {
        List<PositionStock> list = objs.getPositionStockList("account='27107470'");
        assertThat(list.size(), is(2));
        assertThat(list.get(0).getId(), is("1"));
        assertThat(list.get(1).getId(), is("2"));
    }

    @Test
    public void test_getPositionStockListAll() {
        List<PositionStock> list = objs.getPositionStockListAll();
        assertThat(list.size(), is(3));
        assertThat(list.get(0).getId(), is("1"));
        assertThat(list.get(1).getId(), is("2"));
        assertThat(list.get(2).getId(), is("3"));
    }
}
