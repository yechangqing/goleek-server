package org.yecq.goleek.server.web.controller;

import org.yecq.goleek.server.service.StockService;
import org.yecq.goleek.server.service.bean.param.StockAddBean;
import org.yecq.goleek.server.service.bean.param.StockInterestBean;
import org.yecq.goleek.server.service.bean.param.StockModifyBean;
import org.yecq.goleek.server.service.bean.param.StockRemoveBean;
import org.yecq.goleek.server.service.bean.param.StockUninterestBean;
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
@RequestMapping("/stock/")
public class StockController extends RestfulControllerBase {

    @Autowired
    private StockService ss;

    @RequestMapping("get_exchange_names.go")
    public Object do_getExchangeNames(HttpServletRequest request) {
        Sret sr = ss.getExchangeNames();
        return sr;
    }

    @RequestMapping("get_list_all.go")
    public Object do_getListAll(HttpServletRequest request) {
        Sret sr = ss.getListAll();
        return sr;
    }

    @RequestMapping("get_list_interested.go")
    public Object do_getListInterested(HttpServletRequest request) {
        Sret sr = ss.getListInterested();
        return sr;
    }

    @RequestMapping("add.go")
    public Object do_add(HttpServletRequest request) {
        StockAddBean bean = new Gson().fromJson(request.getParameter("json"), StockAddBean.class);
        Sret sr = ss.add(bean);
        return sr;
    }

    @RequestMapping("remove.go")
    public Object do_remove(HttpServletRequest request) {
        StockRemoveBean bean = new Gson().fromJson(request.getParameter("json"), StockRemoveBean.class);
        Sret sr = ss.remove(bean);
        return sr;
    }

    @RequestMapping("modify.go")
    public Object do_modify(HttpServletRequest request) {
        StockModifyBean bean = new Gson().fromJson(request.getParameter("json"), StockModifyBean.class);
        Sret sr = ss.modify(bean);
        return sr;
    }

    @RequestMapping("interest.go")
    public Object do_interest(HttpServletRequest request) {
        StockInterestBean bean = new Gson().fromJson(request.getParameter("json"), StockInterestBean.class);
        Sret sr = ss.interest(bean);
        return sr;
    }

    @RequestMapping("un_interest.go")
    public Object do_unInterest(HttpServletRequest request) {
        StockUninterestBean bean = new Gson().fromJson(request.getParameter("json"), StockUninterestBean.class);
        Sret sr = ss.unInterest(bean);
        return sr;
    }

    // v1.1增加，选择所有合约
    @RequestMapping("interest_all.go")
    public Object do_interestAll(HttpServletRequest request) {
        Sret sr = ss.interestAll();
        return sr;
    }

    // v1.1增加，取消所有
    @RequestMapping("un_interest_all.go")
    public Object do_unInterestAll(HttpServletRequest request) {
        Sret sr = ss.unInterestAll();
        return sr;
    }
}
