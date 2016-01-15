package org.yecq.goleek.server.service;

import org.yecq.goleek.server.service.bean.param.FuturesAddBean;
import org.yecq.goleek.server.service.bean.param.FuturesCloneBean;
import org.yecq.goleek.server.service.bean.param.FuturesInterestBean;
import org.yecq.goleek.server.service.bean.param.FuturesModifyBean;
import org.yecq.goleek.server.service.bean.param.FuturesRemoveBean;
import org.yecq.goleek.server.service.bean.param.FuturesUninterestBean;
import org.yecq.goleek.server.service.bean.result.FuturesInfoBean;
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
public class FuturesServiceTest extends Base {

    @Autowired
    private FuturesService fs;

    @Test
    public void test_getExchangeNames() {
        Sret sr = fs.getExchangeNames();
        assertThat(sr.isOk(), is(true));
        String[] names = (String[]) sr.getData();
        assertThat(names.length, is(3));
        assertThat(names[0], is("上海期货"));
        assertThat(names[1], is("大连商品"));
        assertThat(names[2], is("郑州商品"));
    }

    @Test
    public void test_getListAll() {
        Sret sr = fs.getListAll();
        assertThat(sr.isOk(), is(true));
        List<FuturesInfoBean> list = (List) sr.getData();
        assertThat(list.size(), is(3));
    }

    @Test
    public void test_getListInterested() {
        Sret sr = fs.getListInterested();
        assertThat(sr.isOk(), is(true));
        List<FuturesInfoBean> list = (List) sr.getData();
        assertThat(list.size(), is(2));
    }

    @Test
    public void test_add() {
        FuturesAddBean bean = new FuturesAddBean();
        bean.setCode("m1602");
        bean.setName("豆粕");
        bean.setMargin(0.09);
        bean.setUnit(10);
        bean.setMin(1);
        bean.setExchange("大连商品");
        Sret sr = fs.add(bean);
        assertThat(sr.isOk(), is(true));
        assertThat((String) sr.getData(), notNullValue());
    }

    @Test
    public void test1_add() {
        FuturesAddBean bean = new FuturesAddBean();
//        bean.setCode("m1602");
        bean.setName("豆粕");
        bean.setMargin(0.09);
        bean.setUnit(10);
        bean.setMin(1);
        bean.setExchange("大连商品");
        Sret sr = fs.add(bean);
        assertThat(sr.isError(), is(true));
    }

    @Test
    public void test2_add() {
        FuturesAddBean bean = new FuturesAddBean();
        bean.setCode("rb1601");
        bean.setName("螺纹钢");
        bean.setMargin(0.09);
        bean.setUnit(10);
        bean.setMin(1);
        bean.setExchange("上海商品");
        Sret sr = fs.add(bean);
        assertThat(sr.isFail(), is(true));
        assertThat(sr.getMessage(), is("已存在该合约"));
    }

    @Test
    public void test_remove() {
        FuturesRemoveBean bean = new FuturesRemoveBean("1");
        Sret sr = fs.remove(bean);
        assertThat(sr.isOk(), is(true));
    }

    @Test
    public void test_cloneItself() {
        FuturesCloneBean bean = new FuturesCloneBean("1", "rb1612");
        Sret sr = fs.cloneItself(bean);
        assertThat(sr.isOk(), is(true));
    }

    @Test
    public void test1_cloneItself() {
        FuturesCloneBean bean = new FuturesCloneBean("1", "m1612");
        Sret sr = fs.cloneItself(bean);
        assertThat(sr.isFail(), is(true));
    }

    @Test
    public void test_modify() {
        FuturesModifyBean bean = new FuturesModifyBean("1");
        bean.setCode("code");
        bean.setName("name");
        bean.setMargin(0.14);
        bean.setUnit(15);
        bean.setMin(0.01);
        bean.setExchange("大连商品");
        Sret sr = fs.modify(bean);
        assertThat(sr.isOk(), is(true));
    }

    @Test
    public void test_interest() {
        FuturesInterestBean bean = new FuturesInterestBean("1");
        Sret sr = fs.interest(bean);
        assertThat(sr.isOk(), is(true));
    }

    @Test
    public void test_unInterest() {
        FuturesUninterestBean bean = new FuturesUninterestBean("2");
        Sret sr = fs.unInterest(bean);
        assertThat(sr.isOk(), is(true));
    }
}
