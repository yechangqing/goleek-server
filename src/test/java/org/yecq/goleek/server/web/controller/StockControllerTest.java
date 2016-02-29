package org.yecq.goleek.server.web.controller;

import com.google.gson.Gson;
import com.jhhc.baseframework.test.IntegrateRestfulBase;
import com.jhhc.baseframework.test.TestReturn;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.yecq.goleek.server.service.bean.param.StockAddBean;
import org.yecq.goleek.server.service.bean.param.StockModifyBean;
import org.yecq.goleek.server.service.bean.result.StockInfoBean;

/**
 *
 * @author yecq
 */
public class StockControllerTest extends IntegrateRestfulBase {

    @Test
    public void test_do_getExchangeNames() {
        TestReturn ret = doGet("/stock_exchange_names");
        assertThat(ret.getStatus(), is("ok"));
        String[] names = ret.getObject(String[].class);
        assertThat(names.length, is(2));
        assertThat(names[0], is("上海证券"));
        assertThat(names[1], is("深圳证券"));
    }

    @Test
    public void test_do_getListAll() {
        TestReturn ret = doGet("/stocks");
        assertThat(ret.getStatus(), is("ok"));
        List<StockInfoBean> data = ret.getObject4List(StockInfoBean.class);
        assertThat(data.size(), is(2));
    }

    @Test
    public void test_do_getListInterested() {
        TestReturn ret = doGet("/stocks_interested");
        assertThat(ret.getStatus(), is("ok"));
        List<StockInfoBean> data = ret.getObject4List(StockInfoBean.class);
        assertThat(data.size(), is(1));
    }

    @Test
    public void test_do_add() {
        StockAddBean bean = new StockAddBean();
        bean.setCode("600395");
        bean.setName("包钢稀土");
        bean.setExchange("上海证券");
        Map map = new HashMap();
        map.put("json", new Gson().toJson(bean));
        TestReturn ret = doPost("/stocks", map);
        assertThat(ret.getStatus(), is("ok"));
        String id = ret.getObject(String.class);
        assertThat(id, notNullValue());
    }

    @Test
    public void test1_do_add() {
        StockAddBean bean = new StockAddBean();
        bean.setCode("600399");
        bean.setName("广晟有色");
        bean.setExchange("上海证券");
        Map map = new HashMap();
        map.put("json", new Gson().toJson(bean));
        TestReturn ret = doPost("/stocks", map);
        assertThat(ret.getStatus(), is("fail"));
        assertThat(ret.getMessage(), is("已存在该股票"));
    }

    @Test
    public void test_do_remove() {
        TestReturn ret = doDelete("/stocks/1");
        assertThat(ret.getStatus(), is("ok"));
    }

    @Test
    public void test_do_modify() {
        StockModifyBean bean = new StockModifyBean();
        bean.setCode("300456");
        bean.setExchange("深圳证券");
        bean.setInterest("y");
        bean.setName("民生银行");
        Map map = new HashMap();
        map.put("json", new Gson().toJson(bean));
        TestReturn ret = doPut("/stocks/1", map);
        assertThat(ret.getStatus(), is("ok"));
    }

    @Test
    public void test_do_interest() {
        StockModifyBean bean = new StockModifyBean();
        bean.setInterest("y");
        Map map = new HashMap();
        map.put("json", new Gson().toJson(bean));
        TestReturn ret = doPut("/stocks/1", map);
        assertThat(ret.getStatus(), is("ok"));
    }

    @Test
    public void test_do_unInterest() {
        StockModifyBean bean = new StockModifyBean();
        bean.setInterest("n");
        Map map = new HashMap();
        map.put("json", new Gson().toJson(bean));
        TestReturn ret = doPut("/stocks/2", map);
        assertThat(ret.getStatus(), is("ok"));
    }

    @Test
    public void test_do_interestAll() {
        Map map = new HashMap();
        map.put("action", "select");
        TestReturn ret = doPut("/stocks_interest", map);
        assertThat(ret.getStatus(), is("ok"));
    }

    @Test
    public void test_do_unInterestAll() {
        Map map = new HashMap();
        map.put("action", "unselect");
        TestReturn ret = doPut("/stocks_interest", map);
        assertThat(ret.getStatus(), is("ok"));
    }

    @Test
    public void test1_do_interest_un() {
        Map map = new HashMap();
        map.put("action", "abcd");
        TestReturn ret = doPut("/stocks_interest", map);
        assertThat(ret.getStatus(), is("error"));
        assertThat(ret.getMessage(), is("错误的参数值action"));
    }

}
