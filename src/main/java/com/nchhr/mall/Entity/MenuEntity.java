package com.nchhr.mall.Entity;

public class MenuEntity {
    private  String M_id;
    private String title;
    private String  url;
    private String menu_sort;
    private String visible;
    private String parents;
    private String css;

    public String getM_id() {
        return M_id;
    }

    public void setM_id(String m_id) {
        M_id = m_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMenu_sort() {
        return menu_sort;
    }

    public void setMenu_sort(String menu_sort) {
        this.menu_sort = menu_sort;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getParents() {
        return parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "M_id='" + M_id + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", menu_sort='" + menu_sort + '\'' +
                ", visible='" + visible + '\'' +
                ", parents='" + parents + '\'' +
                ", css='" + css + '\'' +
                '}';
    }
}
