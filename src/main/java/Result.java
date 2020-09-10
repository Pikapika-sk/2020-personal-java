import java.util.ArrayList;

public class Result {
    int PushEvent;
    int IssueCommentEvent;
    int IssuesEvent;
    int PullRequestEvent;

    @Override
    public String toString() {
        return "Result{" +
                "PushEvent=" + PushEvent +
                ", IssueCommentEvent=" + IssueCommentEvent +
                ", IssuesEvent=" + IssuesEvent +
                ", PullRequestEvent=" + PullRequestEvent +
                '}';
    }
}
