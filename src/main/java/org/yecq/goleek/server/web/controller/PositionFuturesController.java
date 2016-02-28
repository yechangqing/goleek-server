package org.yecq.goleek.server.web.controller;

import org.yecq.goleek.server.service.PositionFuturesService;
import org.yecq.goleek.server.service.bean.param.PositionFuturesActionsBean;
import org.yecq.goleek.server.service.bean.param.PositionFuturesCloseBean;
import org.yecq.goleek.server.service.bean.param.PositionFuturesDeleteBean;
import org.yecq.goleek.server.service.bean.param.PositionFuturesEditBean;
import org.yecq.goleek.server.service.bean.param.PositionFuturesOpenBean;
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
@RequestMapping("/position_futures/")
public class PositionFuturesController extends RestfulControllerBase {

    @Autowired
    private PositionFuturesService ps;

    // 获取所有持仓
    @RequestMapping("get_list_all.go")
    public Object do_getListAll(HttpServletRequest request) {
        Sret sr = ps.getListAll();
        return sr;
    }

    // 编辑退出条件
    @RequestMapping("edit_quit.go")
    public Object do_editQuit(HttpServletRequest request) {
        PositionFuturesEditBean bean = new Gson().fromJson(request.getParameter("json"), PositionFuturesEditBean.class);
        Sret sr = ps.editQuit(bean);
        return sr;
    }

    // 开仓
    @RequestMapping("open.go")
    public Object do_open(HttpServletRequest request) {
        PositionFuturesOpenBean bean = new Gson().fromJson(request.getParameter("json"), PositionFuturesOpenBean.class);
        Sret sr = ps.open(bean);
        return sr;
    }

    // 平仓
    @RequestMapping("close.go")
    public Object do_close(HttpServletRequest request) {
        PositionFuturesCloseBean bean = new Gson().fromJson(request.getParameter("json"), PositionFuturesCloseBean.class);
        Sret sr = ps.close(bean);
        return sr;
    }

    // 删除
    @RequestMapping("delete.go")
    public Object do_delete(HttpServletRequest request) {
        PositionFuturesDeleteBean bean = new Gson().fromJson(request.getParameter("json"), PositionFuturesDeleteBean.class);
        Sret sr = ps.delete(bean);
        return sr;
    }

    // 获取离场动作列表
    @RequestMapping("get_actions.go")
    public Object do_getActions(HttpServletRequest request) {
        PositionFuturesActionsBean bean = new Gson().fromJson(request.getParameter("json"), PositionFuturesActionsBean.class);
        Sret sr = ps.getActions(bean);
        return sr;
    }
}
