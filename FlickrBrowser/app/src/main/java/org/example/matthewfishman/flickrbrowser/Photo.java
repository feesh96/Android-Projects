package org.example.matthewfishman.flickrbrowser;

import java.io.Serializable;

/**
 * Created by Matthew on 1/6/2017.
 *
 * Exists to hold all the data the a photo consist of
 */

class Photo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String mAuthor;
    private String mAuthorId;
    private String mImage;
    private String mLink;
    private String mTags;
    private String mTitle;

    public Photo(String author, String authorId, String image, String link, String tags, String title) {
        mAuthor = author;
        mAuthorId = authorId;
        mImage = image;
        mLink = link;
        mTags = tags;
        mTitle = title;
    }

    String getAuthor() {
        return mAuthor;
    }

    void setAuthor(String author) {
        mAuthor = author;
    }

    String getAuthorId() {
        return mAuthorId;
    }

    void setAuthorId(String authorId) {
        mAuthorId = authorId;
    }

    String getImage() {
        return mImage;
    }

    void setImage(String image) {
        mImage = image;
    }

    String getLink() {
        return mLink;
    }

    void setLink(String link) {
        mLink = link;
    }

    String getTags() {
        return mTags;
    }

    void setTags(String tags) {
        mTags = tags;
    }

    String getTitle() {
        return mTitle;
    }

    void setTitle(String title) {
        mTitle = title;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "mAuthor='" + mAuthor + '\'' +
                ", mAuthorId='" + mAuthorId + '\'' +
                ", mImage='" + mImage + '\'' +
                ", mLink='" + mLink + '\'' +
                ", mTags='" + mTags + '\'' +
                ", mTitle='" + mTitle + '\'' +
                '}';
    }
}
