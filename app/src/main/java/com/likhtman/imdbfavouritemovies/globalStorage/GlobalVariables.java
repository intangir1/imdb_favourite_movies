package com.likhtman.imdbfavouritemovies.globalStorage;

public class GlobalVariables {

    private boolean compareByYear=false;
    //private boolean is_local_fragment = true;

    public boolean getCompareByYear(){
        return this.compareByYear;
    }


    public void setCompareByYear(boolean to_set){
        this.compareByYear = to_set;
    }


//    public boolean getIs_local_fragment(){
//        return this.is_local_fragment;
//    }


//    public void setIs_local_fragment(boolean to_set){
//        this.is_local_fragment = to_set;
//    }


    private static GlobalVariables instance = null;

    private GlobalVariables(){

    }


    public static GlobalVariables getInstance(){
        if (instance == null)
            instance = new GlobalVariables();
        return instance;
    }
}
