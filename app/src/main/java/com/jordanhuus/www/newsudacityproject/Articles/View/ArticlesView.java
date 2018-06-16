package com.jordanhuus.www.newsudacityproject.Articles.View;

import com.jordanhuus.www.newsudacityproject.Articles.Model.News;
import java.util.ArrayList;

/**
 * Created by jordanhuus on 6/15/2018.
 */

public interface ArticlesView {
    public void updateUi(ArrayList<News> articles);
}
