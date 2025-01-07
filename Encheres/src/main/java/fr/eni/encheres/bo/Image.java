package fr.eni.encheres.bo;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.util.Arrays;

public class Image {

    @Id
    @Column("no_article")
    private int noArticle;
    @Column("file_name")
    private String fileName;
    @Column("mime_type")
    private String mimeType;
    private byte[] data;

    @Override
    public String toString() {
        return "Image{" +
                "noArticle=" + noArticle +
                ", fileName='" + fileName + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }

    public Image() {

    }

    public int getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(int noArticle) {
        this.noArticle = noArticle;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
