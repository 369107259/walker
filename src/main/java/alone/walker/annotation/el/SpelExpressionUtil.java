package alone.walker.annotation.el;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @Author: huangYong
 * @Date: 2020/3/18 15:28
 * spring EL表达式转化工具
 */
public class SpelExpressionUtil {
    private static ExpressionParser parser = new SpelExpressionParser();

    /***
     * @param key EL表达式字符串，占位符以#开头
     * @param prams 形参名称，可以理解为占位符名称
     * @param args 参数值，可以理解为占位符的真实值
     * @return 返回el表达式经过参数替换后的字符串值
     */
    public static String getKey(String key, String[] prams, Object[] args) {
        Expression expression = parser.parseExpression(key);
        EvaluationContext context = new StandardEvaluationContext();
        if (args.length <= 0) {
            return null;
        }
        for (int i = 0; i < args.length; i++) {
            context.setVariable(prams[i], args[i]);
        }
        return expression.getValue(context, String.class);
    }
}
