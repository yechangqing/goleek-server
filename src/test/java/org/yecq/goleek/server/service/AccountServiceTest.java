package org.yecq.goleek.server.service;

import org.yecq.goleek.server.service.bean.param.AccountAddBean;
import org.yecq.goleek.server.service.bean.param.AccountModifyBean;
import org.yecq.goleek.server.service.bean.param.AccountRemoveBean;
import org.yecq.goleek.server.service.bean.param.AccountUnuseBean;
import org.yecq.goleek.server.service.bean.param.AccountUseBean;
import org.yecq.goleek.server.service.bean.result.AccountFuturesInfoBean;
import org.yecq.goleek.server.service.bean.result.AccountStockInfoBean;
import org.yecq.goleek.server.service.core.Account;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.yecq.baseframework.plain.service.Sret;
import org.yecq.baseframework.test.Base;

/**
 *
 * @author yecq
 */
public class AccountServiceTest extends Base {

    @Autowired
    private AccountService as;

    @Test
    public void test_getAccountListFutures() {
        Sret sr = as.getAccountListFutures();
        assertThat(sr.isOk(), is(true));
        assertThat(sr.getMessage(), is("ok"));
        List<AccountFuturesInfoBean> list = (List<AccountFuturesInfoBean>) sr.getData();
        assertThat(list.size(), is(2));
        AccountFuturesInfoBean bean = list.get(0);
        assertThat(bean.getCode(), is("100003109"));
        bean = list.get(1);
        assertThat(bean.getCode(), is("390430"));
    }

    @Test
    public void test_getAccountListFuturesUsed() {
        Sret sr = as.getAccountListFuturesUsed();
        assertThat(sr.isOk(), is(true));
        assertThat(sr.getMessage(), is("ok"));
        List<AccountFuturesInfoBean> list = (List<AccountFuturesInfoBean>) sr.getData();
        assertThat(list.size(), is(1));
        AccountFuturesInfoBean bean = list.get(0);
        assertThat(bean.getCode(), is("100003109"));
    }

    @Test
    public void test_getMoneyFutures() {
        Sret sr = as.getMoneyFutures();
        assertThat(sr.isOk(), is(true));
        assertThat(sr.getMessage(), is("ok"));
        double money = (double) sr.getData();
        assertThat(money, closeTo(150000, 0.1));
    }

    @Test
    public void test_add() {
        AccountAddBean bean = new AccountAddBean("100234", "弘业期货", 121000, "期货");
        Sret sr = as.add(bean);
        assertThat(sr.isOk(), is(true));
        assertThat(sr.getMessage(), is("ok"));
        assertThat(sr.getData(), notNullValue());
    }

    @Test
    public void test1_add() {
        AccountAddBean bean = new AccountAddBean("100234", "弘业期货", 121000, null);
        Sret sr = as.add(bean);
        assertThat(sr.isError(), is(true));
    }

    @Test
    public void test2_add() {
        AccountAddBean bean = new AccountAddBean("100003109", "新纪元期货", 121000, null);
        Sret sr = as.add(bean);
        assertThat(sr.isFail(), is(true));
        assertThat(sr.getMessage(), is("已存在该账号"));
    }

    @Test
    public void test_modify() {
        AccountModifyBean bean = new AccountModifyBean("1");
        bean.setCode("1234");
        bean.setCompany("割韭菜");
        bean.setMoney(3200000);
        bean.setUsed("n");
        Sret sr = as.modify(bean);
        assertThat(sr.isOk(), is(true));
        Account acc = new Account(bean.getId());
        assertThat(acc.getInfo("code") + "", is("1234"));
        assertThat(acc.getInfo("company") + "", is("割韭菜"));
        assertThat(Double.parseDouble(acc.getInfo("money") + ""), closeTo(3200000, 0.1));
        assertThat(acc.getInfo("used") + "", is("n"));
    }

    @Test
    public void test_remove() {
        AccountRemoveBean bean = new AccountRemoveBean("1");
        Sret sr = as.remove(bean);
        assertThat(sr.isOk(), is(true));
    }

    @Test
    public void test_use() {
        AccountUseBean bean = new AccountUseBean("2");
        Sret sr = as.use(bean);
        assertThat(sr.isOk(), is(true));
    }

    @Test
    public void test_unUse() {
        AccountUnuseBean bean = new AccountUnuseBean("1");
        Sret sr = as.unUse(bean);
        assertThat(sr.isOk(), is(true));
    }

    @Test
    public void test_getAccountListStock() {
        Sret sr = as.getAccountListStock();
        assertThat(sr.isOk(), is(true));
        List<AccountStockInfoBean> list = (List) sr.getData();
        assertThat(list.size(), is(1));
        assertThat(list.get(0).getCode(), is("27107470"));
    }

    @Test
    public void test_getAccountListStockUsed() {
        Sret sr = as.getAccountListStockUsed();
        assertThat(sr.isOk(), is(true));
        List<AccountStockInfoBean> list = (List) sr.getData();
        assertThat(list.size(), is(1));
        assertThat(list.get(0).getCode(), is("27107470"));
    }

    @Test
    public void test_getMoneyStock() {
        Sret sr = as.getMoneyStock();
        assertThat(sr.isOk(), is(true));
        double money = (double) sr.getData();
        assertThat(money, closeTo(43000, 0.1));
    }
}
