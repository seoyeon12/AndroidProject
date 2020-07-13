package com.codes.todolist;

public class ListViewItem {
    private Integer index;
    private String name ;
    private String context ;
    private String time ;

    public void setindex(Integer index) {
        this.index = index ;
    }
    public void setname(String name) {
        this.name = name ;
    }
    public void setContext(String context) {
        this.context = context ;
    }
    public void setTime(String time) {
        this.time = time ;
    }

    public Integer getindex() {
        return this.index;
    }
    public String getName() {
        return this.name ;
    }
    public String getContext() {
        return this.context ;
    }
    public String getTime() {
        return this.time ;
    }
}
