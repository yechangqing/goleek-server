package org.yecq.goleek.server.service.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.yecq.baseframework.plain.core.Root;
import org.yecq.baseframework.test.Base;

/**
 *
 * @author yecq
 */
public class StockTest extends Base {

    @Test
    public void test_Stock() {
        this.expectedEx.expect(IllegalStateException.class);
        this.expectedEx.expectMessage("记录不存在");
        new Stock("17");
    }

    @Test
    public void test1_Stock() {
        Stock st = new Stock("600399", true);
        assertThat(st.getId(), is("1"));

        Stock st1 = new Stock("深圳光电", true, true);
        assertThat(st1.getId(), is("2"));
    }

    @Test
    public void test2_Stock() {
        this.expectedEx.expect(IllegalStateException.class);
        this.expectedEx.expectMessage("不存在此股票");
        Stock st = new Stock("600122", true);
    }

    @Test
    public void test3_Stock() {
        this.expectedEx.expect(IllegalStateException.class);
        this.expectedEx.expectMessage("不存在此股票");
        Stock st = new Stock("浦发银行", true, true);
    }

    @Test
    public void test4_Stock() {
        this.expectedEx.expect(IllegalArgumentException.class);
        this.expectedEx.expectMessage("没有属性abcd");
        Map hv = new HashMap();
        hv.put("abcd", "22");
        new Stock(hv);
    }

    @Test
    public void test_getInfo() {
        Stock st = new Stock("1");
        Map<String, Object> map = st.getInfo();
        assertThat(map.get("code") + "", is("600399"));
        assertThat(map.get("name") + "", is("广晟有色"));
        assertThat(map.get("exchange") + "", is("上海证券"));
        assertThat(map.get("interest") + "", is("y"));
    }

    @Test
    public void test1_getInfo() {
        Stock st = new Stock("1");
        assertThat(st.getInfo("code") + "", is("600399"));
        assertThat(st.getInfo("name") + "", is("广晟有色"));
        assertThat(st.getInfo("exchange") + "", is("上海证券"));
        assertThat(st.getInfo("interest") + "", is("y"));
    }

    @Test
    public void test_add() {
        Map hv = new HashMap();
        hv.put("code", "000321");
        hv.put("name", "浦发银行");
        hv.put("exchange", "深圳证券");
        String id = new Stock(hv).add();
        assertThat(id, notNullValue());
    }

    @Test
    public void test1_add() {
        Map hv = new HashMap();
        hv.put("code", "600399");
        hv.put("name", "广晟有色");
        hv.put("exchange", "上海证券");
        this.expectedEx.expect(IllegalStateException.class);
        this.expectedEx.expectMessage("已存在该股票");
        String id = new Stock(hv).add();
    }

    @Test
    public void test_remove() {
        String stmt = "select * from stock where id=1";
        List list = Root.getInstance().getSqlOperator().query(stmt);
        assertThat(list.size(), is(1));
        Stock st = new Stock("1");
        st.remove();
        stmt = "select * from stock where id=1";
        list = Root.getInstance().getSqlOperator().query(stmt);
        assertThat(list.size(), is(0));
    }

    @Test
    public void test_modify() {
        Map hv1 = new HashMap();
        hv1.put("code", "000000");
        hv1.put("name", "中国建筑");
        Stock st = new Stock("2");
        st.modify(hv1);
        assertThat(st.getInfo("code") + "", is("000000"));
        assertThat(st.getInfo("name") + "", is("中国建筑"));
    }

    @Test
    public void test_interest() {
        Stock st = new Stock("2");
        assertThat(st.getInfo("interest") + "", is("n"));
        st.interest();
        assertThat(st.getInfo("interest") + "", is("y"));
    }

    @Test
    public void test_unInterest() {
        Stock st = new Stock("1");
        assertThat(st.getInfo("interest") + "", is("y"));
        st.unInterest();
        assertThat(st.getInfo("interest") + "", is("n"));
    }
}
