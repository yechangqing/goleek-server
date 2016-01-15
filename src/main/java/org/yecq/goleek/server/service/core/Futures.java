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
public class Futures extends CoreTable {

    public Futures(Map<String, Object> hv) {
        super(hv);
    }

    public Futures(String id) {
        super(id);
    }

    public Futures(String code, boolean flag) {
        Map<String, Object> mp = new HashMap();
        mp.put("code", code);
        List<Map<String, Object>> list = Root.getInstance().getBean(CoreSelector.class).getListByAnd(mp, this);
        if (list.isEmpty()) {
            throw new IllegalStateException("合约不存在");
        }
        changeId(list.get(0).get("id") + "");
    }

    @Override
    public String add() {
        // 看是否存在
        Object code = getInfoOfHv("code");
        Map<String, Object> mp = new HashMap();
        mp.put("code", code);
        List<Map<String, Object>> list = Root.getInstance().getBean(CoreSelector.class).getListByAnd(mp, this);
        if (!list.isEmpty()) {
            throw new IllegalStateException("已存在该合约");
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

    public Futures cloneItself(String code) {
        if (code == null || code.trim().equals("")) {
            throw new IllegalArgumentException("参数为空");
        }
        String ori = getInfo("code") + "";
        ori = ori.substring(0, ori.length() - 4);
        String now = code.trim();
        now = now.substring(0, now.length() - 4);
        if (!now.equalsIgnoreCase(ori)) {
            throw new IllegalArgumentException("输入的合约名应当是" + ori);
        }
        Map map = new HashMap();
        Map<String, Object> infos = getInfo();
        map.put("code", code.trim());
        map.put("name", infos.get("name"));
        map.put("margin", infos.get("margin"));
        map.put("unit", infos.get("unit"));
        map.put("min", infos.get("min"));
        map.put("exchange", infos.get("exchange"));
        map.put("interest", "n");

        String id1 = new Futures(map).add();
        return new Futures(id1);
    }

    @Override
    public String getTable() {
        return "futures";
    }
}
