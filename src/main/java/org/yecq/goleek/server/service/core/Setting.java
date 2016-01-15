package org.yecq.goleek.server.service.core;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.yecq.baseframework.plain.core.CoreSelector;
import org.yecq.baseframework.plain.core.CoreTable;
import org.yecq.baseframework.plain.core.Root;

/**
 *
 * @author yecq
 */
@Component
@Scope("singleton")
public class Setting extends CoreTable {

    @Autowired
    public Setting(Root root) {
        // 读取id=1的默认配置
        List<Map<String, Object>> list = root.getBean(CoreSelector.class).getList("id=?", new Object[]{"1"}, this);
        if (list.isEmpty()) {
            Root.getInstance().getSqlOperator().insert("insert into setting(id,open_percent,loss_percent) values (?,?,?)", new Object[]{"1", 0.25, 0.02});
            list = root.getBean(CoreSelector.class).getList("id=?", new Object[]{"1"}, this);
        }
        Map<String, Object> map = list.get(0);
        String id = map.get("id") + "";
        changeId(id);
    }

    public Setting(Map<String, Object> hv) {
        super(hv);
    }

    public Setting(String id) {
        super(id);
    }

    @Override
    public String getTable() {
        return "setting";
    }
}
