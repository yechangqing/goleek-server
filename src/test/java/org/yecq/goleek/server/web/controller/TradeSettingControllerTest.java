package org.yecq.goleek.server.web.controller;

import org.yecq.goleek.server.service.bean.param.TradeSettingSaveBean;
import org.yecq.goleek.server.service.bean.result.TradeSettingInfoBean;
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
public class TradeSettingControllerTest extends IntegrateBase {

    @Test
    public void test_do_getDefault() {
        List list = getJsonReturn("/trade_setting/get_default.go");
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
        TradeSettingInfoBean bean = getSingleObject(list, TradeSettingInfoBean.class);
        assertThat(bean.getId(), is("1"));
//        assertThat(bean.getOpen_percent(), closeTo(0.25, 0.001));
//        assertThat(bean.getLoss_percent(), closeTo(0.02, 0.001));
    }

    @Test
    public void test_do_saveDefault() {
        TradeSettingSaveBean bean = new TradeSettingSaveBean("1", 0.41, 0.05);
        List list = getJsonReturn("/trade_setting/save_default.go", bean);
        Head head = getHeader(list);
        assertThat(head.getStatus(), is("ok"));
    }
}
