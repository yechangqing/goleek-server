package org.yecq.goleek.server.web.controller;

import com.google.gson.Gson;
import com.jhhc.baseframework.test.IntegrateRestfulBase;
import com.jhhc.baseframework.test.TestReturn;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.yecq.goleek.server.service.bean.param.PositionStockCloseBean;
import org.yecq.goleek.server.service.bean.param.PositionStockEditBean;
import org.yecq.goleek.server.service.bean.param.PositionStockOpenBean;
import org.yecq.goleek.server.service.bean.result.PositionStockInfoBean;

/**
 *
 * @author yecq
 */
public class PositionStockControllerTest extends IntegrateRestfulBase {

    @Test
    public void test_do_getListAll() {
        TestReturn ret = doGet("/position_stocks");
        assertThat(ret.getStatus(), is("ok"));
        List<PositionStockInfoBean> data = ret.getObject4List(PositionStockInfoBean.class);
        assertThat(data.size(), is(3));
    }

    @Test
    public void test_do_modify() {
        PositionStockEditBean bean = new PositionStockEditBean("卖出 >=", 31);
        Map map = new HashMap();
        map.put("json", new Gson().toJson(bean));
        map.put("type", "edit");
        TestReturn ret = doPut("/position_stocks/1", map);
        assertThat(ret.getStatus(), is("ok"));
    }

    @Test
    public void tes1_do_modify() {
        PositionStockEditBean bean = new PositionStockEditBean("卖出 >=", 31);
        Map map = new HashMap();
        map.put("json", new Gson().toJson(bean));
        map.put("type", "abcd");
        TestReturn ret = doPut("/position_stocks/1", map);
        assertThat(ret.getStatus(), is("error"));
        assertThat(ret.getMessage(), is("错误的type值"));
    }

    @Test
    public void test_do_open() {
        PositionStockOpenBean bean = new PositionStockOpenBean();
        bean.setCode("600399");
        bean.setLot(2);
        bean.setOpen_price(42);
        bean.setOpen_date("2015-11-13");
        bean.setQuit_price(40);
        bean.setAccount("27107470");
        Map map = new HashMap();
        map.put("json", new Gson().toJson(bean));
        TestReturn ret = doPost("/position_stocks", map);
        assertThat(ret.getStatus(), is("ok"));
        String id = ret.getObject(String.class);
        assertThat(id, is("1"));
    }

    @Test
    public void test_do_close() {
        PositionStockCloseBean bean = new PositionStockCloseBean(2, 56, "2015-10-20");
        Map map = new HashMap();
        map.put("type", "close");
        map.put("json", new Gson().toJson(bean));
        TestReturn ret = doPut("/position_stocks/1", map);
        assertThat(ret.getStatus(), is("ok"));
    }

    @Test
    public void test_do_delete() {
        TestReturn ret = doDelete("/position_stocks/1");
        assertThat(ret.getStatus(), is("ok"));
    }

    @Test
    public void test_do_getActions() {
        TestReturn ret = doGet("/position_stocks_actions");
        assertThat(ret.getStatus(), is("ok"));
        String[] accs = ret.getObject(String[].class);
        assertThat(accs.length, is(2));
        assertThat(accs[0], is("卖出 <="));
        assertThat(accs[1], is("卖出 >="));
    }
}
