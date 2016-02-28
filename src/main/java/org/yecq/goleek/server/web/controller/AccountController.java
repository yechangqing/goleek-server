package org.yecq.goleek.server.web.controller;

import com.google.gson.Gson;
import org.yecq.goleek.server.service.AccountService;
import com.jhhc.baseframework.web.controller.restful.RestfulControllerBase;
import com.jhhc.baseframework.web.service.Sret;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.yecq.goleek.server.service.bean.param.AccountAddBean;
import org.yecq.goleek.server.service.bean.param.AccountModifyBean;

/**
 *
 * @author yecq
 */
@RestController
public class AccountController extends RestfulControllerBase {

    @Autowired
    private AccountService as;

    // 获取所有期货账户
    @RequestMapping(value = {"/accounts"}, method = RequestMethod.GET)
    public Object do_getAccountList(@RequestParam("type") String type, HttpServletRequest request) {
        Sret sr = new Sret();
        if (type.equalsIgnoreCase("futures")) {
            sr = as.getAccountListFutures();
        } else if (type.equalsIgnoreCase("stock")) {
            sr = as.getAccountListStock();
        } else {
            sr.setError("错误的参数值type");
        }
        return sr;
    }

    // 获取所有已使用的期货账户
    @RequestMapping(value = {"/accounts_used"}, method = RequestMethod.GET)
    public Object do_getAccountListUsed(@RequestParam("type") String type, HttpServletRequest request) {
        // 也是有type
        Sret sr = new Sret();
        if (type.equalsIgnoreCase("futures")) {
            sr = as.getAccountListFuturesUsed();
        } else if (type.equalsIgnoreCase("stock")) {
            sr = as.getAccountListStockUsed();
        } else {
            sr.setError("错误的参数值type");
        }
        return sr;
    }

    // 获得所有的已使用的期货账户资金
    @RequestMapping(value = {"/moneys_futures"}, method = RequestMethod.GET)
    public Object do_getMoneyFutures(HttpServletRequest request) {
        Sret sr = as.getMoneyFutures();
        return sr;
    }

    // 获得所有的已使用的股票账户资金
    @RequestMapping(value = {"/moneys_stock"}, method = RequestMethod.GET)
    public Object do_getMoneyStock(HttpServletRequest request) {
        Sret sr = as.getMoneyStock();
        return sr;
    }

    // 增加账户
    @RequestMapping(value = {"/accounts"}, method = RequestMethod.POST)
    public Object do_add(@RequestParam("json") String json, HttpServletRequest request) {
        AccountAddBean bean = new Gson().fromJson(json, AccountAddBean.class);
        Sret sr = as.add(bean);
        return sr;
    }

    // 修改
    @RequestMapping(value = {"/accounts/{id}"}, method = RequestMethod.PUT)
    public Object do_modify(@PathVariable("id") String id, @RequestParam("json") String json, HttpServletRequest request) {
        AccountModifyBean bean = new Gson().fromJson(json, AccountModifyBean.class);
        Sret sr = as.modify(id, bean);
        return sr;
    }

    // 删除
    @RequestMapping(value = {"/accounts/{id}"}, method = RequestMethod.DELETE)
    public Object do_remove(@PathVariable("id") String id, HttpServletRequest request) {
        Sret sr = as.remove(id);
        return sr;
    }
}
