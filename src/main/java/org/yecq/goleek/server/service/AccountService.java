package org.yecq.goleek.server.service;

import org.yecq.goleek.server.service.bean.param.AccountAddBean;
import org.yecq.goleek.server.service.bean.param.AccountModifyBean;
import org.yecq.goleek.server.service.bean.param.AccountRemoveBean;
import org.yecq.goleek.server.service.bean.param.AccountUnuseBean;
import org.yecq.goleek.server.service.bean.param.AccountUseBean;
import org.yecq.goleek.server.service.bean.result.AccountFuturesInfoBean;
import org.yecq.goleek.server.service.bean.result.AccountStockInfoBean;
import org.yecq.goleek.server.service.core.Account;
import org.yecq.goleek.server.service.core.ObjectCreator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yecq.baseframework.plain.service.Sret;

/**
 *
 * @author yecq
 */
@Service
@Transactional
public class AccountService {

    @Autowired
    private ObjectCreator objs;

    // 获得所有的期货账户
    public Sret getAccountListFutures() {
        List<Account> list = objs.getAccountListFutures();
        int len = list.size();
        List<AccountFuturesInfoBean> ret = new ArrayList(len);
        for (int i = 0; i < len; i++) {
            Map<String, Object> map = list.get(i).getInfo();
            String id = map.get("id") + "";
            String code = map.get("code") + "";
            String company = map.get("company") + "";
            double money = Double.parseDouble(map.get("money") + "");
            String used = map.get("used") + "";
            ret.add(new AccountFuturesInfoBean(id, code, company, money, used));
        }
        Sret sr = new Sret();
        sr.setOk();
        sr.setData(ret);
        return sr;
    }

    // 获得所有使用的期货账户
    public Sret getAccountListFuturesUsed() {
        List<Account> list = objs.getAccountListFuturesUsed();
        int len = list.size();
        List<AccountFuturesInfoBean> ret = new ArrayList(len);
        for (int i = 0; i < len; i++) {
            Map<String, Object> map = list.get(i).getInfo();
            String id = map.get("id") + "";
            String code = map.get("code") + "";
            String company = map.get("company") + "";
            double money = Double.parseDouble(map.get("money") + "");
            String used = map.get("used") + "";
            ret.add(new AccountFuturesInfoBean(id, code, company, money, used));
        }
        Sret sr = new Sret();
        sr.setOk();
        sr.setData(ret);
        return sr;
    }

    // 获得所有的已使用的期货账户资金
    public Sret getMoneyFutures() {
        List<Account> list = objs.getAccountListFuturesUsed();
        int len = list.size();
        double ret = 0.;
        for (int i = 0; i < len; i++) {
            Account acc = list.get(i);
            ret += Double.parseDouble(acc.getInfo("money") + "");
        }
        Sret sr = new Sret();
        sr.setOk();
        sr.setData(ret);
        return sr;
    }

    // 增加账户
    public Sret add(AccountAddBean add) {
        Map hv = new HashMap();
        hv.put("code", add.getCode());
        hv.put("company", add.getCompany());
        hv.put("money", add.getMoney());
        hv.put("type", add.getType());
        Account acc = new Account(hv);
        String id = acc.add();
        Sret sr = new Sret();
        sr.setOk();
        sr.setData(id);
        return sr;
    }

    // 修改
    public Sret modify(AccountModifyBean modi) {
        Map hv1 = new HashMap();
        if (modi.isCode()) {
            hv1.put("code", modi.getCode());
        }
        if (modi.isCompany()) {
            hv1.put("company", modi.getCompany());
        }
        if (modi.isMoney()) {
            hv1.put("money", modi.getMoney());
        }
        if (modi.isUsed()) {
            hv1.put("used", modi.getUsed());
        }
        Account acc = new Account(modi.getId());
        acc.modify(hv1);
        Sret sr = new Sret();
        sr.setOk();
        return sr;
    }

    // 删除
    public Sret remove(AccountRemoveBean rem) {
        Account acc = new Account(rem.getId());
        acc.remove();
        Sret sr = new Sret();
        sr.setOk();
        return sr;
    }

    // 标记使用
    public Sret use(AccountUseBean use) {
        Account acc = new Account(use.getId());
        acc.use();
        Sret sr = new Sret();
        sr.setOk();
        return sr;
    }

    // 标记取消使用
    public Sret unUse(AccountUnuseBean use) {
        Account acc = new Account(use.getId());
        acc.unUse();
        Sret sr = new Sret();
        sr.setOk();
        return sr;
    }

    // 获得所有的股票账户
    public Sret getAccountListStock() {
        List<Account> list = objs.getAccountListStock();
        int len = list.size();
        List<AccountStockInfoBean> ret = new ArrayList(len);
        for (int i = 0; i < len; i++) {
            Map<String, Object> map = list.get(i).getInfo();
            String id = map.get("id") + "";
            String code = map.get("code") + "";
            String company = map.get("company") + "";
            double money = Double.parseDouble(map.get("money") + "");
            String used = map.get("used") + "";
            ret.add(new AccountStockInfoBean(id, code, company, money, used));
        }
        Sret sr = new Sret();
        sr.setOk();
        sr.setData(ret);
        return sr;
    }

    // 获得所有已使用的股票账户
    public Sret getAccountListStockUsed() {
        List<Account> list = objs.getAccountListStockUsed();
        int len = list.size();
        List<AccountStockInfoBean> ret = new ArrayList(len);
        for (int i = 0; i < len; i++) {
            Map<String, Object> map = list.get(i).getInfo();
            String id = map.get("id") + "";
            String code = map.get("code") + "";
            String company = map.get("company") + "";
            double money = Double.parseDouble(map.get("money") + "");
            String used = map.get("used") + "";
            ret.add(new AccountStockInfoBean(id, code, company, money, used));
        }
        Sret sr = new Sret();
        sr.setOk();
        sr.setData(ret);
        return sr;
    }

    // 获得所有的已使用的股票账户资金
    public Sret getMoneyStock() {
        List<Account> list = objs.getAccountListStockUsed();
        int len = list.size();
        double ret = 0.;
        for (int i = 0; i < len; i++) {
            Account acc = list.get(i);
            ret += Double.parseDouble(acc.getInfo("money") + "");
        }
        Sret sr = new Sret();
        sr.setOk();
        sr.setData(ret);
        return sr;
    }
}
