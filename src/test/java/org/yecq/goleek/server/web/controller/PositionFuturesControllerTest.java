package org.yecq.goleek.server.web.controller;

import org.yecq.goleek.server.service.bean.param.PositionFuturesActionsBean;
import org.yecq.goleek.server.service.bean.param.PositionFuturesCloseBean;
import org.yecq.goleek.server.service.bean.param.PositionFuturesDeleteBean;
import org.yecq.goleek.server.service.bean.param.PositionFuturesEditBean;
import org.yecq.goleek.server.service.bean.param.PositionFuturesOpenBean;
import org.yecq.goleek.server.service.bean.result.PositionFuturesInfoBean;
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
public class PositionFuturesControllerTest extends IntegrateBase {

    @Test
    public void test_do_getListAll() {
        List list = getJsonReturn("/position_futures/get_list_all.go");
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
        List<PositionFuturesInfoBean> ret = getListObject(list, PositionFuturesInfoBean.class);
        assertThat(ret.size(), is(3));
    }

    @Test
    public void test_do_editQuit() {
        List list = getJsonReturn("/position_futures/edit_quit.go", new PositionFuturesEditBean("1", "买入平仓 >=", 7009));
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
    }

    @Test
    public void test_do_open() {
        PositionFuturesOpenBean bean = new PositionFuturesOpenBean("y1605", "空", 3, 6000, "2015-6-19", 5800, "390430");
        List list = getJsonReturn("/position_futures/open.go", bean);
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
        String id = getSingleObject(list, String.class);
        assertThat(id, notNullValue());
    }

    @Test
    public void test_do_close() {
        List list = getJsonReturn("/position_futures/close.go", new PositionFuturesCloseBean("1", 2, 4500, "2015-10-3"));
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
    }

    @Test
    public void test_do_delete() {
        List list = getJsonReturn("/position_futures/delete.go", new PositionFuturesDeleteBean("2"));
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
    }

    @Test
    public void test_do_getActions() {
        List list = getJsonReturn("/position_futures/get_actions.go", new PositionFuturesActionsBean("多"));
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
        String[] names = (getListObject(list, String[].class)).get(0);
        assertThat(names[0], is("卖出平仓 <="));
        assertThat(names[1], is("卖出平仓 >="));

        list = getJsonReturn("/position_futures/get_actions.go", new PositionFuturesActionsBean("空"));
        head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
        names = (getListObject(list, String[].class)).get(0);
        assertThat(names[0], is("买入平仓 >="));
        assertThat(names[1], is("买入平仓 <="));
    }
}
