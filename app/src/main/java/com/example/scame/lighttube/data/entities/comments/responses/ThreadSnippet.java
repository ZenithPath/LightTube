package com.example.scame.lighttube.data.entities.comments.responses;


public class ThreadSnippet {

    private String videoId;

    private CommentSnippetHolder topLevelComment;

    private boolean canReply;

    private int totalReplyCount;

    private boolean isPublic;

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void setTopLevelComment(CommentSnippetHolder topLevelComment) {
        this.topLevelComment = topLevelComment;
    }

    public void setCanReply(boolean canReply) {
        this.canReply = canReply;
    }

    public void setTotalReplyCount(int totalReplyCount) {
        this.totalReplyCount = totalReplyCount;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getVideoId() {
        return videoId;
    }

    public CommentSnippetHolder getTopLevelComment() {
        return topLevelComment;
    }

    public boolean isCanReply() {
        return canReply;
    }

    public int getTotalReplyCount() {
        return totalReplyCount;
    }

    public boolean isPublic() {
        return isPublic;
    }
}
