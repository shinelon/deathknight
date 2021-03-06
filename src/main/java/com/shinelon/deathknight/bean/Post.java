package com.shinelon.deathknight.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;

/*
 *
 *
 * @author syq
 */
public class Post {
  private String title;
  private User author;
  private List<Tag> tags;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createDate;

  private String content;

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<Tag> getTags() {
    return tags;
  }

  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getCreateDate() {
    return createDate == null ? null : new Date(createDate.getTime());
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate == null ? null : new Date(createDate.getTime());
  }

  @Override
  public String toString() {
    return "Post [title="
        + title
        + ", author="
        + author
        + ", tags="
        + tags
        + ", createDate="
        + createDate
        + ", content="
        + content
        + "]";
  }
}
