package org.yecq.goleek.server.web.controller;

import org.yecq.goleek.server.service.AccountService;
import org.yecq.goleek.server.service.bean.param.AccountAddBean;
import org.yecq.goleek.server.service.bean.param.AccountModifyBean;
import org.yecq.goleek.server.service.bean.param.AccountRemoveBean;
import org.yecq.goleek.server.service.bean.param.AccountUnuseBean;
import org.yecq.goleek.server.service.bean.param.AccountUseBean;
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
@RequestMapping("/account/")
public class AccountController extends ControllerBase {

    @Autowired
    private AccountService as;

    // 获取所有期货账户
    @RequestMapping("get_list_futures.go")
    @ResponseBody
    public List do_getAccountListFutures() {
        Sret sr = as.getAccountListFutures();
        return getRetList(sr);
    }

    // 获取所有已使用的期货账户
    @RequestMapping("get_list_futures_used.go")
    @ResponseBody
    public List do_getAccountListFuturesUsed() {
        Sret sr = as.getAccountListFuturesUsed();
        return getRetList(sr);
    }

    // 获得所有的已使用的期货账户资金
    @RequestMapping("get_money_futures.go")
    @ResponseBody
    public List do_getMoneyFutures() {
        Sret sr = as.getMoneyFutures();
        return getRetList(sr);
    }

    // 增加账户
    @RequestMapping("add.go")
    @ResponseBody
    public List do_add(@RequestParam("json") String json) {
        AccountAddBean bean = new Gson().fromJson(json, AccountAddBean.class);
        Sret sr = as.add(bean);
        return getRetList(sr);
    }

    // 修改
    @RequestMapping("modify.go")
    @ResponseBody
    public List do_modify(@RequestParam("json") String json) {
        AccountModifyBean bean = new Gson().fromJson(json, AccountModifyBean.class);
        Sret sr = as.modify(bean);
        return getRetList(sr);
    }

    // 删除
    @RequestMapping("remove.go")
    @ResponseBody
    public List do_remove(@RequestParam("json") String json) {
        AccountRemoveBean bean = new Gson().fromJson(json, AccountRemoveBean.class);
        Sret sr = as.remove(bean);
        return getRetList(sr);
    }

    // 标记使用
    @RequestMapping("use.go")
    @ResponseBody
    public List do_use(@RequestParam("json") String json) {
        AccountUseBean bean = new Gson().fromJson(json, AccountUseBean.class);
        Sret sr = as.use(bean);
        return getRetList(sr);
    }

    // 标记取消使用
    @RequestMapping("un_use.go")
    @ResponseBody
    public List do_unUse(@RequestParam("json") String json) {
        AccountUnuseBean bean = new Gson().fromJson(json, AccountUnuseBean.class);
        Sret sr = as.unUse(bean);
        return getRetList(sr);
    }

    // 获得所有的股票账户
    @RequestMapping("get_list_stock.go")
    @ResponseBody
    public List do_getAccountListStock() {
        Sret sr = as.getAccountListStock();
        return getRetList(sr);
    }

    // 获得所有已使用的股票账户
    @RequestMapping("get_list_stock_used.go")
    @ResponseBody
    public List do_getAccountListStockUsed() {
        Sret sr = as.getAccountListStockUsed();
        return getRetList(sr);
    }

    // 获得所有的已使用的股票账户资金
    @RequestMapping("get_money_stock.go")
    @ResponseBody
    public List do_getMoneyStock() {
        Sret sr = as.getMoneyStock();
        return getRetList(sr);
    }
}
