package org.yecq.goleek.server.service;

import com.jhhc.baseframework.test.Base;
import com.jhhc.baseframework.web.core.Root;
import com.jhhc.baseframework.web.service.Sret;
import org.yecq.goleek.server.service.bean.param.FuturesAddBean;
import org.yecq.goleek.server.service.bean.param.FuturesCloneBean;
import org.yecq.goleek.server.service.bean.param.FuturesModifyBean;
import org.yecq.goleek.server.service.bean.result.FuturesInfoBean;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author yecq
 */
public class FuturesServiceTest extends Base {

    @Autowired
    private FuturesService fs;

    @Test
    public void test_getExchangeNames() {
        Sret sr = fs.getExchangeNames();
        assertThat(sr.isOk(), is(true));
        String[] names = (String[]) sr.getData();
        assertThat(names.length, is(3));
        assertThat(names[0], is("上海期货"));
        assertThat(names[1], is("大连商品"));
        assertThat(names[2], is("郑州商品"));
    }

    @Test
    public void test_getListAll() {
        Sret sr = fs.getListAll();
        assertThat(sr.isOk(), is(true));
        List<FuturesInfoBean> list = (List) sr.getData();
        assertThat(list.size(), is(3));
    }

    @Test
    public void test_getListInterested() {
        Sret sr = fs.getListInterested();
        assertThat(sr.isOk(), is(true));
        List<FuturesInfoBean> list = (List) sr.getData();
        assertThat(list.size(), is(2));
    }

    @Test
    public void test_add() {
        FuturesAddBean bean = new FuturesAddBean();
        bean.setCode("m1602");
        bean.setName("豆粕");
        bean.setMargin(0.09);
        bean.setUnit(10);
        bean.setMin(1);
        bean.setExchange("大连商品");
        Sret sr = fs.add(bean);
        assertThat(sr.isOk(), is(true));
        assertThat((String) sr.getData(), notNullValue());
    }

    @Test
    public void test1_add() {
        FuturesAddBean bean = new FuturesAddBean();
//        bean.setCode("m1602");
        bean.setName("豆粕");
        bean.setMargin(0.09);
        bean.setUnit(10);
        bean.setMin(1);
        bean.setExchange("大连商品");
        Sret sr = fs.add(bean);
        assertThat(sr.isError(), is(true));
    }

    @Test
    public void test2_add() {
        FuturesAddBean bean = new FuturesAddBean();
        bean.setCode("rb1601");
        bean.setName("螺纹钢");
        bean.setMargin(0.09);
        bean.setUnit(10);
        bean.setMin(1);
        bean.setExchange("上海商品");
        Sret sr = fs.add(bean);
        assertThat(sr.isFail(), is(true));
        assertThat(sr.getMessage(), is("已存在该合约"));
    }

    @Test
    public void test_remove() {
        Sret sr = fs.remove("1");
        assertThat(sr.isOk(), is(true));
    }

    @Test
    public void test_cloneItself() {
        FuturesCloneBean bean = new FuturesCloneBean();
        bean.setNewCode("rb1612");
        Sret sr = fs.cloneItself("1", bean);
        assertThat(sr.isOk(), is(true));
    }

    @Test
    public void test1_cloneItself() {
        FuturesCloneBean bean = new FuturesCloneBean();
        bean.setNewCode("m1612");
        Sret sr = fs.cloneItself("1", bean);
        assertThat(sr.isFail(), is(true));
    }

    @Test
    public void test_modify() {
        FuturesModifyBean bean = new FuturesModifyBean();
        bean.setCode("code");
        bean.setName("name");
        bean.setMargin(0.14);
        bean.setUnit(15);
        bean.setMin(0.01);
        bean.setExchange("大连商品");
        Sret sr = fs.modify("1", bean);
        assertThat(sr.isOk(), is(true));
    }

    @Test
    public void test_interest() {
        FuturesModifyBean bean = new FuturesModifyBean();
        bean.setInterest("y");
        Sret sr = fs.modify("1", bean);
        assertThat(sr.isOk(), is(true));
    }

    @Test
    public void test_unInterest() {
        FuturesModifyBean bean = new FuturesModifyBean();
        bean.setInterest("n");
        Sret sr = fs.modify("2", bean);
        assertThat(sr.isOk(), is(true));
    }

    @Test
    public void test_interestAll() {
        Sret sr = fs.interestAll();
        assertThat(sr.isOk(), is(true));
        List li = Root.getInstance().getSqlOperator().query("select * from futures where interest='y'");
        assertThat(li.size(), is(3));
        li = Root.getInstance().getSqlOperator().query("select * from futures where interest='n'");
        assertThat(li.size(), is(0));
    }

    @Test
    public void test_unInterestAll() {
        Sret sr = fs.unInterestAll();
        assertThat(sr.isOk(), is(true));
        List li = Root.getInstance().getSqlOperator().query("select * from futures where interest='n'");
        assertThat(li.size(), is(3));
        li = Root.getInstance().getSqlOperator().query("select * from futures where interest='y'");
        assertThat(li.size(), is(0));
    }
}
