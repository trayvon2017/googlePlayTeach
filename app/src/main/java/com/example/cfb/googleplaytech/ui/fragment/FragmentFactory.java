package com.example.cfb.googleplaytech.ui.fragment;


import android.util.Log;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by cfb on 2018/6/20.
 */

public  class FragmentFactory {
    private static HashMap<Integer, BaseFragment> mFragments =
            new HashMap<Integer, BaseFragment>();

    public static BaseFragment creatFragment(int position) {
        BaseFragment baseFragment = mFragments.get(position);

        if (baseFragment == null) {
            switch (position) {
                case 0:
                    baseFragment = new HomePageFragment();
                    break;

                case 1:
                    baseFragment = new ApplicationPageFragment();
                    break;
                case 2:
                    baseFragment = new GamePageFragment();
                    break;
                case 3:
                    baseFragment = new SubjectPageFragment();
                    break;

                case 4:
                    baseFragment = new RecommendPageFragment();
                    break;

                case 5:
                    baseFragment = new ClassificationPageFragment();
                    break;
                case 6:
                    baseFragment = new RanklistPageFragment();
                    break;
                default:
                    baseFragment = null;
                    break;
            }
            Log.d(TAG, "creatFragment: 新建了fragment:"+baseFragment.getClass().getSimpleName());
            mFragments.put(position,baseFragment);
        }


        return baseFragment;
    }

    /*private static ApplicationPageFragment applicationPageFragment;
    private static HomePageFragment homePageFragment;
    private static GamePageFragment gamePageFragment;
    private static SubjectPageFragment subjectPageFragment;
    private static RecommendPageFragment recommendPageFragment;
    private static ClassificationPageFragment classificationPageFragment;
    private static RanklistPageFragment ranklistPageFragment;

    public static Fragment creatFragment(int position){
        switch (position){
            case 0:
                if (homePageFragment ==null){
                    homePageFragment = new HomePageFragment();
                }
                return homePageFragment;
            case 1:
                if (applicationPageFragment == null){
                    applicationPageFragment = new ApplicationPageFragment();
                }
                return applicationPageFragment;
            case 2:
                if (gamePageFragment == null){
                    gamePageFragment = new GamePageFragment();
                }
                return gamePageFragment;
            case 3:
                if (subjectPageFragment == null){
                    subjectPageFragment = new SubjectPageFragment();
                }
                return subjectPageFragment;
            case 4:
                if (recommendPageFragment == null){
                    recommendPageFragment = new RecommendPageFragment();

                }
                return recommendPageFragment;
            case 5:
                if (classificationPageFragment == null){
                    classificationPageFragment = new ClassificationPageFragment();
                }
                return classificationPageFragment;
            case 6:
                if (ranklistPageFragment == null){
                    ranklistPageFragment = new RanklistPageFragment();
                }
                return ranklistPageFragment;
            default:
                return null;
        }
    }*/
}
