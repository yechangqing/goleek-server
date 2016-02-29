package org.yecq.goleek.server.service;

import com.jhhc.baseframework.test.Base;
import com.jhhc.baseframework.web.service.Sret;
import org.yecq.goleek.server.service.bean.param.PositionStockCloseBean;
import org.yecq.goleek.server.service.bean.param.PositionStockEditBean;
import org.yecq.goleek.server.service.bean.param.PositionStockOpenBean;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author yecq
 */
public class PositionStockServiceTest extends Base {

    @Autowired
    private PositionStockService ps;

    @Test
    public void test_getListAll() {
        Sret sr = ps.getListAll();
        assertThat(sr.isOk(), is(true));
        List list = (List) sr.getData();
        assertThat(list.size(), is(3));
    }

    @Test
    public void test_editQuit() {
        Sret sr = ps.editQuit("1", new PositionStockEditBean("卖出 >=", 34));
        assertThat(sr.isOk(), is(true));
    }

    @Test
    public void test_open() {
        PositionStockOpenBean bean = new PositionStockOpenBean();
        bean.setCode("000123");
        bean.setLot(4);
        bean.setOpen_price(62);
        bean.setOpen_date("2015-10-12");
        bean.setQuit_price(58);
        bean.setAccount("123456");
        Sret sr = ps.open(bean);
        assertThat(sr.isOk(), is(true));
    }

    @Test
    public void test_close() {
        Sret sr = ps.close("1", new PositionStockCloseBean(2, 45, "2015-10-13"));
        assertThat(sr.isOk(), is(true));
    }

    @Test
    public void test_delete() {
        Sret sr = ps.delete("2");
        assertThat(sr.isOk(), is(true));
    }

    @Test
    public void test_getActions() {
        Sret sr = ps.getActions();
        assertThat(sr.isOk(), is(true));
        String[] acs = (String[]) sr.getData();
        assertThat(acs[0], is("卖出 <="));
        assertThat(acs[1], is("卖出 >="));
    }
}
