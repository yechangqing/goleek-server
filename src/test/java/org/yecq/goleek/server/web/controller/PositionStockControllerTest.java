package org.yecq.goleek.server.web.controller;

import org.yecq.goleek.server.service.bean.param.PositionStockCloseBean;
import org.yecq.goleek.server.service.bean.param.PositionStockDeleteBean;
import org.yecq.goleek.server.service.bean.param.PositionStockEditBean;
import org.yecq.goleek.server.service.bean.param.PositionStockOpenBean;
import org.yecq.goleek.server.service.bean.result.PositionStockInfoBean;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.yecq.baseframework.test.IntegrateBase;
import org.yecq.baseframework.web.Head;

/**
 *
 * @author yecq
 */
public class PositionStockControllerTest extends IntegrateBase {

    @Test
    public void test_do_getListAll() {
        List list = getJsonReturn("/position_stock/get_list_all.go");
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
        List<PositionStockInfoBean> data = getListObject(list, PositionStockInfoBean.class);
        assertThat(data.size(), is(3));
    }

//    @Test
    public void test_do_editQuit() {
        List list = getJsonReturn("/position_stock/edit_quit.go", new PositionStockEditBean("1", "卖出 >=", 31));
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
    }

//    @Test
    public void test_do_open() {
        PositionStockOpenBean bean = new PositionStockOpenBean();
        bean.setCode("600399");
        bean.setLot(2);
        bean.setOpen_price(42);
        bean.setOpen_date("2015-11-13");
        bean.setQuit_price(40);
        bean.setAccount("27107470");
        List list = getJsonReturn("/position_stock/open.go", bean);
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
        String id = getSingleObject(list, String.class);
        assertThat(id, is("1"));
    }

//    @Test
    public void test_do_close() {
        List list = getJsonReturn("/position_stock/close.go", new PositionStockCloseBean("1", 2, 56, "2015-10-20"));
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
    }

//    @Test
    public void test_do_delete() {
        List list = getJsonReturn("/position_stock/delete.go", new PositionStockDeleteBean("1"));
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
    }

    @Test
    public void test_do_getActions() {
        List list = getJsonReturn("/position_stock/get_actions.go");
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
        String[] accs = getListObject(list, String[].class).get(0);
        assertThat(accs.length, is(2));
        assertThat(accs[0], is("卖出 <="));
        assertThat(accs[1], is("卖出 >="));
    }
}
