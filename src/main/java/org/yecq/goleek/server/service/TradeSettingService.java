package org.yecq.goleek.server.service;

import com.jhhc.baseframework.web.service.Sret;
import org.yecq.goleek.server.service.bean.param.TradeSettingSaveBean;
import org.yecq.goleek.server.service.bean.result.TradeSettingInfoBean;
import org.yecq.goleek.server.service.core.Setting;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author yecq
 */
@Component
@Transactional
public class TradeSettingService {

    @Autowired
    private Setting setting;

    // 获取默认配置
    public Sret getDefault() {
        String id = setting.getId();
        double open_percent = Double.parseDouble(setting.getInfo("open_percent") + "");
        double loss_percent = Double.parseDouble(setting.getInfo("loss_percent") + "");
        TradeSettingInfoBean bean = new TradeSettingInfoBean(id, open_percent, loss_percent);
        Sret sr = new Sret();
        sr.setOk();
        sr.setData(bean);
        return sr;
    }

    // 保存配置
    public Sret saveDefault(TradeSettingSaveBean modi) {
        Map hv1 = new HashMap();
        if (modi.isOpen_percent()) {
            hv1.put("open_percent", modi.getOpen_percent());
        }
        if (modi.isLoss_percent()) {
            hv1.put("loss_percent", modi.getLoss_percent());
        }
        setting.modify(hv1);
        Sret sr = new Sret();
        sr.setOk();
        return sr;
    }
}
