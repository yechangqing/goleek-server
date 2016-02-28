package org.yecq.goleek.server.web.controller;

import org.yecq.goleek.server.service.PositionStockService;
import org.yecq.goleek.server.service.bean.param.PositionStockCloseBean;
import org.yecq.goleek.server.service.bean.param.PositionStockDeleteBean;
import org.yecq.goleek.server.service.bean.param.PositionStockEditBean;
import org.yecq.goleek.server.service.bean.param.PositionStockOpenBean;
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
@RequestMapping("/position_stock/")
public class PositionStockController extends RestfulControllerBase {

    @Autowired
    private PositionStockService ps;

    @RequestMapping("get_list_all.go")
    public Object do_getListAll(HttpServletRequest request) {
        Sret sr = ps.getListAll();
        return sr;
    }

    @RequestMapping("edit_quit.go")
    public Object do_editQuit(HttpServletRequest request) {
        PositionStockEditBean bean = new Gson().fromJson(request.getParameter("json"), PositionStockEditBean.class);
        Sret sr = ps.editQuit(bean);
        return sr;
    }

    @RequestMapping("open.go")
    public Object do_open(HttpServletRequest request) {
        PositionStockOpenBean bean = new Gson().fromJson(request.getParameter("json"), PositionStockOpenBean.class);
        Sret sr = ps.open(bean);
        return sr;
    }

    @RequestMapping("close.go")
    public Object do_close(HttpServletRequest request) {
        PositionStockCloseBean bean = new Gson().fromJson(request.getParameter("json"), PositionStockCloseBean.class);
        Sret sr = ps.close(bean);
        return sr;
    }

    @RequestMapping("delete.go")
    public Object do_delete(HttpServletRequest request) {
        PositionStockDeleteBean bean = new Gson().fromJson(request.getParameter("json"), PositionStockDeleteBean.class);
        Sret sr = ps.delete(bean);
        return sr;
    }

    @RequestMapping("get_actions.go")
    public Object do_getActions() {
        Sret sr = ps.getActions();
        return sr;
    }
}
