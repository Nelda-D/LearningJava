package org.nelda.learningjava;

import java.util.List;

/**
 * Created by Administrator on 2015/7/20 0020.
 */
public class MenuData {
    private String[] itemArray;
    private List<ViewInfo> listItem;

    public MenuData(){
        listItem = JSONObjectParse.getList();
        itemArray = new String[listItem.size()];
        for(int i=0;i<listItem.size();i++){
            itemArray[i] = listItem.get(i).getTitleContent();
        }
    }


    public String[] getMenuData(){
        return itemArray;
    }
}
