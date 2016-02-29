package org.yecq.goleek.server.web.controller;

import org.yecq.goleek.server.service.PositionFuturesService;
import org.yecq.goleek.server.service.bean.param.PositionFuturesActionsBean;
import org.yecq.goleek.server.service.bean.param.PositionFuturesCloseBean;
import org.yecq.goleek.server.service.bean.param.PositionFuturesEditBean;
import org.yecq.goleek.server.service.bean.param.PositionFuturesOpenBean;
import com.google.gson.Gson;
import com.jhhc.baseframework.web.controller.restful.RestfulControllerBase;
import com.jhhc.baseframework.web.service.Sret;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author yecq
 */
@RestController
public class PositionFuturesController extends RestfulControllerBase {

    @Autowired
    private PositionFuturesService ps;

    // 获取所有持仓
    @RequestMapping(value = {"/position_futures"}, method = RequestMethod.GET)
    public Object do_getListAll(HttpServletRequest request) {
        Sret sr = ps.getListAll();
        return sr;
    }

    // 编辑退出条件或者平仓
    @RequestMapping(value = {"/position_futures/{id}"}, method = RequestMethod.PUT)
    public Object do_modify(@PathVariable("id") String id, @RequestParam("type") String type, @RequestParam("json") String json, HttpServletRequest request) {
        if (type.equalsIgnoreCase("edit")) {
            PositionFuturesEditBean bean = new Gson().fromJson(json, PositionFuturesEditBean.class);
            Sret sr = ps.editQuit(id, bean);
            return sr;
        } else if (type.equalsIgnoreCase("close")) {
            PositionFuturesCloseBean bean = new Gson().fromJson(json, PositionFuturesCloseBean.class);
            Sret sr = ps.close(id, bean);
            return sr;
        } else {
            Sret sr = new Sret();
            sr.setError("错误的type值");
            return sr;
        }

    }

    // 开仓
    @RequestMapping(value = {"/position_futures"}, method = RequestMethod.POST)
    public Object do_open(@RequestParam("json") String json, HttpServletRequest request) {
        PositionFuturesOpenBean bean = new Gson().fromJson(json, PositionFuturesOpenBean.class);
        Sret sr = ps.open(bean);
        return sr;
    }

    // 删除
    @RequestMapping(value = {"/position_futures/{id}"}, method = RequestMethod.DELETE)
    public Object do_delete(@PathVariable("id") String id, HttpServletRequest request) {
        Sret sr = ps.delete(id);
        return sr;
    }

    // 获取离场动作列表
    @RequestMapping(value = {"/position_futures_actions"}, method = RequestMethod.GET)
    public Object do_getActions(@RequestParam("json") String json, HttpServletRequest request) {
        PositionFuturesActionsBean bean = new Gson().fromJson(json, PositionFuturesActionsBean.class);
        Sret sr = ps.getActions(bean);
        return sr;
    }
}
