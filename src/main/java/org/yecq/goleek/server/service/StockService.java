package org.yecq.goleek.server.service;

import com.jhhc.baseframework.web.service.Sret;
import org.yecq.goleek.server.service.bean.param.StockAddBean;
import org.yecq.goleek.server.service.bean.param.StockInterestBean;
import org.yecq.goleek.server.service.bean.param.StockModifyBean;
import org.yecq.goleek.server.service.bean.param.StockRemoveBean;
import org.yecq.goleek.server.service.bean.param.StockUninterestBean;
import org.yecq.goleek.server.service.bean.result.StockInfoBean;
import org.yecq.goleek.server.service.core.ObjectCreator;
import org.yecq.goleek.server.service.core.Stock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author yecq
 */
@Service
@Transactional
public class StockService {

    @Autowired
    private ObjectCreator objs;

    // 获取所有交易所名称
    public Sret getExchangeNames() {
        String[] names = objs.getStockExchangeNames();
        Sret sr = new Sret();
        sr.setOk();
        sr.setData(names);
        return sr;
    }

    // 获取所有股票
    public Sret getListAll() {
        List<Stock> list = objs.getStockListAll();
        int len = list.size();
        List<StockInfoBean> ret = new ArrayList(len);
        for (int i = 0; i < len; i++) {
            Map<String, Object> map = list.get(i).getInfo();
            String id = map.get("id") + "";
            String code = map.get("code") + "";
            String name = map.get("name") + "";
            String exchange = map.get("exchange") + "";
            String interest = map.get("interest") + "";
            ret.add(new StockInfoBean(id, code, name, exchange, interest));
        }
        Sret sr = new Sret();
        sr.setOk();
        sr.setData(ret);
        return sr;
    }

    // 获取感兴趣的股票
    public Sret getListInterested() {
        List<Stock> list = objs.getStockListInterested();
        int len = list.size();
        List<StockInfoBean> ret = new ArrayList(len);
        for (int i = 0; i < len; i++) {
            Map<String, Object> map = list.get(i).getInfo();
            String id = map.get("id") + "";
            String code = map.get("code") + "";
            String name = map.get("name") + "";
            String exchange = map.get("exchange") + "";
            String interest = map.get("interest") + "";
            ret.add(new StockInfoBean(id, code, name, exchange, interest));
        }
        Sret sr = new Sret();
        sr.setOk();
        sr.setData(ret);
        return sr;
    }

    // 添加
    public Sret add(StockAddBean bean) {
        Map hv = new HashMap();
        hv.put("code", bean.getCode());
        hv.put("name", bean.getName());
        hv.put("exchange", bean.getExchange());
        Stock st = new Stock(hv);
        String id = st.add();
        Sret sr = new Sret();
        sr.setOk();
        sr.setData(id);
        return sr;
    }

    // 删除
    public Sret remove(StockRemoveBean bean) {
        Stock st = new Stock(bean.getId());
        st.remove();
        Sret sr = new Sret();
        sr.setOk();
        return sr;
    }

    // 修改
    public Sret modify(StockModifyBean bean) {
        Map hv = new HashMap();
        if (bean.isCode()) {
            hv.put("code", bean.getCode());
        }
        if (bean.isName()) {
            hv.put("name", bean.getName());
        }
        if (bean.isExchange()) {
            hv.put("exchange", bean.getExchange());
        }
        if (bean.isInterest()) {
            hv.put("interest", bean.getInterest());
        }
        Stock st = new Stock(bean.getId());
        st.modify(hv);
        Sret sr = new Sret();
        sr.setOk();
        return sr;
    }

    // 感兴趣
    public Sret interest(StockInterestBean bean) {
        Stock st = new Stock(bean.getId());
        st.interest();
        Sret sr = new Sret();
        sr.setOk();
        return sr;
    }

    // 取消感兴趣
    public Sret unInterest(StockUninterestBean bean) {
        Stock st = new Stock(bean.getId());
        st.unInterest();
        Sret sr = new Sret();
        sr.setOk();
        return sr;
    }

    // v1.1增加，选择所有的合约
    public Sret interestAll() {
        // 获得所有的合约
        List<Stock> list = objs.getStockListAll();
        Iterator<Stock> ite = list.iterator();
        while (ite.hasNext()) {
            ite.next().interest();
        }
        Sret sr = new Sret();
        sr.setOk();
        return sr;
    }

    // v1.1增加，取消选择所有的合约
    public Sret unInterestAll() {
        // 获得所有的合约
        List<Stock> list = objs.getStockListAll();
        Iterator<Stock> ite = list.iterator();
        while (ite.hasNext()) {
            ite.next().unInterest();
        }
        Sret sr = new Sret();
        sr.setOk();
        return sr;
    }
}
