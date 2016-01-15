package org.yecq.goleek.server.web.controller;

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
import org.yecq.baseframework.test.IntegrateBase;
import org.yecq.baseframework.web.Head;

/**
 *
 * @author yecq
 */
public class StockControllerTest extends IntegrateBase {

    @Test
    public void test_do_getExchangeNames() {
        List list = getJsonReturn("/stock/get_exchange_names.go");
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
        List<String[]> list1 = getListObject(list, String[].class);
        String[] names = list1.get(0);
        assertThat(names.length, is(2));
        assertThat(names[0], is("上海证券"));
        assertThat(names[1], is("深圳证券"));
    }

    @Test
    public void test_do_getListAll() {
        List list = getJsonReturn("/stock/get_list_all.go");
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
        List<StockInfoBean> data = getListObject(list, StockInfoBean.class);
        assertThat(data.size(), is(2));
    }

    @Test
    public void test_do_getListInterested() {
        List list = getJsonReturn("/stock/get_list_interested.go");
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
        List<StockInfoBean> data = getListObject(list, StockInfoBean.class);
        assertThat(data.size(), is(1));
    }

    @Test
    public void test_do_add() {
        List list = getJsonReturn("/stock/add.go", new StockAddBean("600395", "包钢稀土", "上海证券"));
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
        String id = getSingleObject(list, String.class);
        assertThat(id, notNullValue());
    }

    @Test
    public void test1_do_add() {
        List list = getJsonReturn("/stock/add.go", new StockAddBean("600399", "广晟有色", "上海证券"));
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("fail"));
        assertThat(head.getMessage(), is("已存在该股票"));
    }

    @Test
    public void test_do_remove() {
        List list = getJsonReturn("/stock/remove.go", new StockRemoveBean("1"));
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
    }

    @Test
    public void test_do_modify() {
        StockModifyBean bean = new StockModifyBean("1");
        bean.setCode("300456");
        bean.setExchange("深圳证券");
        bean.setInterest("y");
        bean.setName("民生银行");
        List list = getJsonReturn("/stock/modify.go", bean);
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
    }

    @Test
    public void test_do_interest() {
        List list = getJsonReturn("/stock/interest.go", new StockInterestBean("1"));
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
    }

    @Test
    public void test_do_unInterest() {
        List list = getJsonReturn("/stock/un_interest.go", new StockUninterestBean("2"));
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
    }

}
