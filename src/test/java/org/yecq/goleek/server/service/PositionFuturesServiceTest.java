package org.yecq.goleek.server.service;

import com.jhhc.baseframework.test.Base;
import com.jhhc.baseframework.web.service.Sret;
import org.yecq.goleek.server.service.bean.param.PositionFuturesActionsBean;
import org.yecq.goleek.server.service.bean.param.PositionFuturesEditBean;
import org.yecq.goleek.server.service.bean.param.PositionFuturesOpenBean;
import org.yecq.goleek.server.service.bean.result.PositionFuturesInfoBean;
import org.yecq.goleek.server.service.core.PositionFutures;
import java.util.List;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author yecq
 */
public class PositionFuturesServiceTest extends Base {

    @Autowired
    private PositionFuturesService ps;

    @Test
    public void test_getListAll() {
        Sret sr = ps.getListAll();
        assertThat(sr.isOk(), is(true));
        List<PositionFuturesInfoBean> list = (List) sr.getData();
        assertThat(list.size(), is(3));
        assertThat(list.get(0).getId(), is("1"));
        assertThat(list.get(1).getId(), is("2"));
        assertThat(list.get(2).getId(), is("3"));
    }

    @Test
    public void test_editQuit() {
        PositionFuturesEditBean bean = new PositionFuturesEditBean("1", "买入平仓 <=", 3001);
        Sret sr = ps.editQuit(bean);
        assertThat(sr.isOk(), is(true));
    }

    @Test
    public void test_open() {
        PositionFuturesOpenBean bean = new PositionFuturesOpenBean("y1605", "空", 3, 6000, "2015-6-19", 5800, "390430");
        Sret sr = ps.open(bean);
        assertThat(sr.isOk(), is(true));
        String id = (String) sr.getData();
        assertThat(id, notNullValue());
        PositionFutures p = new PositionFutures(id);
        Map<String, Object> map = p.getInfo();
        assertThat(map.get("contract") + "", is("y1605"));
        assertThat(map.get("direct") + "", is("空"));
        assertThat(Integer.parseInt(map.get("lot") + ""), is(3));
        assertThat(Double.parseDouble(map.get("open_price") + ""), closeTo(6000, 0.1));
        assertThat(Double.parseDouble(map.get("quit_price") + ""), closeTo(5800, 0.01));
        assertThat(map.get("action") + "", is("买入平仓 >="));
        assertThat(map.get("account") + "", is("390430"));

        sr = ps.getListAll();
        assertThat(((List) sr.getData()).size(), is(4));
    }

    @Test
    public void test_getActions() {
        Sret sr = ps.getActions(new PositionFuturesActionsBean("多"));
        assertThat(sr.isOk(), is(true));
        String[] acts = (String[]) sr.getData();
        assertThat(acts[0], is("卖出平仓 <="));
        assertThat(acts[1], is("卖出平仓 >="));

        sr = ps.getActions(new PositionFuturesActionsBean("空"));
        assertThat(sr.isOk(), is(true));
        acts = (String[]) sr.getData();
        assertThat(acts[0], is("买入平仓 >="));
        assertThat(acts[1], is("买入平仓 <="));
    }
}
