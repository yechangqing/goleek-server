package org.yecq.goleek.server.web.controller;

import org.yecq.goleek.server.service.bean.param.AccountAddBean;
import org.yecq.goleek.server.service.bean.param.AccountModifyBean;
import org.yecq.goleek.server.service.bean.param.AccountRemoveBean;
import org.yecq.goleek.server.service.bean.param.AccountUnuseBean;
import org.yecq.goleek.server.service.bean.param.AccountUseBean;
import org.yecq.goleek.server.service.bean.result.AccountFuturesInfoBean;
import org.yecq.goleek.server.service.bean.result.AccountStockInfoBean;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.yecq.baseframework.test.IntegrateBase;
import org.yecq.baseframework.web.Head;

/**
 *
 * @author yecq
 */
public class AccountControllerTest extends IntegrateBase {

    @Test
    public void test_do_getAccountListFutures() {
        List list = getJsonReturn("/account/get_list_futures.go");
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
        assertThat(head.getMessage(), is("ok"));
        List<AccountFuturesInfoBean> data = getListObject(list, AccountFuturesInfoBean.class);
        assertThat(data.size(), is(2));
        assertThat(data.get(0).getCode(), is("100003109"));
        assertThat(data.get(1).getCode(), is("390430"));
    }

    @Test
    public void test_do_getAccountListFuturesUsed() {
        List list = getJsonReturn("/account/get_list_futures_used.go");
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
        assertThat(head.getMessage(), is("ok"));
        List<AccountFuturesInfoBean> data = getListObject(list, AccountFuturesInfoBean.class);
        assertThat(data.size(), is(1));
        assertThat(data.get(0).getCode(), is("100003109"));
    }

    @Test
    public void test_do_getMoneyFutures() {
        List list = getJsonReturn("/account/get_money_futures.go");
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
        assertThat(head.getMessage(), is("ok"));
        Double money = getSingleObject(list, Double.class);
        assertThat(money, closeTo(150000, 0.1));
    }

    @Test
    public void test_do_add() {
        AccountAddBean add = new AccountAddBean("1234", "锦泰期货", 150000, "期货");
        List list = getJsonReturn("/account/add.go", add);
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
        assertThat(head.getMessage(), is("ok"));
        assertThat(getSingleObject(list, String.class), notNullValue());
    }

    @Test
    public void test1_do_add() {
        AccountAddBean add = new AccountAddBean("1234", "锦泰期货", 150000, null);
        List list = getJsonReturn("/account/add.go", add);
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("error"));
    }

    @Test
    public void test2_do_add() {
        AccountAddBean add = new AccountAddBean("100003109", "新纪元期货", 150000, "期货");
        List list = getJsonReturn("/account/add.go", add);
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("fail"));
        assertThat(head.getMessage(), is("已存在该账号"));
    }

    @Test
    public void test_do_modify() {
        AccountModifyBean modi = new AccountModifyBean("1");
        modi.setMoney(170000);
        modi.setCompany("中粮期货");
        List list = getJsonReturn("/account/modify.go", modi);
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
    }

    @Test
    public void test_do_remove() {
        AccountRemoveBean rem = new AccountRemoveBean("2");
        List list = getJsonReturn("/account/remove.go", rem);
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
    }

    @Test
    public void test_do_use() {
        AccountUseBean use = new AccountUseBean("2");
        List list = getJsonReturn("/account/use.go", use);
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
    }

    @Test
    public void test_do_unUse() {
        AccountUnuseBean uuse = new AccountUnuseBean("1");
        List list = getJsonReturn("/account/un_use.go", uuse);
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
    }

    @Test
    public void test1_do_unUse() {
        AccountUnuseBean uuse = new AccountUnuseBean("13");
        List list = getJsonReturn("/account/un_use.go", uuse);
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("fail"));
    }

    @Test
    public void test_do_getAccountListStock() {
        List list = getJsonReturn("/account/get_list_stock.go");
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
        List<AccountStockInfoBean> data = (List) getListObject(list, AccountStockInfoBean.class);
        assertThat(data.size(), is(1));
        assertThat(data.get(0).getCode(), is("27107470"));
    }

    @Test
    public void test_do_getAccountListStockUsed() {
        List list = getJsonReturn("/account/get_list_stock_used.go");
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
        List<AccountStockInfoBean> data = (List) getListObject(list, AccountStockInfoBean.class);
        assertThat(data.size(), is(1));
        assertThat(data.get(0).getCode(), is("27107470"));
    }

    @Test
    public void test_do_getMoneyStock() {
        List list = getJsonReturn("/account/get_money_stock.go");
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
        double money = getSingleObject(list, Double.class);
        assertThat(money, closeTo(43000, 0.1));
    }
}
