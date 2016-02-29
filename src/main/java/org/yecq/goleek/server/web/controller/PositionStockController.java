package org.yecq.goleek.server.web.controller;

import org.yecq.goleek.server.service.PositionStockService;
import org.yecq.goleek.server.service.bean.param.PositionStockCloseBean;
import org.yecq.goleek.server.service.bean.param.PositionStockEditBean;
import org.yecq.goleek.server.service.bean.param.PositionStockOpenBean;
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
public class PositionStockController extends RestfulControllerBase {

    @Autowired
    private PositionStockService ps;

    @RequestMapping(value = {"/position_stocks"}, method = RequestMethod.GET)
    public Object do_getListAll(HttpServletRequest request) {
        Sret sr = ps.getListAll();
        return sr;
    }

    @RequestMapping(value = {"/position_stocks/{id}"}, method = RequestMethod.PUT)
    public Object do_modify(@PathVariable("id") String id, @RequestParam("type") String type, @RequestParam("json") String json, HttpServletRequest request) {
        if (type.equalsIgnoreCase("edit")) {
            PositionStockEditBean bean = new Gson().fromJson(json, PositionStockEditBean.class);
            Sret sr = ps.editQuit(id, bean);
            return sr;
        } else if (type.equalsIgnoreCase("close")) {
            PositionStockCloseBean bean = new Gson().fromJson(json, PositionStockCloseBean.class);
            Sret sr = ps.close(id, bean);
            return sr;
        } else {
            Sret sr = new Sret();
            sr.setError("错误的type值");
            return sr;
        }
    }

    @RequestMapping(value = {"/position_stocks"}, method = RequestMethod.POST)
    public Object do_open(@RequestParam("json") String json, HttpServletRequest request) {
        PositionStockOpenBean bean = new Gson().fromJson(json, PositionStockOpenBean.class);
        Sret sr = ps.open(bean);
        return sr;
    }

    @RequestMapping(value = {"/position_stocks/{id}"}, method = RequestMethod.DELETE)
    public Object do_delete(@PathVariable("id") String id, HttpServletRequest request) {
        Sret sr = ps.delete(id);
        return sr;
    }

    @RequestMapping(value = {"/position_stocks_actions"}, method = RequestMethod.GET)
    public Object do_getActions() {
        Sret sr = ps.getActions();
        return sr;
    }
}
