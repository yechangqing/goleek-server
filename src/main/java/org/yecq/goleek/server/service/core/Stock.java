package org.yecq.goleek.server.service.core;

import com.jhhc.baseframework.web.core.CoreSelector;
import com.jhhc.baseframework.web.core.CoreTable;
import com.jhhc.baseframework.web.core.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author yecq
 */
public class Stock extends CoreTable {

    public Stock(Map hv) {
        super(hv);
    }

    public Stock(String id) {
        super(id);
    }

    public Stock(String code, boolean flag) {
        Map mp = new HashMap();
        mp.put("code", code);
        List<Map<String, Object>> list = Root.getInstance().getBean(CoreSelector.class).getListByAnd(mp, this);
        if (list.isEmpty()) {
            throw new IllegalStateException("不存在此股票");
        }
        String id = list.get(0).get("id") + "";
        changeId(id);
    }

    public Stock(String name, boolean flag1, boolean flag2) {
        Map mp = new HashMap();
        mp.put("name", name);
        List<Map<String, Object>> list = Root.getInstance().getBean(CoreSelector.class).getListByAnd(mp, this);
        if (list.isEmpty()) {
            throw new IllegalStateException("不存在此股票");
        }
        String id = list.get(0).get("id") + "";
        changeId(id);
    }

    @Override
    public String add() {
        // 检查是否存在该股票
        Object code = getInfoOfHv("code");
        List list = Root.getInstance().getBean(CoreSelector.class).getList("code=?", new Object[]{code}, this);
        if (!list.isEmpty()) {
            throw new IllegalStateException("已存在该股票");
        }
        return super.add();
    }

    public void interest() {
        Map map = new HashMap();
        map.put("interest", "y");
        super.modify(map);
    }

    public void unInterest() {
        Map map = new HashMap();
        map.put("interest", "n");
        super.modify(map);
    }

    @Override
    public String getTable() {
        return "stock";
    }
}
