package org.yecq.goleek.server.service.core;

import com.jhhc.baseframework.record.Record;
import com.jhhc.baseframework.record.SqlOperator;
import com.jhhc.baseframework.web.core.CoreSelector;
import com.jhhc.baseframework.web.core.CoreView;
import com.jhhc.baseframework.web.core.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author yecq
 */
public class PositionFutures extends CoreView {

    public PositionFutures(String id) {
        super(id);
    }

    public PositionFutures(Map<String, Object> hv) {
        super(hv);
    }

    public PositionFutures() {
        super();
    }

    // 根据合约，方向，账户，判断是否有持仓，没有返回null
    public String exist() {
        Object contract = getInfoOfHv("contract");
        Object direct = getInfoOfHv("direct");
        Object account = getInfoOfHv("account");
        if (contract == null || direct == null || account == null) {
            throw new IllegalArgumentException("需要合约名、方向、账户");
        }
//        String stmt = "select id from v_position_futures where contract=? and direct=? and account=?";
//        List<Map<String, Object>> list = Root.getInstance().getSqlOperator().query(stmt, new Object[]{contract, direct, account});      
        List<Map<String, Object>> list = Root.getInstance().getBean(CoreSelector.class).getList("contract=? and direct=? and account=?", new Object[]{contract, direct, account}, this);
        return list.isEmpty() ? null : list.get(0).get("id") + "";
    }

    public String open(String contract, String direct, int lot, double open_price, String open_date, double quit_price, String account) {
        if (lot <= 0) {
            throw new IllegalArgumentException("交易手数需>0");
        }
        // 先看是否存在此持仓
        Map hv = new HashMap();
        hv.put("contract", contract);
        hv.put("direct", direct);
        hv.put("account", account);
        changeHv(hv);
        String id1 = exist();
        if (id1 == null) {
            hv.clear();
            if (direct.equals("多")) {
                hv.put("action", "卖出平仓 <=");
            } else {
                hv.put("action", "买入平仓 >=");
            }
            hv.put("quit_price", quit_price);
            changeHv(hv);
            id1 = add();
        }

        // 交易记录写入detail
        SqlOperator sql = Root.getInstance().getSqlOperator();
        String stmt = "insert into detail_futures (contract,name,direct,open_price,open_date,margin,unit,account) values ";
        String tmp = "";
        Object[] args = new Object[lot * 8];
        for (int i = 1, j = 0; i <= lot; i++) {
            tmp += "(?, "
                    + "(select name from futures where code=?), "
                    + "?,?,?, "
                    + "(select margin from futures where code=?), "
                    + "(select unit from futures where code=?), "
                    + "?),";
            args[j++] = contract;
            args[j++] = contract;
            args[j++] = direct;
            args[j++] = open_price;
            args[j++] = open_date;
            args[j++] = contract;
            args[j++] = contract;
            args[j++] = account;
        }
        tmp = tmp.substring(0, tmp.length() - 1);
        stmt += tmp;
        String[] dids = sql.insert(stmt, args);

        // 建立position_detail_futures
        stmt = "insert into position_detail_futures(position_futures_id,detail_futures_id) values ";
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

    // 判断是否还有未平仓的detail
    public boolean hasOpeningDetails() {
        return getOpeningDetails().length != 0;
    }

    public void close(int lot, double close_price, String close_date) {
        if (lot == 0) {
            return;
        }

        SqlOperator sql = Root.getInstance().getSqlOperator();

        // 选出需要平仓的detail的id
        String stmt = "select id from detail_futures "
                + "where id in (select detail_futures_id from position_detail_futures where position_futures_id=?) "
                + "and status='持' "
                + "order by id "
                + "limit ?";
        List<Map<String, Object>> list = sql.query(stmt, new Object[]{getId(), lot});

        // 写入交易记录
        stmt = "update detail_futures set status='平', close_price=?, close_date=? where ";
        String tmp = "";
        Object[] args = new Object[list.size() + 2];
        int j = 0;
        args[j++] = close_price;
        args[j++] = close_date;
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

    // 删除仓位，顺带连detail也删除
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
            stmt = "delete from detail_futures where " + stmt;
            Root.getInstance().getSqlOperator().delete(stmt, arg);
        }
        remove();
    }

    // 返回所有的detail
    String[] getDetails() {
        String stmt = "select id from detail_futures "
                + "where id in (select detail_futures_id from position_detail_futures where position_futures_id=?)";
        List<Map<String, Object>> list = Root.getInstance().getSqlOperator().query(stmt, new Object[]{getId()});
        String[] ret = new String[list.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = list.get(i).get("id") + "";
        }
        return ret;
    }

    // 返回还未平仓的detail
    String[] getOpeningDetails() {
        String stmt = "select id from detail_futures "
                + "where id in (select detail_futures_id from position_detail_futures where position_futures_id=?) "
                + "and status='持' "
                + "order by id";
        List<Map<String, Object>> list = Root.getInstance().getSqlOperator().query(stmt, new Object[]{getId()});
        String[] ret = new String[list.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = list.get(i).get("id") + "";
        }
        return ret;
    }

    public void setActionAndQuitPrice(String action, double price) {
        Map hv1 = new HashMap();
        hv1.put("action", action);
        hv1.put("quit_price", price);
        Record rec = Root.getInstance().getRecord();
        rec.init1("position_futures", getId());
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
    public String getView() {
        return "v_position_futures";
    }

    @Override
    public String getMainTable() {
        return "position_futures.id";
    }

    @Override
    public String[] getSlaveTables() {
        return new String[]{"position_detail_futures", "detail_futures"};
    }
}
