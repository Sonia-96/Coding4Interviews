import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class q770_BasicCalculatorIV {

    public List<String> basicCalculatorIV(String expression, String[] evalvars, int[] evalints) {
        Map<String, Integer> evalMap = new HashMap<>();
        for (int i = 0; i < evalvars.length; i++) {
            evalMap.put(evalvars[i], evalints[i]);
        }
        return parse(expression).evaluate(evalMap).toList();
    }

    /**
     * 创造一个新的 Poly 实例来表示常数或 expr 指定的变量
     */
    public Poly make(String expr) {
        Poly ans = new Poly();
        List<String> list = new ArrayList();
        if (Character.isDigit(expr.charAt(0))) {
            ans.update(list, Integer.valueOf(expr)); // 为什么？
        } else {
            list.add(expr);
            ans.update(list, 1);
        }
        return ans;
    }

    /**
     * 返回对 左边（left) 和 右边(left) 进行 symobl 操作之后的结果
     */
    public Poly combine(Poly left, Poly right, char symbol) {
        if (symbol == '+') return left.add(right);
        if (symbol == '-') return left.sub(right);
        if (symbol == '*') return left.mul(right);
        throw null;
    }

    /**
     * 将 expr 解析成一个 Poly 实例
     */
    public Poly parse(String expr) {
        List<Poly> bucket = new ArrayList();
        List<Character> symbols = new ArrayList();
        int i = 0;
        while (i < expr.length()) {
            if (expr.charAt(i) == '(') {
                // 寻找反括号，并将这段字串加入bucket
                int bal = 0, j = i;
                for (; j < expr.length(); ++j) {
                    if (expr.charAt(j) == '(') bal++;
                    if (expr.charAt(j) == ')') bal--;
                    if (bal == 0) break;
                }
                bucket.add(parse(expr.substring(i+1, j)));
                i = j;
            } else if (Character.isLetterOrDigit(expr.charAt(i))) {
                int j = i;
                search : { // 标记该代码块的名称为search
                    for (; j < expr.length(); ++j) {
                        if (expr.charAt(j) == ' ') {
                            bucket.add(make(expr.substring(i, j)));
                            break search; // “break 标记名称”，跳出指定代码块
                        }
                    }
                    bucket.add(make(expr.substring(i)));
                }
                i = j;
//              search代码块等同于：
//              （参考：https://www.cnblogs.com/pinlantu/p/9941539.html）
//                boolean last = true;
//                for (; j < expr.length(); ++j)
//                    if (expr.charAt(j) == ' ') {
//                        bucket.add(make(expr.substring(i, j)));
//                        last = false;
//                        break; // break 标记名称，跳出指定循环
//                    }
//                if (last) {
//                    bucket.add(make(expr.substring(i)));
//                }
            } else if (expr.charAt(i) != ' ') {
                symbols.add(expr.charAt(i));
            }
            i++;
        }

        // 从右到左算乘法
        for (int j = symbols.size() - 1; j >= 0; --j) {
            if (symbols.get(j) == '*')
                bucket.set(j, combine(bucket.get(j), bucket.remove(j+1), symbols.remove(j)));
        }

        if (bucket.isEmpty()) {
            return new Poly();
        }
        // 从左到右算剩余的加减法
        Poly ans = bucket.get(0);
        for (int j = 0; j < symbols.size(); ++j) {
            ans = combine(ans, bucket.get(j+1), symbols.get(j));
        }
        return ans;
    }

    @Test
    public void test1() {
        String expression = "e + 8 - a + 5";
        String[] evalvars = {"e"};
        int[] evalints = {1};
        String[] tmp = {"-1*a","14"};
        List<String> res = new ArrayList<>(Arrays.asList(tmp));
        Assert.assertEquals(res, basicCalculatorIV(expression, evalvars, evalints));
    }

    @Test
    public void test2() {
        String expression = "e - 8 + temperature - pressure";
        String[] evalvars = {"e", "temperature"};
        int[] evalints = {1, 12};
        String[] tmp = {"-1*pressure","5"};
        List<String> res = new ArrayList<>(Arrays.asList(tmp));
        Assert.assertEquals(res, basicCalculatorIV(expression, evalvars, evalints));
    }

    @Test
    public void test3() {
        String expression = "(e + 8) * (e - 8)";
        String[] evalvars = {};
        int[] evalints = {};
        String[] tmp = {"1*e*e","-64"};
        List<String> res = new ArrayList<>(Arrays.asList(tmp));
        Assert.assertEquals(res, basicCalculatorIV(expression, evalvars, evalints));
    }
}

class Poly {
    // TODO 为什么这里是List<String> 而不是String？
    HashMap<List<String>, Integer> count;

    Poly() {
        count = new HashMap<>();
    }

    void update(List<String> key, int val) {
        count.put(key, count.getOrDefault(key, 0) + val);
    }

    Poly add(Poly that) {
        Poly ans = new Poly();
        for (List<String> k : count.keySet()) {
            ans.update(k, count.get(k));
        }
        for (List<String> k : that.count.keySet()) {
            ans.update(k, that.count.get(k));
        }
        return ans;
    }

    Poly sub(Poly that) {
        Poly ans = new Poly();
        for (List<String> k: this.count.keySet())
            ans.update(k, this.count.get(k));
        for (List<String> k: that.count.keySet())
            ans.update(k, -that.count.get(k));
        return ans;
    }

    Poly mul(Poly that) {
        Poly ans = new Poly();
        for (List<String> k1: this.count.keySet())
            for (List<String> k2: that.count.keySet()) {
                List<String> kNew = new ArrayList();
                for (String x: k1) kNew.add(x);
                for (String x: k2) kNew.add(x);
                Collections.sort(kNew);
                ans.update(kNew, this.count.get(k1) * that.count.get(k2));
            }
        return ans;
    }

    /**
     * 返回将所有的自由变量替换成 evalmap 指定常数之后的结果
     */
    Poly evaluate(Map<String, Integer> evalMap) {
        Poly ans = new Poly();
        for (List<String> k: this.count.keySet()) {
            int c = this.count.get(k);
            List<String> free = new ArrayList();
            for (String token: k) {
                if (evalMap.containsKey(token))
                    c *= evalMap.get(token);
                else
                    free.add(token);
            }
            ans.update(free, c);
        }
        return ans;
    }

    int compareList(List<String> A, List<String> B) {
        int i = 0;
        for (String x: A) {
            String y = B.get(i++);
            if (x.compareTo(y) != 0) return x.compareTo(y);
        }
        return 0;
    }

    /**
     * 返回正确的多项式输出格式
     */
    List<String> toList() {
        List<String> ans = new ArrayList();
        List<List<String>> keys = new ArrayList(this.count.keySet());
        Collections.sort(keys, (a, b) -> // TODO 看不懂
                a.size() != b.size() ? b.size() - a.size() : compareList(a, b));

        for (List<String> key: keys) {
            int v = this.count.get(key);
            if (v == 0) continue;
            StringBuilder word = new StringBuilder();
            word.append("" + v);
            for (String token: key) {
                word.append('*');
                word.append(token);
            }
            ans.add(word.toString());
        }
        return ans;
    }
}
