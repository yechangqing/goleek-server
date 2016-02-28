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
public class AccountTest extends Base {

    @Test
    public void test_Account() {
        this.expectedEx.expect(IllegalStateException.class);
        this.expectedEx.expectMessage("记录不存在");
        new Account("13");
    }

    @Test
    public void test1_Account() {
        this.expectedEx.expect(IllegalArgumentException.class);
        this.expectedEx.expectMessage("没有属性abcd");
        Map hv = new HashMap();
        hv.put("abcd", "22");
        new Account(hv);
    }

    @Test
    public void test_getInfo() {
        Account ac = new Account("1");
        Map<String, Object> map = ac.getInfo();
        assertThat(map.get("code") + "", is("100003109"));
        assertThat(map.get("company") + "", is("新纪元期货"));
        assertThat(Double.parseDouble(map.get("money") + ""), closeTo(150000, 0.1));
        assertThat(map.get("type") + "", is("期货"));
        assertThat(map.get("used") + "", is("y"));
    }

    @Test
    public void test1_getInfo() {
        Account ac = new Account("1");
        assertThat(ac.getInfo("code") + "", is("100003109"));
        assertThat(ac.getInfo("company") + "", is("新纪元期货"));
        assertThat(Double.parseDouble(ac.getInfo("money") + ""), closeTo(150000, 0.1));
        assertThat(ac.getInfo("type") + "", is("期货"));
        assertThat(ac.getInfo("used") + "", is("y"));
    }

    @Test
    public void test_add() {
        Map hv = new HashMap();
        hv.put("code", "3005335");
        hv.put("company", "新纪元期货");
        hv.put("money", 110000);
        hv.put("type", "期货");
        Account acc = new Account(hv);
        String id = acc.add();
        assertThat(id, notNullValue());
    }

    @Test
    public void test1_add() {
        Map hv = new HashMap();
        hv.put("code", "100003109");
        hv.put("company", "新纪元期货");
        hv.put("money", 110000);
        hv.put("type", "期货");
        this.expectedEx.expect(IllegalStateException.class);
        this.expectedEx.expectMessage("已存在该账号");
        Account acc = new Account(hv);
        acc.add();
    }

    @Test
    public void test_remove() {
        String stmt = "select * from account where id=1";
        List list = Root.getInstance().getSqlOperator().query(stmt);
        assertThat(list.size(), is(1));
        new Account("1").remove();
        stmt = "select * from account where id=1";
        list = Root.getInstance().getSqlOperator().query(stmt);
        assertThat(list.size(), is(0));
    }

    @Test
    public void test_modify() {
        Map hv = new HashMap();
        hv.put("money", 39000);
        hv.put("code", "100003111");
        Account acc = new Account("1");
        acc.modify(hv);
        assertThat(acc.getInfo("code") + "", is("100003111"));
        assertThat(Double.parseDouble(acc.getInfo("money") + ""), closeTo(39000, 0.1));
    }

    @Test
    public void test_use() {
        Account acc = new Account("2");
        acc.use();
        assertThat(acc.getInfo("used") + "", is("y"));
    }

    @Test
    public void test_unUse() {
        Account acc = new Account("1");
        acc.unUse();
        assertThat(acc.getInfo("used") + "", is("n"));
    }
}
