package org.yecq.goleek.server.web.controller;

import com.google.gson.Gson;
import com.jhhc.baseframework.test.IntegrateRestfulBase;
import com.jhhc.baseframework.test.TestReturn;
import java.util.HashMap;
import org.yecq.goleek.server.service.bean.param.AccountAddBean;
import org.yecq.goleek.server.service.bean.param.AccountModifyBean;
import org.yecq.goleek.server.service.bean.result.AccountFuturesInfoBean;
import org.yecq.goleek.server.service.bean.result.AccountStockInfoBean;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

/**
 *
 * @author yecq
 */
public class AccountControllerTest extends IntegrateRestfulBase {

    @Test
    public void test_do_getAccountListFutures() {
        Map map = new HashMap();
        map.put("type", "futures");
        TestReturn ret = doGet("/accounts", map);
        assertThat(ret.getStatus(), is("ok"));
        assertThat(ret.getMessage(), is("ok"));
        List<AccountFuturesInfoBean> data = ret.getObject4List(AccountFuturesInfoBean.class);
        assertThat(data.size(), is(2));
        assertThat(data.get(0).getCode(), is("100003109"));
        assertThat(data.get(1).getCode(), is("390430"));
    }

    @Test
    public void test1_do_getAccountListFutures() {
        Map map = new HashMap();
        map.put("type", "abcd");
        TestReturn ret = doGet("/accounts", map);
        assertThat(ret.getStatus(), is("error"));
        assertThat(ret.getMessage(), is("错误的参数值type"));
    }

    @Test
    public void test_do_getAccountListFuturesUsed() {
        Map map = new HashMap();
        map.put("type", "futures");
        TestReturn ret = doGet("/accounts_used", map);
        assertThat(ret.getStatus(), is("ok"));
        assertThat(ret.getMessage(), is("ok"));
        List<AccountFuturesInfoBean> data = ret.getObject4List(AccountFuturesInfoBean.class);
        assertThat(data.size(), is(1));
        assertThat(data.get(0).getCode(), is("100003109"));
    }

    @Test
    public void test_do_getMoneyFutures() {
        TestReturn ret = doGet("/moneys_futures");
        assertThat(ret.getStatus(), is("ok"));
        assertThat(ret.getMessage(), is("ok"));
        Double money = ret.getObject(Double.class);
        assertThat(money, closeTo(150000, 0.1));
    }

    @Test
    public void test_do_add() {
        AccountAddBean add = new AccountAddBean("1234", "锦泰期货", 150000, "期货");
        Map map = new HashMap();
        map.put("json", new Gson().toJson(add));
        TestReturn ret = doPost("/accounts", map);
        assertThat(ret.getStatus(), is("ok"));
        assertThat(ret.getMessage(), is("ok"));
        assertThat(ret.getObject(String.class), notNullValue());
    }

    @Test
    public void test1_do_add() {
        AccountAddBean add = new AccountAddBean("1234", "锦泰期货", 150000, null);
        Map map = new HashMap();
        map.put("json", new Gson().toJson(add));
        TestReturn ret = doPost("/accounts", map);
        assertThat(ret.getStatus(), is("error"));
    }

    @Test
    public void test2_do_add() {
        AccountAddBean add = new AccountAddBean("100003109", "新纪元期货", 150000, "期货");
        Map map = new HashMap();
        map.put("json", new Gson().toJson(add));
        TestReturn ret = doPost("/accounts", map);
        assertThat(ret.getStatus(), is("fail"));
        assertThat(ret.getMessage(), is("已存在该账号"));
    }

    @Test
    public void test_do_modify() {
        AccountModifyBean modi = new AccountModifyBean();
        modi.setMoney(170000);
        modi.setCompany("中粮期货");
        Map map = new HashMap();
        map.put("json", new Gson().toJson(modi));
        TestReturn ret = doPut("/accounts/1", map);
        assertThat(ret.getStatus(), is("ok"));
    }

    @Test
    public void test_do_remove() {
        TestReturn ret = doDelete("/accounts/2");
        assertThat(ret.getStatus(), is("ok"));
    }

    @Test
    public void test_do_use() {
        AccountModifyBean bean = new AccountModifyBean();
        bean.setUsed("y");
        Map map = new HashMap();
        map.put("json", new Gson().toJson(bean));
        TestReturn ret = doPut("/accounts/2", map);
        assertThat(ret.getStatus(), is("ok"));
    }

    @Test
    public void test_do_unUse() {
        AccountModifyBean bean = new AccountModifyBean();
        bean.setUsed("n");
        Map map = new HashMap();
        map.put("json", new Gson().toJson(bean));
        TestReturn ret = doPut("/accounts/1", map);
        assertThat(ret.getStatus(), is("ok"));
    }

    @Test
    public void test1_do_unUse() {
        AccountModifyBean bean = new AccountModifyBean();
        bean.setUsed("n");
        Map map = new HashMap();
        map.put("json", new Gson().toJson(bean));
        TestReturn ret = doPut("/accounts/13", map);
        assertThat(ret.getStatus(), is("fail"));
    }

    @Test
    public void test_do_getAccountListStock() {
        Map map = new HashMap();
        map.put("type", "stock");
        TestReturn ret = doGet("/accounts", map);
        assertThat(ret.getStatus(), is("ok"));
        List<AccountStockInfoBean> data = ret.getObject4List(AccountStockInfoBean.class);
        assertThat(data.size(), is(1));
        assertThat(data.get(0).getCode(), is("27107470"));
    }

    @Test
    public void test_do_getAccountListStockUsed() {
        Map map = new HashMap();
        map.put("type", "stock");
        TestReturn ret = doGet("/accounts_used", map);
        assertThat(ret.getStatus(), is("ok"));
        List<AccountStockInfoBean> data = ret.getObject4List(AccountStockInfoBean.class);
        assertThat(data.size(), is(1));
        assertThat(data.get(0).getCode(), is("27107470"));
    }

    @Test
    public void test_do_getMoneyStock() {
        TestReturn ret = doGet("/moneys_stock");
        assertThat(ret.getStatus(), is("ok"));
        Double money = ret.getObject(Double.class);
        assertThat(money, closeTo(43000, 0.1));
    }
}
