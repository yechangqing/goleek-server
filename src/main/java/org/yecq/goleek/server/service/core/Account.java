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
