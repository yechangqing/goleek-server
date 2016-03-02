package org.yecq.goleek.server.web.controller;

import com.google.gson.Gson;
import com.jhhc.baseframework.test.IntegrateRestfulBase;
import com.jhhc.baseframework.test.TestReturn;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.yecq.goleek.server.service.bean.param.PositionFuturesActionsBean;
import org.yecq.goleek.server.service.bean.param.PositionFuturesCloseBean;
import org.yecq.goleek.server.service.bean.param.PositionFuturesEditBean;
import org.yecq.goleek.server.service.bean.param.PositionFuturesOpenBean;
import org.yecq.goleek.server.service.bean.result.PositionFuturesInfoBean;

/**
 *
 * @author yecq
 */
public class PositionFuturesControllerTest extends IntegrateRestfulBase {

    @Test
    public void test_do_getListAll() {
        TestReturn ret = doGet("/position_futures");
        assertThat(ret.getStatus(), is("ok"));
        List<PositionFuturesInfoBean> list = ret.getObject4List(PositionFuturesInfoBean.class);
        assertThat(list.size(), is(3));
    }

    @Test
    public void test_do_modify() {
        PositionFuturesEditBean bean = new PositionFuturesEditBean();
        bean.setAction("买入平仓 >=");
        bean.setPrice(7009);
        Map map = new HashMap();
        map.put("type", "edit");
        map.put("json", new Gson().toJson(bean));
        TestReturn ret = doPut("/position_futures/1", map);
        assertThat(ret.getStatus(), is("ok"));
    }

    @Test
    public void test_do_open() {
        PositionFuturesOpenBean bean = new PositionFuturesOpenBean("y1605", "空", 3, 6000, "2015-6-19", 5800, "390430");
        Map map = new HashMap();
        map.put("json", new Gson().toJson(bean));
        TestReturn ret = doPost("/position_futures", map);
        assertThat(ret.getStatus(), is("ok"));
        String id = ret.getObject(String.class);
        assertThat(id, notNullValue());
    }

    @Test
    public void test_do_close() {
        PositionFuturesCloseBean bean = new PositionFuturesCloseBean();
        bean.setLot(2);
        bean.setPrice(4500);
        bean.setDate("2015-10-3");
        Map map = new HashMap();
        map.put("type", "close");
        map.put("json", new Gson().toJson(bean));
        TestReturn ret = doPut("/position_futures/1", map);
        assertThat(ret.getStatus(), is("ok"));
    }

    @Test
    public void test1_do_modify() {
        PositionFuturesCloseBean bean = new PositionFuturesCloseBean();
        bean.setLot(2);
        bean.setPrice(4500);
        bean.setDate("2015-10-3");
        Map map = new HashMap();
        map.put("type", "adfd");
        map.put("json", new Gson().toJson(bean));
        TestReturn ret = doPut("/position_futures/1", map);
        assertThat(ret.getStatus(), is("error"));
        assertThat(ret.getMessage(), is("错误的type值"));
    }

    @Test
    public void test_do_delete() {
        TestReturn ret = doDelete("/position_futures/2");
        assertThat(ret.getStatus(), is("ok"));
    }

    @Test
    public void test_do_getActions() {
        PositionFuturesActionsBean bean = new PositionFuturesActionsBean("多");
        Map map = new HashMap();
        map.put("json", new Gson().toJson(bean));
        TestReturn ret = doGet("/position_futures_actions", map);
        assertThat(ret.getStatus(), is("ok"));
        String[] names = ret.getObject(String[].class);
        assertThat(names[0], is("卖出平仓 <="));
        assertThat(names[1], is("卖出平仓 >="));

        bean = new PositionFuturesActionsBean("空");
        map.clear();
        map.put("json", new Gson().toJson(bean));
        ret = doGet("/position_futures_actions", map);
        assertThat(ret.getStatus(), is("ok"));
        names = ret.getObject(String[].class);
        assertThat(names[0], is("买入平仓 >="));
        assertThat(names[1], is("买入平仓 <="));
    }

    @Test
    public void test1_do_getActions() {
        PositionFuturesActionsBean bean = new PositionFuturesActionsBean("多");
        Map map = new HashMap();
        String code = URLEncoder.encode(new Gson().toJson(bean));
        System.out.println("json=" + code);
        map.put("json", code);
        TestReturn ret = doGet("/position_futures_actions", map);
        assertThat(ret.getStatus(), is("ok"));
        String[] names = ret.getObject(String[].class);
        assertThat(names[0], is("卖出平仓 <="));
        assertThat(names[1], is("卖出平仓 >="));

    }
}
