package org.yecq.goleek.server.service.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
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
public class PositionStockTest extends Base {

    @Test
    public void test_PositionStock() {
        new PositionStock("1");
    }

    @Test
    public void test2_PositionStock() {
        this.expectedEx.expect(IllegalArgumentException.class);
        this.expectedEx.expectMessage("不存在字段abcd");
        Map hv = new HashMap();
        hv.put("abcd", "22");
        new PositionStock(hv);
    }

    @Test
    public void test1_PositionStock() {
        this.expectedEx.expect(IllegalStateException.class);
        this.expectedEx.expectMessage("记录不存在");
        new PositionStock("6");
    }

    @Test
    public void test_exist() {
        Map hv = new HashMap();
        hv.put("code", "600399");
        hv.put("account", "27107470");
        String id = new PositionStock(hv).exist();
        assertThat(id, notNullValue());
    }

    @Test
    public void test1_exist() {
        Map hv = new HashMap();
        hv.put("code", "600123");
        hv.put("account", "27107470");
        String id = new PositionStock(hv).exist();
        assertThat(id, nullValue());
    }

    @Test
    public void test_open() {
        String code = "000123";
        int lot = 3;
        double open_price = 34;
        String open_date = "2015-10-11";
        double quit_price = 30;
        String account = "123456";
        PositionStock p = new PositionStock();
        String id = p.open(code, lot, open_price, open_date, quit_price, account);
        assertThat(id, notNullValue());

        String stmt = "select * from v_position_stock";
        List list = Root.getInstance().getSqlOperator().query(stmt);
        assertThat(list.size(), is(4));
    }

    @Test
    public void test_close() {
        int lot = 2;
        double price = 54;
        String date = "2015-10-10";
        PositionStock p = new PositionStock("1");
        p.close(lot, price, date);
        String stmt = "select * from v_position_stock";
        List list = Root.getInstance().getSqlOperator().query(stmt);
        assertThat(list.size(), is(2));
    }

    @Test
    public void test1_close() {
        int lot = 1;
        double price = 37;
        String date = "2015-10-10";
        PositionStock p = new PositionStock("2");
        p.close(lot, price, date);
        String stmt = "select * from v_position_stock";
        List list = Root.getInstance().getSqlOperator().query(stmt);
        assertThat(list.size(), is(3));
    }

    @Test
    public void test_delete() {
        PositionStock p = new PositionStock("1");
        p.delete();
        String stmt = "select * from position_stock where id=1";
        List list = Root.getInstance().getSqlOperator().query(stmt);
        assertThat(list.size(), is(0));

        stmt = "select * from detail_stock";
        list = Root.getInstance().getSqlOperator().query(stmt);
        assertThat(list.size(), is(4));
    }

    @Test
    public void test_add() {
        Map hv = new HashMap();
        hv.put("action", "卖出 <=");
        hv.put("quit_price", 3);
        String id = new PositionStock(hv).add();
        assertThat(id, notNullValue());
    }

    @Test
    public void test_remove() {
        new PositionStock("3").delete();
        String stmt = "select * from position_stock where id=3";
        List list = Root.getInstance().getSqlOperator().query(stmt);
        assertThat(list.isEmpty(), is(true));
    }

    @Test
    public void test_getDetails() {
        String[] ids = new PositionStock("1").getDetails();
        assertThat(ids.length, is(3));
    }

    @Test
    public void test_getOpeningDetails() {
        String[] ids = new PositionStock("1").getOpeningDetails();
        assertThat(ids.length, is(2));
        assertThat(ids[0], is("2"));
        assertThat(ids[1], is("3"));
    }

    @Test
    public void test_setActionAndQuitPrice() {
        PositionStock ps = new PositionStock("2");
        ps.setActionAndQuitPrice("卖出 >=", 100);
        String action = ps.getInfo("action") + "";
        double quit_price = Double.parseDouble(ps.getInfo("quit_price") + "");
        assertThat(action, is("卖出 >="));
        assertThat(quit_price, closeTo(100, 0.1));
    }

    @Test
    public void test_getInfo() {
        Map<String, Object> map = new PositionStock("1").getInfo();
        assertThat(map.get("code") + "", is("600399"));
        assertThat(Integer.parseInt(map.get("lot") + ""), is(2));
        assertThat(Double.parseDouble(map.get("open_price") + ""), closeTo(31, 0.1));
        assertThat(map.get("action") + "", is("卖出 <="));
        assertThat(Double.parseDouble(map.get("quit_price") + ""), closeTo(29, 0.1));
        assertThat(map.get("account") + "", is("27107470"));
        assertThat(map.get("open_date") + "", is("2015-05-01"));
    }

    @Test
    public void test1_getInfo() {
        PositionStock p = new PositionStock("1");
        assertThat(p.getInfo("code") + "", is("600399"));
        assertThat(Integer.parseInt(p.getInfo("lot") + ""), is(2));
        assertThat(Double.parseDouble(p.getInfo("open_price") + ""), closeTo(31, 0.1));
        assertThat(p.getInfo("action") + "", is("卖出 <="));
        assertThat(Double.parseDouble(p.getInfo("quit_price") + ""), closeTo(29, 0.1));
        assertThat(p.getInfo("account") + "", is("27107470"));
        assertThat(p.getInfo("open_date") + "", is("2015-05-01"));
    }

    @Test
    public void test_do_isReady2Close() {
        String open_date = "2015-11-2";
        PositionStock p = new PositionStock();
        String code = "000123";
        int lot = 3;
        double open_price = 34;
        double quit_price = 30;
        String account = "123456";
        String id = p.open(code, lot, open_price, open_date, quit_price, account);
        assertThat(id, notNullValue());

        p = new PositionStock(id);
        assertThat(p.getInfo("open_date") + "", is("2015-11-02"));
        assertThat(p.do_isReady2Close(open_date, "2015-11-4"), is(false));
        assertThat(p.do_isReady2Close(open_date, "2015-11-5"), is(true));
        assertThat(p.do_isReady2Close(open_date, "2015-11-6"), is(true));
    }

    @Test
    public void test1_do_isReady2Close() {
        String open_date = "2015-11-4";
        PositionStock p = new PositionStock();
        String code = "000123";
        int lot = 3;
        double open_price = 34;
        double quit_price = 30;
        String account = "123456";
        String id = p.open(code, lot, open_price, open_date, quit_price, account);
        assertThat(id, notNullValue());

        p = new PositionStock(id);
        assertThat(p.getInfo("open_date") + "", is("2015-11-04"));
        assertThat(p.do_isReady2Close(open_date, "2015-11-6"), is(false));
        assertThat(p.do_isReady2Close(open_date, "2015-11-7"), is(false));
        assertThat(p.do_isReady2Close(open_date, "2015-11-8"), is(false));
        assertThat(p.do_isReady2Close(open_date, "2015-11-9"), is(true));
    }
}
