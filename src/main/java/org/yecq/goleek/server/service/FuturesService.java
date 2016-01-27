package org.yecq.goleek.server.service;

import org.yecq.goleek.server.service.bean.param.FuturesAddBean;
import org.yecq.goleek.server.service.bean.param.FuturesCloneBean;
import org.yecq.goleek.server.service.bean.param.FuturesInterestBean;
import org.yecq.goleek.server.service.bean.param.FuturesModifyBean;
import org.yecq.goleek.server.service.bean.param.FuturesRemoveBean;
import org.yecq.goleek.server.service.bean.param.FuturesUninterestBean;
import org.yecq.goleek.server.service.bean.result.FuturesInfoBean;
import org.yecq.goleek.server.service.core.Futures;
import org.yecq.goleek.server.service.core.ObjectCreator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
public class FuturesService {

    @Autowired
    private ObjectCreator objs;

    // 获取所有交易所名称
    public Sret getExchangeNames() {
        String[] names = objs.getFuturesExchangeNames();
        Sret sr = new Sret();
        sr.setOk();
        sr.setData(names);
        return sr;
    }

    // 获取所有期货品种
    public Sret getListAll() {
        List<Futures> list = objs.getFuturesListAll();
        int len = list.size();
        List<FuturesInfoBean> ret = new ArrayList(len);
        for (int i = 0; i < len; i++) {
            Map map = list.get(i).getInfo();
            String id = map.get("id") + "";
            String code = map.get("code") + "";
            String name = map.get("name") + "";
            double margin = Double.parseDouble(map.get("margin") + "");
            int unit = Integer.parseInt(map.get("unit") + "");
            double min = Double.parseDouble(map.get("min") + "");
            String exchange = map.get("exchange") + "";
            String interested = map.get("interest") + "";
            ret.add(new FuturesInfoBean(id, code, name, margin, unit, min, exchange, interested));
        }
        Sret sr = new Sret();
        sr.setOk();
        sr.setData(ret);
        return sr;
    }

    // 获取感兴趣的品种列表
    public Sret getListInterested() {
        List<Futures> list = objs.getFuturesListInterested();
        int len = list.size();
        List<FuturesInfoBean> ret = new ArrayList(len);
        for (int i = 0; i < len; i++) {
            Map map = list.get(i).getInfo();
            String id = map.get("id") + "";
            String code = map.get("code") + "";
            String name = map.get("name") + "";
            double margin = Double.parseDouble(map.get("margin") + "");
            int unit = Integer.parseInt(map.get("unit") + "");
            double min = Double.parseDouble(map.get("min") + "");
            String exchange = map.get("exchange") + "";
            String interested = map.get("interest") + "";
            ret.add(new FuturesInfoBean(id, code, name, margin, unit, min, exchange, interested));
        }
        Sret sr = new Sret();
        sr.setOk();
        sr.setData(ret);
        return sr;
    }

    // 添加
    public Sret add(FuturesAddBean add) {
        Map hv = new HashMap();
        hv.put("code", add.getCode());
        hv.put("name", add.getName());
        hv.put("margin", add.getMargin());
        hv.put("unit", add.getUnit());
        hv.put("min", add.getMin());
        hv.put("exchange", add.getExchange());
        Futures f = new Futures(hv);
        String id = f.add();
        Sret sr = new Sret();
        sr.setOk();
        sr.setData(id);
        return sr;
    }

    // 删除
    public Sret remove(FuturesRemoveBean rem) {
        Futures f = new Futures(rem.getId());
        f.remove();
        Sret sr = new Sret();
        sr.setOk();
        return sr;
    }

    // 克隆
    public Sret cloneItself(FuturesCloneBean clo) {
        Futures f = new Futures(clo.getId());
        String id = f.cloneItself(clo.getNewCode()).getId();
        Sret sr = new Sret();
        sr.setOk();
        sr.setData(id);
        return sr;
    }

    // 修改
    public Sret modify(FuturesModifyBean modi) {
        Map hv = new HashMap();
        if (modi.isCode()) {
            hv.put("code", modi.getCode());
        }
        if (modi.isExchange()) {
            hv.put("exchange", modi.getExchange());
        }
        if (modi.isMargin()) {
            hv.put("margin", modi.getMargin());
        }
        if (modi.isMin()) {
            hv.put("min", modi.getMin());
        }
        if (modi.isName()) {
            hv.put("name", modi.getName());
        }
        if (modi.isUnit()) {
            hv.put("unit", modi.getUnit());
        }
        Futures f = new Futures(modi.getId());
        f.modify(hv);
        Sret sr = new Sret();
        sr.setOk();
        return sr;
    }

    // 关注
    public Sret interest(FuturesInterestBean bean) {
        Futures f = new Futures(bean.getId());
        f.interest();
        Sret sr = new Sret();
        sr.setOk();
        return sr;
    }

    // 取消关注
    public Sret unInterest(FuturesUninterestBean bean) {
        Futures f = new Futures(bean.getId());
        f.unInterest();
        Sret sr = new Sret();
        sr.setOk();
        return sr;
    }

    // v1.1.sae增加，选择所有的合约
    public Sret interestAll() {
        // 获得所有的合约
        List<Futures> list = objs.getFuturesListAll();
        Iterator<Futures> ite = list.iterator();
        while (ite.hasNext()) {
            ite.next().interest();
        }
        Sret sr = new Sret();
        sr.setOk();
        return sr;
    }

    // v1.1.sae增加，取消选择所有的合约
    public Sret unInterestAll() {
        // 获得所有的合约
        List<Futures> list = objs.getFuturesListAll();
        Iterator<Futures> ite = list.iterator();
        while (ite.hasNext()) {
            ite.next().unInterest();
        }
        Sret sr = new Sret();
        sr.setOk();
        return sr;
    }
}
