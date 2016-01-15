package org.yecq.goleek.server.service.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.yecq.baseframework.plain.core.Root;
import org.yecq.baseframework.test.Base;

/**
 *
 * @author yecq
 */
public class PositionFuturesTest extends Base {

    @Test
    public void test_PositionFutures() {
        this.expectedEx.expect(IllegalStateException.class);
        this.expectedEx.expectMessage("记录不存在");
        new PositionFutures("24");
    }

    @Test
    public void test1_PositionFutures() {
        this.expectedEx.expect(IllegalArgumentException.class);
        this.expectedEx.expectMessage("abcd");
        Map hv = new HashMap();
        hv.put("abcd", "22");
        new PositionFutures(hv);
    }

    @Test
    public void test_add() {
        Map hv = new HashMap();
        hv.put("action", "卖出平仓 <=");
        hv.put("quit_price", 3500);
        PositionFutures p = new PositionFutures(hv);
        String id = p.add();
        assertThat(id, notNullValue());
    }

    @Test
    public void test_remove() {
        String stmt = "select * from position_futures where id=1";
        List list = Root.getInstance().getSqlOperator().query(stmt);
        assertThat(list.size(), is(1));
        PositionFutures p = new PositionFutures("1");
        p.remove();
        stmt = "select * from position_futures where id=1";
        list = Root.getInstance().getSqlOperator().query(stmt);
        assertThat(list.size(), is(0));
    }

    @Test
    public void test_getInfo() {
        PositionFutures p = new PositionFutures("1");
        Map<String, Object> map = p.getInfo();
        assertThat(map.get("id") + "", is("1"));
        assertThat(map.get("contract") + "", is("rb1601"));
        assertThat(map.get("direct") + "", is("多"));
        assertThat(Integer.parseInt(map.get("lot") + ""), is(2));
        assertThat(Double.parseDouble(map.get("open_price") + ""), closeTo(3050, 0.1));
        assertThat(map.get("action") + "", is("卖出平仓 >="));
        assertThat(Double.parseDouble(map.get("quit_price") + ""), closeTo(2910, 0.1));
        assertThat(map.get("account") + "", is("100003109"));
        assertThat(map.get("open_date") + "", is("2015-09-01"));
    }

    @Test
    public void test1_getInfo() {
        PositionFutures p = new PositionFutures("1");
        assertThat(p.getInfo("id") + "", is("1"));
        assertThat(p.getInfo("contract") + "", is("rb1601"));
        assertThat(p.getInfo("direct") + "", is("多"));
        assertThat(Integer.parseInt(p.getInfo("lot") + ""), is(2));
        assertThat(Double.parseDouble(p.getInfo("open_price") + ""), closeTo(3050, 0.1));
        assertThat(p.getInfo("action") + "", is("卖出平仓 >="));
        assertThat(Double.parseDouble(p.getInfo("quit_price") + ""), closeTo(2910, 0.1));
        assertThat(p.getInfo("account") + "", is("100003109"));
        assertThat(p.getInfo("open_date") + "", is("2015-09-01"));
    }

    @Test
    public void test_exist() {
        String contract = "rb1601";
        String direct = "多";
        String account = "100003109";
        Map map = new HashMap();
        map.put("contract", contract);
        map.put("direct", direct);
        map.put("account", account);
        PositionFutures p = new PositionFutures(map);
        String id = p.exist();
        assertThat(id, is("1"));
    }

    @Test
    public void test1_exist() {
        String contract = "rb1601";
        String direct = "多";
        String account = "290334";
        Map map = new HashMap();
        map.put("contract", contract);
        map.put("direct", direct);
        map.put("account", account);
        PositionFutures p = new PositionFutures(map);
        String id = p.exist();
        assertThat(id, nullValue());
    }

    @Test
    public void test2_exist() {
        String contract = "rb1601";
        String account = "390430";
        Map map = new HashMap();
        map.put("contract", contract);
        map.put("account", account);
        this.expectedEx.expect(IllegalArgumentException.class);
        this.expectedEx.expectMessage("需要合约名、方向、账户");
        PositionFutures p = new PositionFutures(map);
        String id = p.exist();
    }

    @Test
    public void test_hasOpeningDetail() {
        PositionFutures p = new PositionFutures("1");
        assertThat(p.hasOpeningDetails(), is(true));
    }

    @Test
    public void test_getDetails() {
        PositionFutures p = new PositionFutures("1");
        String[] ids = p.getDetails();
        assertThat(ids.length, is(3));
        assertThat(ids[0], is("1"));
        assertThat(ids[1], is("2"));
        assertThat(ids[2], is("3"));
    }

    @Test
    public void test_setActionAndQuitPrice() {
        PositionFutures p = new PositionFutures("1");
        p.setActionAndQuitPrice("卖出平仓 <=", 2800);
        assertThat(p.getInfo("action") + "", is("卖出平仓 <="));
        assertThat(Double.parseDouble(p.getInfo("quit_price") + ""), closeTo(2800, 0.1));
    }

    @Test
    public void test_getOpeningDetails() {
        PositionFutures p = new PositionFutures("1");
        String[] ids = p.getOpeningDetails();
        assertThat(ids.length, is(2));
        assertThat(ids[0], is("1"));
        assertThat(ids[1], is("2"));
    }

    @Test
    public void test_delete() {
        String stmt = "select * from position_futures where id =1";
        List list = Root.getInstance().getSqlOperator().query(stmt);
        assertThat(list.size(), is(1));
        stmt = "select * from detail_futures where id=1 or id=2 or id=3";
        list = Root.getInstance().getSqlOperator().query(stmt);
        assertThat(list.size(), is(3));

        PositionFutures p = new PositionFutures("1");
        p.delete();

        stmt = "select * from position_futures where id =1";
        list = Root.getInstance().getSqlOperator().query(stmt);
        assertThat(list.isEmpty(), is(true));
        stmt = "select * from detail_futures where id=1 or id=2 or id=3";
        list = Root.getInstance().getSqlOperator().query(stmt);
        assertThat(list.isEmpty(), is(true));
    }

    @Test
    public void test_open() {
        PositionFutures p = new PositionFutures();
        String contract = "ta1610";
        String direct = "空";
        int lot = 3;
        double open_price = 5200;
        String open_date = "205-9-24";
        double quit_price = 5300;
        String account = "123456";
        String id = p.open(contract, direct, lot, open_price, open_date, quit_price, account);
        assertThat(id, not(nullValue()));
//        String stmt = "select * from detail_futures where contract=?";
//        List list = Root.getInstance().getSqlOperator().query(stmt, new Object[]{"ta1610"});
//        assertThat(list.size(), is(3));
//        stmt = "select * from position_detail_futures where position_futures_id=?";
//        list = Root.getInstance().getSqlOperator().query(stmt, new Object[]{id});
//        assertThat(list.size(), is(3));

        p = new PositionFutures(id);
        assertThat(p.getInfo("id") + "", is(id));
        assertThat(p.getInfo("contract") + "", is("ta1610"));
        assertThat(p.getInfo("direct") + "", is("空"));
        assertThat(Integer.parseInt(p.getInfo("lot") + ""), is(3));
        assertThat(Double.parseDouble(p.getInfo("open_price") + ""), closeTo(5200, 0.1));
        assertThat(p.getInfo("action") + "", is("买入平仓 >="));
        assertThat(Double.parseDouble(p.getInfo("quit_price") + ""), closeTo(5300, 0.1));
        assertThat(p.getInfo("account") + "", is("123456"));
    }

    @Test
    public void test_close() {
        PositionFutures p = new PositionFutures("1");
        assertThat(Integer.parseInt(p.getInfo("lot") + ""), is(2));
        String[] ids = p.getOpeningDetails();
        assertThat(ids.length, is(2));
        assertThat(ids[0], is("1"));
        assertThat(ids[1], is("2"));
        double close_price = 3210;
        String close_date = "2015-10-4";
        p.close(1, close_price, close_date);

        assertThat(Integer.parseInt(p.getInfo("lot") + ""), is(1));
        ids = p.getOpeningDetails();
        assertThat(ids.length, is(1));
        assertThat(ids[0], is("2"));
    }

    @Test
    public void test1_close() {
        PositionFutures p = new PositionFutures("1");
        double close_price = 3210;
        String close_date = "2015-10-4";
        p.close(2, close_price, close_date);

        String stmt = "select * from position_futures where id=1";
        List<Map<String, Object>> list = Root.getInstance().getSqlOperator().query(stmt);
        assertThat(list.size(), is(0));
        stmt = "select * from position_detail_futures where position_futures_id=1";
        list = Root.getInstance().getSqlOperator().query(stmt);
        assertThat(list.size(), is(0));
        stmt = "select * from detail_futures where id=1 or id=2 or id=3";
        list = Root.getInstance().getSqlOperator().query(stmt);
        assertThat(list.size(), is(3));
        assertThat(list.get(0).get("status") + "", is("平"));
        assertThat(Double.parseDouble(list.get(0).get("close_price") + ""), closeTo(3210, 0.1));
        assertThat(list.get(0).get("close_date") + "", is("2015-10-04"));
        assertThat(list.get(1).get("status") + "", is("平"));
        assertThat(Double.parseDouble(list.get(1).get("close_price") + ""), closeTo(3210, 0.1));
        assertThat(list.get(1).get("close_date") + "", is("2015-10-04"));
    }

    @Test
    public void test_do_isReady2Close() {
        String open_date = "2015-11-2";
        PositionFutures p = new PositionFutures();
        String contract = "ta1610";
        String direct = "多";
        int lot = 3;
        double open_price = 6600;
        double quit_price = 6500;
        String account = "123456";
        String id = p.open(contract, direct, lot, open_price, open_date, quit_price, account);
        assertThat(id, not(nullValue()));
        p = new PositionFutures(id);
        assertThat(p.getInfo("open_date") + "", is("2015-11-02"));
        assertThat(p.do_isReady2Close(open_date, "2015-11-4"), is(false));
        assertThat(p.do_isReady2Close(open_date, "2015-11-5"), is(true));
        assertThat(p.do_isReady2Close(open_date, "2015-11-6"), is(true));
    }

    @Test
    public void test1_do_isReady2Close() {
        String open_date = "2015-11-4";
        PositionFutures p = new PositionFutures();
        String contract = "ta1610";
        String direct = "多";
        int lot = 3;
        double open_price = 6600;
        double quit_price = 6500;
        String account = "123456";
        String id = p.open(contract, direct, lot, open_price, open_date, quit_price, account);
        assertThat(id, not(nullValue()));
        p = new PositionFutures(id);
        assertThat(p.getInfo("open_date") + "", is("2015-11-04"));
        assertThat(p.do_isReady2Close(open_date, "2015-11-6"), is(false));
        assertThat(p.do_isReady2Close(open_date, "2015-11-7"), is(false));
        assertThat(p.do_isReady2Close(open_date, "2015-11-8"), is(false));
        assertThat(p.do_isReady2Close(open_date, "2015-11-9"), is(true));
    }
}
