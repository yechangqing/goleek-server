package org.yecq.goleek.server.web.controller;

import org.yecq.goleek.server.service.StockService;
import org.yecq.goleek.server.service.bean.param.StockAddBean;
import org.yecq.goleek.server.service.bean.param.StockModifyBean;
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
public class StockController extends RestfulControllerBase {

    @Autowired
    private StockService ss;

    @RequestMapping(value = {"/stock_exchange_names"}, method = RequestMethod.GET)
    public Object do_getExchangeNames(HttpServletRequest request) {
        Sret sr = ss.getExchangeNames();
        return sr;
    }

    @RequestMapping(value = {"/stocks"}, method = RequestMethod.GET)
    public Object do_getListAll(HttpServletRequest request) {
        Sret sr = ss.getListAll();
        return sr;
    }

    @RequestMapping(value = {"/stocks_interested"}, method = RequestMethod.GET)
    public Object do_getListInterested(HttpServletRequest request) {
        Sret sr = ss.getListInterested();
        return sr;
    }

    @RequestMapping(value = {"/stocks"}, method = RequestMethod.POST)
    public Object do_add(@RequestParam("json") String json, HttpServletRequest request) {
        StockAddBean bean = new Gson().fromJson(json, StockAddBean.class);
        Sret sr = ss.add(bean);
        return sr;
    }

    @RequestMapping(value = {"/stocks/{id}"}, method = RequestMethod.DELETE)
    public Object do_remove(@PathVariable("id") String id, HttpServletRequest request) {
        Sret sr = ss.remove(id);
        return sr;
    }

    @RequestMapping(value = {"/stocks/{id}"}, method = RequestMethod.PUT)
    public Object do_modify(@PathVariable("id") String id, @RequestParam("json") String json, HttpServletRequest request) {
        StockModifyBean bean = new Gson().fromJson(json, StockModifyBean.class);
        Sret sr = ss.modify(id, bean);
        return sr;
    }

    // v1.1增加，选择和取消选择所有合约
    @RequestMapping(value = {"/stocks_interest"}, method = RequestMethod.PUT)
    public Object do_interestAll(@RequestParam("action") String action, HttpServletRequest request) {
        if (action.equalsIgnoreCase("select")) {
            return ss.interestAll();
        } else if (action.equalsIgnoreCase("unselect")) {
            return ss.unInterestAll();
        } else {
            Sret sr = new Sret();
            sr.setError("错误的参数值action");
            return sr;
        }
    }
}
