package org.yecq.goleek.server.web.controller;

import org.yecq.goleek.server.service.FuturesService;
import org.yecq.goleek.server.service.bean.param.FuturesAddBean;
import org.yecq.goleek.server.service.bean.param.FuturesCloneBean;
import org.yecq.goleek.server.service.bean.param.FuturesInterestBean;
import org.yecq.goleek.server.service.bean.param.FuturesModifyBean;
import org.yecq.goleek.server.service.bean.param.FuturesRemoveBean;
import org.yecq.goleek.server.service.bean.param.FuturesUninterestBean;
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
@RequestMapping("/futures/")
public class FuturesController extends ControllerBase {

    @Autowired
    private FuturesService fs;

    @RequestMapping("get_exchange_names.go")
    @ResponseBody
    public List do_getExchangeNames() {
        Sret sr = fs.getExchangeNames();
        return getRetList(sr);
    }

    @RequestMapping("get_list_all.go")
    @ResponseBody
    public List do_getListAll() {
        Sret sr = fs.getListAll();
        return getRetList(sr);
    }

    @RequestMapping("get_list_interested.go")
    @ResponseBody
    public List do_getListInterested() {
        Sret sr = fs.getListInterested();
        return getRetList(sr);
    }

    @RequestMapping("add.go")
    @ResponseBody
    public List do_add(@RequestParam("json") String json) {
        FuturesAddBean bean = new Gson().fromJson(json, FuturesAddBean.class);
        Sret sr = fs.add(bean);
        return getRetList(sr);
    }

    @RequestMapping("remove.go")
    @ResponseBody
    public List do_remove(@RequestParam("json") String json) {
        FuturesRemoveBean bean = new Gson().fromJson(json, FuturesRemoveBean.class);
        Sret sr = fs.remove(bean);
        return getRetList(sr);
    }

    @RequestMapping("clone.go")
    @ResponseBody
    public List do_cloneItself(@RequestParam("json") String json) {
        FuturesCloneBean bean = new Gson().fromJson(json, FuturesCloneBean.class);
        Sret sr = fs.cloneItself(bean);
        return getRetList(sr);
    }

    @RequestMapping("modify.go")
    @ResponseBody
    public List do_modify(@RequestParam("json") String json) {
        FuturesModifyBean bean = new Gson().fromJson(json, FuturesModifyBean.class);
        Sret sr = fs.modify(bean);
        return getRetList(sr);
    }

    @RequestMapping("interest.go")
    @ResponseBody
    public List do_interest(@RequestParam("json") String json) {
        FuturesInterestBean bean = new Gson().fromJson(json, FuturesInterestBean.class);
        Sret sr = fs.interest(bean);
        return getRetList(sr);
    }

    @RequestMapping("un_interest.go")
    @ResponseBody
    public List do_unInterest(@RequestParam("json") String json) {
        FuturesUninterestBean bean = new Gson().fromJson(json, FuturesUninterestBean.class);
        Sret sr = fs.unInterest(bean);
        return getRetList(sr);
    }

    // v1.1.sae增加
    @RequestMapping("interest_all.go")
    @ResponseBody
    public List do_interestAll() {
        Sret sr = fs.interestAll();
        return getRetList(sr);
    }

    // v1.1.sae增加���
    @RequestMapping("un_interest_all.go")
    @ResponseBody
    public List do_unInterestAll() {
        Sret sr = fs.unInterestAll();
        return getRetList(sr);
    }
}
