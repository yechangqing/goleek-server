package org.yecq.goleek.server.service.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.yecq.baseframework.plain.core.CoreTable;
import org.yecq.baseframework.plain.core.Root;
import org.yecq.record.Record;
import org.yecq.record.SqlOperator;

/**
 *
 * @author yecq
 */
public class PositionStock extends CoreTable {

    public PositionStock(Map hv) {
        super(hv);
    }

    public PositionStock(String id) {
        super(id);
    }

    public PositionStock() {
        super();
    }

    // 根据股票，账户，判断是否有持仓，没有返回null
    public String exist() {
        Object code = getOriginHv().get("code");
        Object account = getOriginHv().get("account");
        if (code == null || account == null) {
            throw new IllegalArgumentException("需要合约名、方向、账户");
        }
        String stmt = "select id from " + SaeViewSubstitute.v_position_stock + " where code=? and account=?";
        List<Map<String, Object>> list = Root.getInstance().getSqlOperator().query(stmt, new Object[]{code, account});
        return list.isEmpty() ? null : list.get(0).get("id") + "";
    }

    public String open(String code, int lot, double open_price, String open_date, double quit_price, String account) {
        if (lot <= 0) {
            throw new IllegalArgumentException("交易手数需>0");
        }
        // 先看是否存在此持仓
        Map hv = new HashMap();
        hv.put("code", code);
        hv.put("account", account);
        setOriginHv(hv);
        String id1 = exist();
        if (id1 == null) {
            hv.clear();
            hv.put("action", "卖出 <=");
            hv.put("quit_price", quit_price);
            changeHv(hv);
            id1 = add();
        }

        // 交易记录写入detail
        SqlOperator sql = Root.getInstance().getSqlOperator();
        String stmt = "insert into detail_stock (code,name,open_price,open_date,account) values ";
        String tmp = "";
        Object[] args = new Object[lot * 5];
        for (int i = 1, j = 0; i <= lot; i++) {
            tmp += "(?, (select name from stock where code=?),?,?,?),";
            args[j++] = code;
            args[j++] = code;
            args[j++] = open_price;
            args[j++] = open_date;
            args[j++] = account;
        }
        tmp = tmp.substring(0, tmp.length() - 1);
        stmt += tmp;
        String[] dids = sql.insert(stmt, args);

        // 建立position_detail_stock
        stmt = "insert into position_detail_stock(position_stock_id,detail_stock_id) values ";
        tmp = "";
        args = new Object[2 * dids.length];
        for (int i = 0, j = 0; i < dids.length; i++) {
            tmp += "(?,?),";
            args[j++] = id1;
            args[j++] = dids[i];
        }
        tmp = tmp.substring(0, tmp.length() - 1);
        stmt += tmp;
        sql.insert(stmt, args);

        return id1;
    }

    public void close(int lot, double price, String date) {
        if (lot == 0) {
            return;
        }

        SqlOperator sql = Root.getInstance().getSqlOperator();

        // 选出需要平仓的detail的id
        String stmt = "select id from detail_stock "
                + "where id in (select detail_stock_id from position_detail_stock where position_stock_id=?) "
                + "and status='持' "
                + "order by id "
                + "limit ?";
        List<Map<String, Object>> list = sql.query(stmt, new Object[]{getId(), lot});

        // 写入交易记录
        stmt = "update detail_stock set status='平', close_price=?, close_date=? where ";
        String tmp = "";
        Object[] args = new Object[list.size() + 2];
        int j = 0;
        args[j++] = price;
        args[j++] = date;
        for (int i = 0; i < args.length - 2; i++) {
            tmp += " id=? or ";
            args[j++] = list.get(i).get("id");
        }
        tmp = tmp.substring(0, tmp.length() - 4);
        stmt += tmp;
        sql.update(stmt, args);
        if (!this.hasOpeningDetails()) {
            remove();
        }
    }

    public void delete() {
        String[] ids = this.getDetails();

        String stmt = "";
        Object[] arg = new Object[ids.length];
        for (int i = 0; i < ids.length; i++) {
            stmt += "id=? or ";
            arg[i] = ids[i];
        }
        if (arg.length > 0) {
            stmt = stmt.substring(0, stmt.length() - 4);
            stmt = "delete from detail_stock where " + stmt;
            Root.getInstance().getSqlOperator().delete(stmt, arg);
        }
        remove();
    }

    // 返回所有的detail
    String[] getDetails() {
        String stmt = "select id from detail_stock "
                + "where id in (select detail_stock_id from position_detail_stock where position_stock_id = ?)";
        List<Map<String, Object>> list = Root.getInstance().getSqlOperator().query(stmt, new Object[]{getId()});
        String[] ret = new String[list.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = list.get(i).get("id") + "";
        }
        return ret;
    }

    // 返回还未平仓的detail
    String[] getOpeningDetails() {
        String stmt = "select id from detail_stock "
                + "where id in (select detail_stock_id from position_detail_stock where position_stock_id = ?) "
                + "and status='持' order by id";
        List<Map<String, Object>> list = Root.getInstance().getSqlOperator().query(stmt, new Object[]{getId()});
        String[] ret = new String[list.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = list.get(i).get("id") + "";
        }
        return ret;
    }

    // 判断是否还有未平仓的detail
    public boolean hasOpeningDetails() {
        return getOpeningDetails().length != 0;
    }

    public void setActionAndQuitPrice(String action, double price) {
        Map hv1 = new HashMap();
        hv1.put("action", action);
        hv1.put("quit_price", price);
        Record rec = Root.getInstance().getRecord();
        rec.init1("position_stock", getId());
        rec.modify(hv1);
    }

    // 是否到达了平仓日
    public boolean isReady2Close() {
        // 获得开仓日期
        String open_date = getInfo("open_date") + "";
        String today = Util.getTodayStr();
        return do_isReady2Close(open_date, today);
    }

    boolean do_isReady2Close(String open_date, String today) {
        String ready = Util.getDateAfter(open_date, 3);
        return !(Util.compareDate(ready, today) > 0);
    }

    @Override
    public String getTable() {
        return "position_stock";
    }

    @Override
    public Map<String, Object> getInfo() {
        // 返回视图
        String stmt = "select * from " + SaeViewSubstitute.v_position_stock + " where id = ?";
        List<Map<String, Object>> list = Root.getInstance().getSqlOperator().query(stmt, new Object[]{getId()});

        return list.get(0);
    }

    @Override
    public Object getInfo(String key) {
        if (key == null || key.trim().equals("")) {
            throw new IllegalArgumentException("key值为null");
        }
        return getInfo().get(key);
    }

    @Override
    protected void setOriginHv(Map<String, Object> hv1) {
        super.setOriginHv(hv1);
    }
}
