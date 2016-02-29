package org.yecq.goleek.server.service;

import com.jhhc.baseframework.test.Base;
import com.jhhc.baseframework.web.service.Sret;
import org.yecq.goleek.server.service.bean.param.TradeSettingSaveBean;
import org.yecq.goleek.server.service.bean.result.TradeSettingInfoBean;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author yecq
 */
public class TradeSettingServiceTest extends Base {

    @Autowired
    private TradeSettingService ts;

    @Test
    public void test_getDefault() {
        Sret sr = ts.getDefault();
        assertThat(sr.isOk(), is(true));
        TradeSettingInfoBean bean = (TradeSettingInfoBean) sr.getData();
        assertThat(bean.getId(), is("1"));
//        assertThat(bean.getOpen_percent(), closeTo(0.25, 0.001));
//        assertThat(bean.getLoss_percent(), closeTo(0.02, 0.001));
    }

    @Test
    public void test_saveDefault() {
        TradeSettingSaveBean modi = new TradeSettingSaveBean(0.5, 0.03);
        Sret sr = ts.saveDefault(modi);
        assertThat(sr.isOk(), is(true));
    }
}
