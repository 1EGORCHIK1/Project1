package UnitTests;

import Project.Expression;
import Project.Ideone;
import org.junit.Assert;
import org.junit.Test;

public class RPNTest {
    @Test
    public void RNP1plus1()
    {
        Assert.assertEquals(Ideone.calc(Expression.parse("1+1")),"2.0");
    }

    @Test
    public void RPN2mul5() {
        Assert.assertEquals(Ideone.calc(Expression.parse("2*5")),"10.0");
    }

    @Test
    public void RPN9dev3() {
        Assert.assertEquals(Ideone.calc(Expression.parse("9/3")),"3.0");
    }

    @Test
    public void RPNplusmuldev() {
        Assert.assertEquals(Ideone.calc(Expression.parse("2+4*5+10/2")),"27.0");
    }

    @Test
    public void RPNplusminusmuldev() {
        Assert.assertEquals(Ideone.calc(Expression.parse("8+2*5")),"18.0");
    }

    @Test
    public void RPNbrackets() {
        Assert.assertEquals(Ideone.calc(Expression.parse("((8+2)*5)/2")),"25.0");
    }
}
