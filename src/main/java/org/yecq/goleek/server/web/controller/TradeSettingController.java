package org.yecq.goleek.server.web.controller;

import org.yecq.goleek.server.service.TradeSettingService;
import org.yecq.goleek.server.service.bean.param.TradeSettingSaveBean;
import com.google.gson.Gson;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yecq.baseframework.plain.service.Sret;
import org.yecq.baseframework.web.ControllerBase;

/**
 *
 * @author yecq
 */
@Controller
@RequestMapping("/trade_setting/")
public class TradeSettingController extends ControllerBase {

    @Autowired
    private TradeSettingService ts;

    // 获取默认配置
    @RequestMapping("get_default.go")
    @ResponseBody
    public List do_getDefault() {
        Sret sr = ts.getDefault();
        return getRetList(sr);
    }

    // 保存配置
    @RequestMapping("save_default.go")
    @ResponseBody
    public List do_saveDefault(@RequestParam("json") String json) {
        TradeSettingSaveBean modi = new Gson().fromJson(json, TradeSettingSaveBean.class);
        Sret sr = ts.saveDefault(modi);
        if (sr.isOk()) {
            sr.setOk("保存成功");
        }
        return getRetList(sr);
    }
}
