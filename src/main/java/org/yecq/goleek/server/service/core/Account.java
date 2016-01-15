package org.yecq.goleek.server.service.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.yecq.baseframework.plain.core.CoreSelector;
import org.yecq.baseframework.plain.core.CoreTable;
import org.yecq.baseframework.plain.core.Root;

/**
 *
 * @author yecq
 */
public class Account extends CoreTable {

    public Account(Map<String, Object> hv) {
        super(hv);
    }

    public Account(String id) {
        super(id);
    }

    @Override
    public String add() {
        // 先看是否存在
        Map<String, Object> tp = new HashMap();
        tp.put("code", getInfoOfHv("code"));
        List list = Root.getInstance().getBean(CoreSelector.class).getListByAnd(tp, this);
        if (!list.isEmpty()) {
            throw new IllegalStateException("已存在该账号");
        }
        return super.add();
    }

    public void use() {
        Map map = new HashMap();
        map.put("used", "y");
        super.modify(map);
    }

    public void unUse() {
        Map map = new HashMap();
        map.put("used", "n");
        super.modify(map);
    }

    @Override
    public String getTable() {
        return "account";
    }
}
