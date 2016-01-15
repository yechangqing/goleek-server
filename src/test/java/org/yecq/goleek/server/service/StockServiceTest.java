package org.yecq.goleek.server.service;

import org.yecq.goleek.server.service.bean.param.StockAddBean;
import org.yecq.goleek.server.service.bean.param.StockInterestBean;
import org.yecq.goleek.server.service.bean.param.StockModifyBean;
import org.yecq.goleek.server.service.bean.param.StockRemoveBean;
import org.yecq.goleek.server.service.bean.param.StockUninterestBean;
import org.yecq.goleek.server.service.bean.result.StockInfoBean;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.yecq.baseframework.plain.service.Sret;
import org.yecq.baseframework.test.Base;

/**
 *
 * @author yecq
 */
public class StockServiceTest extends Base {

    @Autowired
    private StockService ss;

    @Test
    public void test_getExchangeNames() {
        Sret sr = ss.getExchangeNames();
        assertThat(sr.isOk(), is(true));
        String[] names = (String[]) sr.getData();
        assertThat(names.length, is(2));
        assertThat(names[0], is("上海证券"));
        assertThat(names[1], is("深圳证券"));
    }

    @Test
    public void test_getListAll() {
        Sret sr = ss.getListAll();
        assertThat(sr.isOk(), is(true));
        List<StockInfoBean> data = (List) sr.getData();
        assertThat(data.size(), is(2));
    }

    @Test
    public void test_getListInterested() {
        Sret sr = ss.getListInterested();
        assertThat(sr.isOk(), is(true));
        List<StockInfoBean> data = (List) sr.getData();
        assertThat(data.size(), is(1));
    }

    @Test
    public void test_add() {
        StockAddBean bean = new StockAddBean("300614", "山东黄金", "深圳证券");
        Sret sr = ss.add(bean);
        assertThat(sr.isOk(), is(true));
        String id = (String) sr.getData();
        assertThat(id, notNullValue());
    }

    @Test
    public void test1_add() {
        StockAddBean bean = new StockAddBean("600399", "广晟有色", "上海证券");
        Sret sr = ss.add(bean);
        assertThat(sr.isFail(), is(true));
        assertThat(sr.getMessage(), is("已存在该股票"));
    }

    @Test
    public void test_remove() {
        StockRemoveBean bean = new StockRemoveBean("1");
        Sret sr = ss.remove(bean);
        assertThat(sr.isOk(), is(true));
    }

    @Test
    public void test_modify() {
        StockModifyBean bean = new StockModifyBean("2");
        bean.setCode("233456");
        bean.setExchange("上海证券");
        bean.setInterest("y");
        bean.setName("春风科技");
        Sret sr = ss.modify(bean);
        assertThat(sr.isOk(), is(true));
    }

    @Test
    public void interest() {
        StockInterestBean bean = new StockInterestBean("1");
        Sret sr = ss.interest(bean);
        assertThat(sr.isOk(), is(true));
    }

    @Test
    public void test_unInterest() {
        StockUninterestBean bean = new StockUninterestBean("2");
        Sret sr = ss.unInterest(bean);
        assertThat(sr.isOk(), is(true));
    }

}
