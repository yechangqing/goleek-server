package org.yecq.goleek.server.web.controller;

import org.yecq.goleek.server.service.StockService;
import org.yecq.goleek.server.service.bean.param.StockAddBean;
import org.yecq.goleek.server.service.bean.param.StockInterestBean;
import org.yecq.goleek.server.service.bean.param.StockModifyBean;
import org.yecq.goleek.server.service.bean.param.StockRemoveBean;
import org.yecq.goleek.server.service.bean.param.StockUninterestBean;
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
@RequestMapping("/stock/")
public class StockController extends ControllerBase {

    @Autowired
    private StockService ss;

    @RequestMapping("get_exchange_names.go")
    @ResponseBody
    public List do_getExchangeNames() {
        Sret sr = ss.getExchangeNames();
        return getRetList(sr);
    }

    @RequestMapping("get_list_all.go")
    @ResponseBody
    public List do_getListAll() {
        Sret sr = ss.getListAll();
        return getRetList(sr);
    }

    @RequestMapping("get_list_interested.go")
    @ResponseBody
    public List do_getListInterested() {
        Sret sr = ss.getListInterested();
        return getRetList(sr);
    }

    @RequestMapping("add.go")
    @ResponseBody
    public List do_add(@RequestParam("json") String json) {
        StockAddBean bean = new Gson().fromJson(json, StockAddBean.class);
        Sret sr = ss.add(bean);
        return getRetList(sr);
    }

    @RequestMapping("remove.go")
    @ResponseBody
    public List do_remove(@RequestParam("json") String json) {
        StockRemoveBean bean = new Gson().fromJson(json, StockRemoveBean.class);
        Sret sr = ss.remove(bean);
        return getRetList(sr);
    }

    @RequestMapping("modify.go")
    @ResponseBody
    public List do_modify(@RequestParam("json") String json) {
        StockModifyBean bean = new Gson().fromJson(json, StockModifyBean.class);
        Sret sr = ss.modify(bean);
        return getRetList(sr);
    }

    @RequestMapping("interest.go")
    @ResponseBody
    public List do_interest(@RequestParam("json") String json) {
        StockInterestBean bean = new Gson().fromJson(json, StockInterestBean.class);
        Sret sr = ss.interest(bean);
        return getRetList(sr);
    }

    @RequestMapping("un_interest.go")
    @ResponseBody
    public List do_unInterest(@RequestParam("json") String json) {
        StockUninterestBean bean = new Gson().fromJson(json, StockUninterestBean.class);
        Sret sr = ss.unInterest(bean);
        return getRetList(sr);
    }

    // v1.1增加，选择所有合约
    @RequestMapping("interest_all.go")
    @ResponseBody
    public List do_interestAll() {
        Sret sr = ss.interestAll();
        return getRetList(sr);
    }

    // v1.1增加，取消所有
    @RequestMapping("un_interest_all.go")
    @ResponseBody
    public List do_unInterestAll() {
        Sret sr = ss.unInterestAll();
        return getRetList(sr);
    }
}
