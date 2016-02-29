package org.yecq.goleek.server.web.controller;

import org.yecq.goleek.server.service.FuturesService;
import org.yecq.goleek.server.service.bean.param.FuturesAddBean;
import org.yecq.goleek.server.service.bean.param.FuturesCloneBean;
import org.yecq.goleek.server.service.bean.param.FuturesModifyBean;
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
public class FuturesController extends RestfulControllerBase {

    @Autowired
    private FuturesService fs;

    @RequestMapping(value = {"/futures_exchange_names"}, method = RequestMethod.GET)
    public Object do_getExchangeNames(HttpServletRequest request) {
        Sret sr = fs.getExchangeNames();
        return sr;
    }

    @RequestMapping(value = {"/futures"}, method = RequestMethod.GET)
    public Object do_getList(HttpServletRequest request) {
        Sret sr = fs.getListAll();
        return sr;
    }

    @RequestMapping(value = {"/futures_interested"}, method = RequestMethod.GET)
    public Object do_getListInterested(HttpServletRequest request) {
        Sret sr = fs.getListInterested();
        return sr;
    }

    @RequestMapping(value = {"/futures"}, method = RequestMethod.POST)
    public Object do_add(@RequestParam("json") String json, HttpServletRequest request) {
        FuturesAddBean bean = new Gson().fromJson(json, FuturesAddBean.class);
        Sret sr = fs.add(bean);
        return sr;
    }

    @RequestMapping(value = {"/futures/{id}"}, method = RequestMethod.DELETE)
    public Object do_remove(@PathVariable("id") String id, HttpServletRequest request) {
        Sret sr = fs.remove(id);
        return sr;
    }

    @RequestMapping(value = {"/futures/{id}"}, method = RequestMethod.POST)
    public Object do_cloneItself(@PathVariable("id") String id, @RequestParam("json") String json, HttpServletRequest request) {
        FuturesCloneBean bean = new Gson().fromJson(json, FuturesCloneBean.class);
        Sret sr = fs.cloneItself(id, bean);
        return sr;
    }

    @RequestMapping(value = {"/futures/{id}"}, method = RequestMethod.PUT)
    public Object do_modify(@PathVariable("id") String id, @RequestParam("json") String json, HttpServletRequest request) {
        FuturesModifyBean bean = new Gson().fromJson(json, FuturesModifyBean.class);
        Sret sr = fs.modify(id, bean);
        return sr;
    }

    // v1.1增加，选择所有和取消选择合约
    @RequestMapping(value = {"/futures_interest"}, method = RequestMethod.PUT)
    public Object do_interestAll(@RequestParam("action") String action, HttpServletRequest request) {
        if (action.equalsIgnoreCase("select")) {
            return fs.interestAll();
        } else if (action.equalsIgnoreCase("unselect")) {
            return fs.unInterestAll();
        } else {
            Sret sr = new Sret();
            sr.setError("错误的参数值action");
            return sr;
        }
    }
}
