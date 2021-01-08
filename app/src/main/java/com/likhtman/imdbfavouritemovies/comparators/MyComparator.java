package com.likhtman.imdbfavouritemovies.comparators;

import com.likhtman.imdbfavouritemovies.bean.Movie;
import com.likhtman.imdbfavouritemovies.globalStorage.GlobalVariables;

import java.util.Comparator;

public class MyComparator implements Comparator<Movie> {

    GlobalVariables globalVariablesInstance = GlobalVariables.getInstance();
    @Override
    public int compare(Movie firstMovie, Movie secondMovie) {
        if (!globalVariablesInstance.getCompareByYear())
            return firstMovie.getName().compareTo(secondMovie.getName());
        else if (globalVariablesInstance.getCompareByYear())
        {
            if (firstMovie.getDate() > secondMovie.getDate())
            {
                return 1;
            }
            else if (firstMovie.getDate() < secondMovie.getDate())
            {
                return -1;
            }
        }
        return 0;
    }
}