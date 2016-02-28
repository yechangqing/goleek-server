package org.yecq.goleek.server.web.controller;

import org.yecq.goleek.server.service.TradeSettingService;
import org.yecq.goleek.server.service.bean.param.TradeSettingSaveBean;
import com.google.gson.Gson;
import com.jhhc.baseframework.web.controller.restful.RestfulControllerBase;
import com.jhhc.baseframework.web.service.Sret;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author yecq
 */
@RestController
@RequestMapping("/trade_setting/")
public class TradeSettingController extends RestfulControllerBase {

    @Autowired
    private TradeSettingService ts;

    // 获取默认配置
    @RequestMapping("get_default.go")
    public Object do_getDefault(HttpServletRequest request) {
        Sret sr = ts.getDefault();
        return sr;
    }

    // 保存配置
    @RequestMapping("save_default.go")
    public Object do_saveDefault(HttpServletRequest request) {
        TradeSettingSaveBean modi = new Gson().fromJson(request.getParameter("json"), TradeSettingSaveBean.class);
        Sret sr = ts.saveDefault(modi);
        if (sr.isOk()) {
            sr.setOk("保存成功");
        }
        return sr;
    }
}
