package org.yecq.goleek.server.web.controller;

import com.google.gson.Gson;
import com.jhhc.baseframework.test.IntegrateRestfulBase;
import com.jhhc.baseframework.test.TestReturn;
import java.util.HashMap;
import org.yecq.goleek.server.service.bean.param.FuturesAddBean;
import org.yecq.goleek.server.service.bean.param.FuturesCloneBean;
import org.yecq.goleek.server.service.bean.param.FuturesModifyBean;
import org.yecq.goleek.server.service.bean.result.FuturesInfoBean;
import java.util.List;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author yecq
 */
public class FuturesControllerTest extends IntegrateRestfulBase {

    @Test
    public void test_do_getExchangeNames() {
        TestReturn ret = doGet("/futures_exchange_names");
        assertThat(ret.getStatus(), is("ok"));
        String[] names = ret.getObject(String[].class);
        assertThat(names.length, is(3));
        assertThat(names[0], is("上海期货"));
        assertThat(names[1], is("大连商品"));
        assertThat(names[2], is("郑州商品"));
    }

    @Test
    public void test_do_getListAll() {
        TestReturn ret = doGet("/futures");
        assertThat(ret.getStatus(), is("ok"));
        List<FuturesInfoBean> list = ret.getObject4List(FuturesInfoBean.class);
        assertThat(list.size(), is(3));
    }

    @Test
    public void test_do_getListInterested() {
        TestReturn ret = doGet("/futures_interested");
        assertThat(ret.getStatus(), is("ok"));
        List list = ret.getObject4List(FuturesInfoBean.class);
        assertThat(list.size(), is(2));
    }

    @Test
    public void test_do_add() {
        FuturesAddBean add = new FuturesAddBean("sr1605", "白糖", 0.09, 10, 1, "郑州商品");
        Map map = new HashMap();
        map.put("json", new Gson().toJson(add));
        TestReturn ret = doPost("/futures", map);
        assertThat(ret.getStatus(), is("ok"));
        String id = ret.getObject(String.class);
        assertThat(id, notNullValue());
    }

    @Test
    public void test1_do_add() {
        FuturesAddBean add = new FuturesAddBean("sr1605", "白糖", 0.09, 10, 1, null);
        Map map = new HashMap();
        map.put("json", new Gson().toJson(add));
        TestReturn ret = doPost("/futures", map);
        assertThat(ret.getStatus(), is("error"));
    }

    @Test
    public void test2_do_add() {
        FuturesAddBean add = new FuturesAddBean("rb1601", "螺纹钢", 0.09, 10, 1, "上海期货");
        Map map = new HashMap();
        map.put("json", new Gson().toJson(add));
        TestReturn ret = doPost("/futures", map);
        assertThat(ret.getStatus(), is("fail"));
        assertThat(ret.getMessage(), is("已存在该合约"));
    }

    @Test
    public void test_do_remove() {
        TestReturn ret = doDelete("/futures/2");
        assertThat(ret.getStatus(), is("ok"));
    }

    @Test
    public void test_do_cloneItself() {
        FuturesCloneBean bean = new FuturesCloneBean();
        bean.setNewCode("rb1701");
        Map map = new HashMap();
        map.put("json", new Gson().toJson(bean));
        TestReturn ret = doPost("/futures/1", map);
        assertThat(ret.getStatus(), is("ok"));
        String id = ret.getObject(String.class);
        assertThat(id, notNullValue());
    }

    @Test
    public void test1_do_cloneItself() {
        FuturesCloneBean bean = new FuturesCloneBean();
        bean.setNewCode("y1701");
        Map map = new HashMap();
        map.put("json", new Gson().toJson(bean));
        TestReturn ret = doPost("/futures/1", map);
        assertThat(ret.getStatus(), is("fail"));
        assertThat(ret.getMessage(), is("输入的合约名应当是rb"));
    }

    @Test
    public void test_do_modify() {
        FuturesModifyBean bean = new FuturesModifyBean();
        bean.setCode("y1703");
        bean.setMargin(0.11);
        Map map = new HashMap();
        map.put("json", new Gson().toJson(bean));
        TestReturn ret = doPut("/futures/2", map);
        assertThat(ret.getStatus(), is("ok"));
    }

    @Test
    public void test_do_interest() {
        FuturesModifyBean bean = new FuturesModifyBean();
        bean.setInterest("y");
        Map map = new HashMap();
        map.put("json", new Gson().toJson(bean));
        TestReturn ret = doPut("/futures/1", map);
        assertThat(ret.getStatus(), is("ok"));
    }

    @Test
    public void test_do_unInterest() {
        FuturesModifyBean bean = new FuturesModifyBean();
        bean.setInterest("n");
        Map map = new HashMap();
        map.put("json", new Gson().toJson(bean));
        TestReturn ret = doPut("/futures/2", map);
        assertThat(ret.getStatus(), is("ok"));
    }

    @Test
    public void test_do_interestAll() {
        Map map = new HashMap();
        map.put("action", "select");
        TestReturn ret = doPut("/futures_interest", map);
        assertThat(ret.getStatus(), is("ok"));
    }

    @Test
    public void test_do_unInterestAll() {
        Map map = new HashMap();
        map.put("action", "unselect");
        TestReturn ret = doPut("/futures_interest", map);
        assertThat(ret.getStatus(), is("ok"));
    }

    @Test
    public void test_do_interest_un() {
        Map map = new HashMap();
        map.put("action", "adf");
        TestReturn ret = doPut("/futures_interest", map);
        assertThat(ret.getStatus(), is("error"));
        assertThat(ret.getMessage(), is("错误的参数值action"));
    }
}
