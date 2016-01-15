package org.yecq.goleek.server.web.controller;

import org.yecq.goleek.server.service.PositionStockService;
import org.yecq.goleek.server.service.bean.param.PositionStockCloseBean;
import org.yecq.goleek.server.service.bean.param.PositionStockDeleteBean;
import org.yecq.goleek.server.service.bean.param.PositionStockEditBean;
import org.yecq.goleek.server.service.bean.param.PositionStockOpenBean;
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
@RequestMapping("/position_stock/")
public class PositionStockController extends ControllerBase {

    @Autowired
    private PositionStockService ps;

    @RequestMapping("get_list_all.go")
    @ResponseBody
    public List do_getListAll() {
        Sret sr = ps.getListAll();
        return getRetList(sr);
    }

    @RequestMapping("edit_quit.go")
    @ResponseBody
    public List do_editQuit(@RequestParam("json") String json) {
        PositionStockEditBean bean = new Gson().fromJson(json, PositionStockEditBean.class);
        Sret sr = ps.editQuit(bean);
        return getRetList(sr);
    }

    @RequestMapping("open.go")
    @ResponseBody
    public List do_open(@RequestParam("json") String json) {
        PositionStockOpenBean bean = new Gson().fromJson(json, PositionStockOpenBean.class);
        Sret sr = ps.open(bean);
        return getRetList(sr);
    }

    @RequestMapping("close.go")
    @ResponseBody
    public List do_close(@RequestParam("json") String json) {
        PositionStockCloseBean bean = new Gson().fromJson(json, PositionStockCloseBean.class);
        Sret sr = ps.close(bean);
        return getRetList(sr);
    }

    @RequestMapping("delete.go")
    @ResponseBody
    public List do_delete(@RequestParam("json") String json) {
        PositionStockDeleteBean bean = new Gson().fromJson(json, PositionStockDeleteBean.class);
        Sret sr = ps.delete(bean);
        return getRetList(sr);
    }

    @RequestMapping("get_actions.go")
    @ResponseBody
    public List do_getActions() {
        Sret sr = ps.getActions();
        return getRetList(sr);
    }
}
