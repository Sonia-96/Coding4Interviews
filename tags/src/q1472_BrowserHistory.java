import java.util.ArrayList;

public class q1472_BrowserHistory {
    ArrayList<String> history;
    int curr;

    public q1472_BrowserHistory(String homepage) {
        history = new ArrayList<>();
        history.add(homepage);
        curr = 0;
    }

    /**
     * Visits url from the current page.
     * It clears up all the forward history.
     */
    public void visit(String url) {
        while (curr < history.size() - 1) {
            history.remove(history.size() - 1);
        }
        history.add(url);
        curr++;
    }

    public String back(int steps) {
        curr = Math.max(0, curr - steps);
        return history.get(curr);
    }

    public String forward(int steps) {
        curr = Math.min(history.size() - 1, curr + steps);
        return history.get(curr);
    }
}
