import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class q770_learning {
    public List<String> basicCalculatorIV(String expression, String[] evalvars, int[] evalints) {
        HashMap<String, Integer> evalMap = new HashMap<>();
        for (int i = 0; i < evalvars.length; i++) {
            evalMap.put(evalvars[i], evalints[i]);
        }
        return parse(expression).evaluate(evalMap).toList();
    }

    /**
     * 将expression解析为Poly形式
     */
    private Poly parse(String expr) {
        List<Poly> bucket = new ArrayList<>();
        List<Character> symbols = new ArrayList<>();
        int i = 0;
        while (i < expr.length()) {
            char c = expr.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                int j = i + 1;
                // 当走到字符串末尾或expr[j]为space时，停止
                for (; j < expr.length(); j++) {
                    if (expr.charAt(j) == ' ') {
                        break;
                    }
                }
                if (j != expr.length()) {
                    bucket.add(make(expr.substring(i, j)));
                } else {
                    bucket.add(make(expr.substring(i)));
                }
                i = j;
            } else if (c == '(') {
                int cnt = 1;
                int j = i + 1;
                for (; j < expr.length() && cnt != 0; j++) {
                    if (expr.charAt(j) == '(') {
                        cnt++;
                    } else if (expr.charAt(j) == ')') {
                        cnt--;
                    }
                }
                bucket.add(parse(expr.substring(i + 1, j - 1)));
                i = j;
            } else if (c != ' ') {
                symbols.add(c);
            }
            i++;
        }
        // 从右到左算乘法
        // TODO 直接在上面的while循环里就把加减乘除算了
        for (int j = symbols.size() - 1; j >= 0; j--) {
            if (symbols.get(j) == '*') {
                bucket.set(j, combine(bucket.get(j), bucket.remove(j + 1), symbols.remove(j)));
            }
        }

        if (bucket.isEmpty()) {
            return new Poly();
        }

        // 从左到右算加减法
        Poly ans = bucket.get(0);
        for (int j = 0; j < symbols.size(); j++) {
            ans = combine(ans, bucket.get(j + 1), symbols.get(j));
        }
        return ans;
    }

    /**
     * 创造一个新的Poly实例来表示常数或变量
     */
    private Poly make(String expr) {
        Poly res = new Poly();
        List<String> list = new ArrayList<>();
        if (Character.isDigit(expr.charAt(0))) {
            res.update(list, Integer.valueOf(expr));
        } else {
            list.add(expr);
            res.update(list, 1);
        }
        return res;
    }

    /**
     * 对式子左侧（left）和右侧（right）进行symbol操作
     */
    private Poly combine(Poly left, Poly right, char symbol) {
        return switch(symbol) {
            case '+' -> left.add(right);
            case '-' -> left.sub(right);
            case '*' -> left.mul(right);
            default -> null;
        };
    }

    class Poly {
        HashMap<List<String>, Integer> count;

        Poly() {
            count = new HashMap<>();
        }

        private void update(List<String> key, int val) {
            count.put(key, count.getOrDefault(key, 0) + val);
        }

        Poly add(Poly that) {
            // that 有可能是（e + 8）这种
            Poly ans = new Poly();
            for (List<String> key : count.keySet()) {
                ans.update(key, count.get(key));
            }
            for (List<String> key: that.count.keySet()) {
                ans.update(key, that.count.get(key));
            }
            return ans;
        }

        Poly sub(Poly that) {
            Poly ans = new Poly();
            for (List<String> key : count.keySet()) {
                ans.update(key, count.get(key));
            }
            for (List<String> key : that.count.keySet()) {
                ans.update(key, -that.count.get(key));
            }
            return ans;
        }

        Poly mul(Poly that) {
            Poly ans = new Poly();
            for (List<String> k1 : this.count.keySet()) {
                for (List<String> k2 : that.count.keySet()) {
                    List<String> kNew = new ArrayList<>();
                    for (String x : k1) {
                        kNew.add(x);
                    }
                    for (String x : k2) {
                        kNew.add(x);
                    }
                    Collections.sort(kNew);
                    ans.update(kNew, this.count.get(k1) * that.count.get(k2));
                }
            }
            return ans;
        }

        /**
         * 返回将所有的自由变量替换成 evalmap 指定常数之后的结果
         */
        Poly evaluate(Map<String, Integer> evalMap) {
            Poly ans = new Poly();
            for (List<String> key : this.count.keySet()) {
                int coefficient = this.count.get(key);
                List<String> free = new ArrayList<>();
                for (String s : key) {
                    if (evalMap.containsKey(s)) {
                        coefficient *= evalMap.get(s);
                    } else {
                        free.add(s);
                    }
                }
                ans.update(free, coefficient);
            }
            return ans;
        }

        /**
         * 比较两个list的大小：
         * 1. 按字母序从小到大
         * 2. 按degree从大到小
         */
        int compareList(List<String> A, List<String> B){
            for (int i = 0; i < Math.min(A.size(), B.size()); i++) {
                String x = A.get(i);
                String y = B.get(i);
                if (x.compareTo(y) != 0) {
                    return x.compareTo(y);
                }
            }
            return 0;
        }

        /**
         * 返回正确的多项式输出格式
         */
        List<String> toList() {
            List<String> ans = new ArrayList<>();
            List<List<String>> keys = new ArrayList(this.count.keySet());
            // 对keys进行排序
            Collections.sort(keys, (a, b) -> // TODO 看不懂
                    a.size() != b.size() ? b.size() - a.size() : compareList(a, b)
            );
            // 按题目要求格式输出
            for (List<String> key : keys) {
                int v = this.count.get(key);
                if (v == 0) {
                    continue;
                }
                StringBuilder word = new StringBuilder();
                word.append("" + v);
                for (String token : key) {
                    word.append("*");
                    word.append(token);
                }
                ans.add(word.toString());
            }
            return ans;
        }
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