package org.nelda.learningjava;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2015/7/21 0021.
 */
public class ViewInfo {


    //private String titleHead;
    //private String numHead;
    //private String authorHead;
    //private String websiteHead;
    //private String imageSrc;
    private String htmlString;
    private String writeTime;
    private String buttonUrl;
    private String titleContent;
    private String textContent;


    public String getHtmlString() {
        return htmlString;
    }

    public void setHtmlString(String htmlString) {
        this.htmlString = "<html><head></head><body><div>";
        this.htmlString += htmlString;
        this.htmlString += "</div></body></html>";
    }
    public String getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(String writeTime) {
        this.writeTime = writeTime;
    }

    public String getButtonUrl() {
        return buttonUrl;
    }

    public void setButtonUrl(String buttonUrl) {
        this.buttonUrl = buttonUrl;
    }

    public String getTitleContent() {
        return titleContent;
    }

    public void setTitleContent(String titleContent) {
        this.titleContent = titleContent;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }







}
