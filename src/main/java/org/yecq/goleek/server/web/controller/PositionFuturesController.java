package org.yecq.goleek.server.web.controller;

import org.yecq.goleek.server.service.PositionFuturesService;
import org.yecq.goleek.server.service.bean.param.PositionFuturesActionsBean;
import org.yecq.goleek.server.service.bean.param.PositionFuturesCloseBean;
import org.yecq.goleek.server.service.bean.param.PositionFuturesDeleteBean;
import org.yecq.goleek.server.service.bean.param.PositionFuturesEditBean;
import org.yecq.goleek.server.service.bean.param.PositionFuturesOpenBean;
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
@RequestMapping("/position_futures/")
public class PositionFuturesController extends ControllerBase {

    @Autowired
    private PositionFuturesService ps;

    // 获取所有持仓
    @RequestMapping("get_list_all.go")
    @ResponseBody
    public List do_getListAll() {
        Sret sr = ps.getListAll();
        return getRetList(sr);
    }

    // 编辑退出条件
    @RequestMapping("edit_quit.go")
    @ResponseBody
    public List do_editQuit(@RequestParam("json") String json) {
        PositionFuturesEditBean bean = new Gson().fromJson(json, PositionFuturesEditBean.class);
        Sret sr = ps.editQuit(bean);
        return getRetList(sr);
    }

    // 开仓
    @RequestMapping("open.go")
    @ResponseBody
    public List do_open(@RequestParam("json") String json) {
        PositionFuturesOpenBean bean = new Gson().fromJson(json, PositionFuturesOpenBean.class);
        Sret sr = ps.open(bean);
        return getRetList(sr);
    }

    // 平仓
    @RequestMapping("close.go")
    @ResponseBody
    public List do_close(@RequestParam("json") String json) {
        PositionFuturesCloseBean bean = new Gson().fromJson(json, PositionFuturesCloseBean.class);
        Sret sr = ps.close(bean);
        return getRetList(sr);
    }

    // 删除
    @RequestMapping("delete.go")
    @ResponseBody
    public List do_delete(@RequestParam("json") String json) {
        PositionFuturesDeleteBean bean = new Gson().fromJson(json, PositionFuturesDeleteBean.class);
        Sret sr = ps.delete(bean);
        return getRetList(sr);
    }

    // 获取离场动作列表
    @RequestMapping("get_actions.go")
    @ResponseBody
    public List do_getActions(@RequestParam("json") String json) {
        PositionFuturesActionsBean bean = new Gson().fromJson(json, PositionFuturesActionsBean.class);
        Sret sr = ps.getActions(bean);
        return getRetList(sr);
    }
}
