package org.yecq.goleek.server.service.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.yecq.baseframework.plain.core.Root;
import org.yecq.baseframework.test.Base;

/**
 *
 * @author yecq
 */
public class SettingTest extends Base {

    @Autowired
    private Setting set;

    @Test
    public void test_Setting() {
        this.expectedEx.expect(IllegalStateException.class);
        this.expectedEx.expectMessage("记录不存在");
        new Setting("45");
    }

    @Test
    public void test1_Setting() {
//        Setting set = new Setting();
        assertThat(set.getId(), is("1"));
        assertThat(Double.parseDouble(set.getInfo("open_percent") + ""), closeTo(0.25, 0.001));
        assertThat(Double.parseDouble(set.getInfo("loss_percent") + ""), closeTo(0.02, 0.001));
    }

    @Test
    public void test2_Setting() {
        this.expectedEx.expect(IllegalArgumentException.class);
        this.expectedEx.expectMessage("没有属性abcd");
        Map hv = new HashMap();
        hv.put("abcd", "22");
        new Setting(hv);
    }

    @Test
    public void test_getInfo() {
        Setting set = new Setting("1");
        Map<String, Object> map = set.getInfo();
        assertThat(Double.parseDouble(map.get("open_percent") + ""), closeTo(0.25, 0.001));
        assertThat(Double.parseDouble(map.get("loss_percent") + ""), closeTo(0.02, 0.001));
    }

    @Test
    public void test1_getInfo() {
        Setting set = new Setting("1");
        assertThat(Double.parseDouble(set.getInfo("open_percent") + ""), closeTo(0.25, 0.001));
        assertThat(Double.parseDouble(set.getInfo("loss_percent") + ""), closeTo(0.02, 0.001));
    }

    @Test
    public void test_add() {
        Map hv1 = new HashMap();
        hv1.put("open_percent", 0.3);
        hv1.put("loss_percent", 0.05);
        Setting set = new Setting(hv1);
        String id = set.add();
        assertThat(id, notNullValue());
    }

    @Test
    public void test_remove() {
        String stmt = "select * from setting where id=1";
        List list = Root.getInstance().getSqlOperator().query(stmt);
        assertThat(list.size(), is(1));
        new Setting("1").remove();
        stmt = "select * from setting where id=1";
        list = Root.getInstance().getSqlOperator().query(stmt);
        assertThat(list.size(), is(0));
    }

    @Test
    public void test_modify() {
        Map hv1 = new HashMap();
        hv1.put("open_percent", 0.15);
        hv1.put("loss_percent", 0.03);
        Setting set = new Setting("1");
        set.modify(hv1);
        assertThat(Double.parseDouble(set.getInfo("open_percent") + ""), closeTo(0.15, 0.001));
        assertThat(Double.parseDouble(set.getInfo("loss_percent") + ""), closeTo(0.03, 0.001));
    }

    @Test
    public void test1_modify() {
        Map hv1 = new HashMap();
        hv1.put("loss_percent", 0.015);
        Setting set = new Setting("1");
        set.modify(hv1);
        assertThat(Double.parseDouble(set.getInfo("loss_percent") + ""), closeTo(0.015, 0.001));
    }

//    @Test
    public void test2_modify() {
        Map hv1 = new HashMap();
        hv1.put("open_percent", 0.5);
        Setting set = new Setting("1");
        set.modify(hv1);
        assertThat(Double.parseDouble(set.getInfo("open_percent") + ""), closeTo(0.5, 0.001));
        assertThat(Double.parseDouble(set.getInfo("loss_percent") + ""), closeTo(0.02, 0.001));
    }
}
