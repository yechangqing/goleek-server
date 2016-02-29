package org.yecq.goleek.server.web.controller;

import com.google.gson.Gson;
import com.jhhc.baseframework.test.IntegrateRestfulBase;
import com.jhhc.baseframework.test.TestReturn;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.yecq.goleek.server.service.bean.param.TradeSettingSaveBean;
import org.yecq.goleek.server.service.bean.result.TradeSettingInfoBean;

/**
 *
 * @author yecq
 */
public class TradeSettingControllerTest extends IntegrateRestfulBase {

    @Test
    public void test_do_getDefault() {
        TestReturn ret = doGet("/trade_settings/default");
        assertThat(ret.getStatus(), is("ok"));
        TradeSettingInfoBean set = ret.getObject(TradeSettingInfoBean.class);
        assertThat(set.getId(), is("1"));
    }

    @Test
    public void test_do_saveDefault() {
        TradeSettingSaveBean bean = new TradeSettingSaveBean();
        bean.setOpen_percent(0.41);
        bean.setLoss_percent(0.05);
        Map map = new HashMap();
        map.put("json", new Gson().toJson(bean));
        TestReturn ret = doPut("/trade_settings/default", map);
        assertThat(ret.getStatus(), is("ok"));
    }
}
