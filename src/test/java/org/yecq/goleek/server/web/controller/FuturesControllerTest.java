package org.yecq.goleek.server.web.controller;

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
import org.yecq.baseframework.test.IntegrateBase;
import org.yecq.baseframework.web.Head;

/**
 *
 * @author yecq
 */
public class FuturesControllerTest extends IntegrateBase {

    @Test
    public void test_do_getExchangeNames() {
        List list = getJsonReturn("/futures/get_exchange_names.go");
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
        List<String[]> list1 = getListObject(list, String[].class);
        String[] names = list1.get(0);
        assertThat(names.length, is(3));
        assertThat(names[0], is("上海期货"));
        assertThat(names[1], is("大连商品"));
        assertThat(names[2], is("郑州商品"));
    }

    @Test
    public void test_do_getListAll() {
        List list = getJsonReturn("/futures/get_list_all.go");
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
        List ret = getListObject(list, FuturesInfoBean.class);
        assertThat(ret.size(), is(3));
    }

    @Test
    public void test_do_getListInterested() {
        List list = getJsonReturn("/futures/get_list_interested.go");
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
        List ret = getListObject(list, FuturesInfoBean.class);
        assertThat(ret.size(), is(2));
    }

    @Test
    public void test_do_add() {
        FuturesAddBean add = new FuturesAddBean("sr1605", "白糖", 0.09, 10, 1, "郑州商品");
        List list = getJsonReturn("/futures/add.go", add);
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
        String id = getSingleObject(list, String.class);
        assertThat(id, notNullValue());
    }

    @Test
    public void test1_do_add() {
        FuturesAddBean add = new FuturesAddBean("sr1605", "白糖", 0.09, 10, 1, null);
        List list = getJsonReturn("/futures/add.go", add);
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("error"));
    }

    @Test
    public void test2_do_add() {
        FuturesAddBean add = new FuturesAddBean("rb1601", "螺纹钢", 0.09, 10, 1, "上海期货");
        List list = getJsonReturn("/futures/add.go", add);
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("fail"));
        assertThat(head.getMessage(), is("已存在该合约"));
    }

    @Test
    public void test_do_remove() {
        FuturesRemoveBean rem = new FuturesRemoveBean("2");
        List list = getJsonReturn("/futures/remove.go", rem);
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
    }

    @Test
    public void test_do_cloneItself() {
        FuturesCloneBean clo = new FuturesCloneBean("1", "rb1701");
        List list = getJsonReturn("/futures/clone.go", clo);
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
    }

    @Test
    public void test1_do_cloneItself() {
        FuturesCloneBean clo = new FuturesCloneBean("1", "y1701");
        List list = getJsonReturn("/futures/clone.go", clo);
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("fail"));
    }

    @Test
    public void test_do_modify() {
        FuturesModifyBean modi = new FuturesModifyBean("2");
        modi.setCode("y1703");
        modi.setMargin(0.11);
        List list = getJsonReturn("/futures/modify.go", modi);
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
    }

    @Test
    public void test_do_interest() {
        FuturesInterestBean in = new FuturesInterestBean("1");
        List list = getJsonReturn("/futures/interest.go", in);
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
    }

    @Test
    public void test_do_unInterest() {
        FuturesUninterestBean in = new FuturesUninterestBean("2");
        List list = getJsonReturn("/futures/un_interest.go", in);
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
    }
}
