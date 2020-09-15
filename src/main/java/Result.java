import java.io.Serializable;
import java.util.ArrayList;

public class Result implements Serializable {
    int PushEvent;
    int IssueCommentEvent;
    int IssuesEvent;
    int PullRequestEvent;

    public int getIssueCommentEvent() {
        return IssueCommentEvent;
    }

    public int getIssuesEvent() {
        return IssuesEvent;
    }

    public int getPushEvent() {
        return PushEvent;
    }

    public int getPullRequestEvent() {
        return PullRequestEvent;
    }

    public void setIssueCommentEvent(int issueCommentEvent) {
        IssueCommentEvent = issueCommentEvent;
    }

    public void setIssuesEvent(int issuesEvent) {
        IssuesEvent = issuesEvent;
    }

    public void setPullRequestEvent(int pullRequestEvent) {
        PullRequestEvent = pullRequestEvent;
    }

    public void setPushEvent(int pushEvent) {
        PushEvent = pushEvent;
    }

    @Override
    public String toString() {
        return " Result{" +
                "PushEvent=" + PushEvent +
                ", IssueCommentEvent=" + IssueCommentEvent +
                ", IssuesEvent=" + IssuesEvent +
                ", PullRequestEvent=" + PullRequestEvent +
                '}';
    }
}
