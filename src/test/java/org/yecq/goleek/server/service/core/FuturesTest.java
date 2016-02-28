package org.yecq.goleek.server.service.core;

import com.jhhc.baseframework.test.Base;
import com.jhhc.baseframework.web.core.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author yecq
 */
public class FuturesTest extends Base {

    @Test
    public void test_Futures() {
        this.expectedEx.expect(IllegalStateException.class);
        this.expectedEx.expectMessage("记录不存在");
        new Futures("23");
    }

    @Test
    public void test3_Futures() {
        this.expectedEx.expect(IllegalArgumentException.class);
        this.expectedEx.expectMessage("没有属性abcd");
        Map hv = new HashMap();
        hv.put("abcd", "22");
        new Futures(hv);
    }

    @Test
    public void test1_Futures() {
        this.expectedEx.expect(IllegalStateException.class);
        this.expectedEx.expectMessage("合约不存在");
        Futures f = new Futures("abcd", true);
    }

    @Test
    public void test2_Futures() {
        Futures f = new Futures("rb1601", true);
        assertThat(f.getId(), is("1"));
    }

    @Test
    public void test_getInfo() {
        Futures f = new Futures("3");
        Map<String, Object> map = f.getInfo();
        assertThat(map.get("id") + "", is("3"));
        assertThat(map.get("code") + "", is("ta1610"));
        assertThat(map.get("name") + "", is("甲酸"));
        assertThat(Double.parseDouble(map.get("margin") + ""), closeTo(0.12, 0.001));
        assertThat(Integer.parseInt(map.get("unit") + ""), is(5));
        assertThat(Double.parseDouble(map.get("min") + ""), closeTo(2, 0.1));
    }

    @Test
    public void test1_getInfo() {
        Futures f = new Futures("3");
        assertThat(f.getInfo("id") + "", is("3"));
        assertThat(f.getInfo("code") + "", is("ta1610"));
        assertThat(f.getInfo("name") + "", is("甲酸"));
        assertThat(Double.parseDouble(f.getInfo("margin") + ""), closeTo(0.12, 0.001));
        assertThat(Integer.parseInt(f.getInfo("unit") + ""), is(5));
        assertThat(Double.parseDouble(f.getInfo("min") + ""), closeTo(2, 0.1));
    }

    @Test
    public void test_add() {
        Map hv = new HashMap();
        hv.put("code", "m1601");
        hv.put("name", "豆粕");
        hv.put("margin", 0.09);
        hv.put("unit", 10);
        hv.put("min", 1);
        hv.put("exchange", "大连商品");
        Futures f = new Futures(hv);
        String id = f.add();
        assertThat(id, notNullValue());
    }

    @Test
    public void test1_add() {
        Map hv = new HashMap();
        hv.put("code", "rb1601");
        hv.put("name", "螺纹钢");
        hv.put("margin", 0.09);
        hv.put("unit", 10);
        hv.put("min", 1);
        hv.put("exchange", "上海期货");
        this.expectedEx.expect(IllegalStateException.class);
        this.expectedEx.expectMessage("已存在该合约");
        Futures f = new Futures(hv);
        String id = f.add();
    }

    @Test
    public void test_remove() {
        Futures f = new Futures("1");
        f.remove();
        String stmt = "select * from futures where id=1";
        List list = Root.getInstance().getSqlOperator().query(stmt);
        assertThat(list.isEmpty(), is(true));
    }

    @Test
    public void test_modify() {
        Map hv1 = new HashMap();
        hv1.put("margin", 0.15);
        hv1.put("min", 6);
        Futures f = new Futures("1");
        f.modify(hv1);
        assertThat(Double.parseDouble(f.getInfo("margin") + ""), closeTo(0.15, 0.001));
        assertThat(Double.parseDouble(f.getInfo("min") + ""), closeTo(6, 0.1));
    }

    @Test
    public void test_interest() {
        Futures f = new Futures("1");
        f.interest();
        assertThat(f.getInfo("interest") + "", is("y"));
    }

    @Test
    public void test1_interest() {
        this.expectedEx.expect(IllegalStateException.class);
        this.expectedEx.expectMessage("记录不存在");
        Futures f = new Futures("23");
        f.interest();
    }

    @Test
    public void test_unInterest() {
        Futures f = new Futures("2");
        f.unInterest();
        assertThat(f.getInfo("interest") + "", is("n"));
    }

    @Test
    public void test_cloneItself() {
        Futures f = new Futures("2");
        Futures f1 = f.cloneItself("y1701");
        assertThat(f1.getId(), notNullValue());
        assertThat(f1.getInfo("code") + "", is("y1701"));
    }

    @Test
    public void test1_cloneItself() {
        this.expectedEx.expect(IllegalArgumentException.class);
        this.expectedEx.expectMessage("输入的合约名应当是y");
        Futures f = new Futures("2");
        Futures f1 = f.cloneItself("m1701");
    }
}
