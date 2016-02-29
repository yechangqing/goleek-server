package org.yecq.goleek.server.service;

import com.jhhc.baseframework.web.service.Sret;
import org.yecq.goleek.server.service.bean.param.PositionStockCloseBean;
import org.yecq.goleek.server.service.bean.param.PositionStockEditBean;
import org.yecq.goleek.server.service.bean.param.PositionStockOpenBean;
import org.yecq.goleek.server.service.bean.result.PositionStockInfoBean;
import org.yecq.goleek.server.service.core.ObjectCreator;
import org.yecq.goleek.server.service.core.PositionStock;
import java.util.ArrayList;
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
public class PositionStockService {

    @Autowired
    private ObjectCreator objs;

    // 获取所有持仓
    public Sret getListAll() {
        List<PositionStock> list = objs.getPositionStockListAll();
        int len = list.size();
        List<PositionStockInfoBean> ret = new ArrayList(len);
        for (int i = 0; i < len; i++) {
            PositionStock p = list.get(i);
            Map<String, Object> map = p.getInfo();
            String id = map.get("id") + "";
            String code = map.get("code") + "";
            String name = map.get("name") + "";
            int lot = Integer.parseInt(map.get("lot") + "");
            double open_price = Double.parseDouble(map.get("open_price") + "");
            String action = map.get("action") + "";
            double quit_price = Double.parseDouble(map.get("quit_price") + "");
            String account = map.get("account") + "";
            String open_date = map.get("open_date") + "";
            String is_ready_close = p.isReady2Close() ? "y" : "n";
            ret.add(new PositionStockInfoBean(id, code, name, lot, open_price, action, quit_price, account, open_date, is_ready_close));
        }
        Sret sr = new Sret();
        sr.setOk();
        sr.setData(ret);
        return sr;
    }

    // 编辑退出条件
    public Sret editQuit(String id, PositionStockEditBean bean) {
        PositionStock p = new PositionStock(id);
        p.setActionAndQuitPrice(bean.getAction(), bean.getPrice());
        Sret sr = new Sret();
        sr.setOk();
        return sr;
    }

    // 开仓
    public Sret open(PositionStockOpenBean bean) {
        String code = bean.getCode();
        int lot = bean.getLot();
        double open_price = bean.getOpen_price();
        String open_date = bean.getOpen_date();
        double quit_price = bean.getQuit_price();
        String account = bean.getAccount();
        String id = new PositionStock().open(code, lot, open_price, open_date, quit_price, account);
        Sret sr = new Sret();
        sr.setOk();
        sr.setData(id);
        return sr;
    }

    // 平仓
    public Sret close(String id, PositionStockCloseBean bean) {
        int lot = bean.getLot();
        double price = bean.getPrice();
        String date = bean.getDate();
        new PositionStock(id).close(lot, price, date);
        Sret sr = new Sret();
        sr.setOk();
        return sr;
    }

    // 删除
    public Sret delete(String id) {
        new PositionStock(id).delete();
        Sret sr = new Sret();
        sr.setOk();
        return sr;
    }

    // 获取离场动作列表
    public Sret getActions() {
        String[] a = objs.getPositionStockActions();
        Sret sr = new Sret();
        sr.setOk();
        sr.setData(a);
        return sr;
    }
}
