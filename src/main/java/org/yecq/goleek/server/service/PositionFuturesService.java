package org.yecq.goleek.server.service;

import org.yecq.goleek.server.service.bean.param.PositionFuturesActionsBean;
import org.yecq.goleek.server.service.bean.param.PositionFuturesCloseBean;
import org.yecq.goleek.server.service.bean.param.PositionFuturesDeleteBean;
import org.yecq.goleek.server.service.bean.param.PositionFuturesEditBean;
import org.yecq.goleek.server.service.bean.param.PositionFuturesOpenBean;
import org.yecq.goleek.server.service.bean.result.PositionFuturesInfoBean;
import org.yecq.goleek.server.service.core.ObjectCreator;
import org.yecq.goleek.server.service.core.PositionFutures;
import java.util.ArrayList;
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
public class PositionFuturesService {

    @Autowired
    private ObjectCreator objs;

    // 获取所有持仓
    public Sret getListAll() {
        List<PositionFutures> list = objs.getPositionFuturesListAll();
        int len = list.size();
        List<PositionFuturesInfoBean> ret = new ArrayList(len);
        for (int i = 0; i < len; i++) {
            PositionFutures pf = list.get(i);
            Map<String, Object> map = pf.getInfo();
            String id = map.get("id") + "";
            String contract = map.get("contract") + "";
            String direct = map.get("direct") + "";
            int lot = Integer.parseInt(map.get("lot") + "");
            double open_price = Double.parseDouble(map.get("open_price") + "");
            String action = map.get("action") + "";
            double quit_price = Double.parseDouble(map.get("quit_price") + "");
            String account = map.get("account") + "";
            String open_date = map.get("open_date") + "";
            String is_ready_close = pf.isReady2Close() ? "y" : "n";
            ret.add(new PositionFuturesInfoBean(id, contract, direct, lot, open_price, action, quit_price, account, open_date, is_ready_close));
        }
        Sret sr = new Sret();
        sr.setOk();
        sr.setData(ret);
        return sr;
    }

    // 编辑退出条件
    public Sret editQuit(PositionFuturesEditBean bean) {
        PositionFutures p = new PositionFutures(bean.getId());
        p.setActionAndQuitPrice(bean.getAction(), bean.getPrice());
        Sret sr = new Sret();
        sr.setOk();
        return sr;
    }

    // 开仓
    public Sret open(PositionFuturesOpenBean bean) {
        PositionFutures p = new PositionFutures();
        String contract = bean.getContract();
        String direct = bean.getDirect();
        int lot = bean.getLot();
        double open_price = bean.getOpen_price();
        String open_date = bean.getOpen_date();
        double quit_price = bean.getQuit_price();
        String account = bean.getAccount();
        String id = p.open(contract, direct, lot, open_price, open_date, quit_price, account);
        Sret sr = new Sret();
        sr.setOk();
        sr.setData(id);
        return sr;
    }

    // 平仓
    public Sret close(PositionFuturesCloseBean bean) {
        int lot = bean.getLot();
        double close_price = bean.getPrice();
        String close_date = bean.getDate();
        PositionFutures p = new PositionFutures(bean.getId());
        p.close(lot, close_price, close_date);
        Sret sr = new Sret();
        sr.setOk();
        return sr;
    }

    // 删除
    public Sret delete(PositionFuturesDeleteBean bean) {
        PositionFutures p = new PositionFutures(bean.getId());
        p.delete();
        Sret sr = new Sret();
        sr.setOk();
        return sr;
    }

    // 获取离场动作列表
    public Sret getActions(PositionFuturesActionsBean bean) {
        String[] acts = objs.getPositionFuturesActions(bean.getDirect());
        Sret sr = new Sret();
        sr.setOk();
        sr.setData(acts);
        return sr;
    }
}
