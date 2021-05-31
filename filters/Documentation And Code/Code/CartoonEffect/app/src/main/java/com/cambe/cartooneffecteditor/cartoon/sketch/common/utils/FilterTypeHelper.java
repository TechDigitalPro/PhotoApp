package com.cambe.cartooneffecteditor.cartoon.sketch.common.utils;


import com.cambe.cartooneffecteditor.R;

import project.android.imageprocessing.filter.processing.FlipFilter;

public class FilterTypeHelper {
    public static int FilterType2Color(int filterType) {
        switch (filterType) {
            case FlipFilter.FLIP_HORIZONTAL /*1*/:
                return R.color.filter_color_blue;
            case FlipFilter.FLIP_BOTH /*2*/:
            case 3 /*3*/:
            case 4 /*4*/:
            case 5 /*5*/:
                return R.color.filter_color_brown_light;
            case 6 /*6*/:
            case 7 /*7*/:
            case 8 /*8*/:
                return R.color.filter_color_red;
            case 9 /*9*/:
                return R.color.filter_color_red_dark;
            case 10 /*10*/:
            case 11 /*11*/:
            case 12 /*12*/:
                return R.color.filter_color_pink;
            case 13 /*13*/:
            case 14 /*14*/:
            case 23 /*23*/:
            case 24 /*24*/:
            case 25 /*25*/:
            case 26 /*26*/:
                return R.color.filter_color_green_dark;
            case 15 /*15*/:
            case 16 /*16*/:
            case 17 /*17*/:
                return R.color.filter_color_brown;
            case 18 /*18*/:
                return R.color.filter_color_blue_dark;
            case 19 /*19*/:
            case 20 /*20*/:
                return R.color.filter_color_blue_dark_dark;
            default:
                return R.color.filter_color_grey_light;
        }
    }

    public static int FilterType2Thumb(int filterType) {
        switch (filterType) {
            case 1 /*1*/:
                return R.drawable.filter_2;
            case 2 /*2*/:
                return R.drawable.filter_3;
            case 3 /*3*/:
                return R.drawable.filter_4;
            case 4 /*4*/:
                return R.drawable.filter_5;
            case 5 /*5*/:
                return R.drawable.filter_6;
            case 6 /*6*/:
                return R.drawable.filter_7;
            case 7 /*7*/:
                return R.drawable.filter_8;
            case 8 /*8*/:
                return R.drawable.filter_9;
            case 9 /*9*/:
                return R.drawable.filter_10;
            case 10 /*10*/:
                return R.drawable.filter_11;
            case 11 /*11*/:
                return R.drawable.filter_12;
            case 12 /*12*/:
                return R.drawable.filter_13;
            case 13 /*13*/:
                return R.drawable.filter_14;
            case 14 /*14*/:
                return R.drawable.filter_15;
            case 15 /*15*/:
                return R.drawable.filter_16;
            case 16 /*16*/:
                return R.drawable.filter_17;
            case 17 /*17*/:
                return R.drawable.filter_18;
            case 18 /*18*/:
                return R.drawable.filter_19;
            case 19 /*19*/:
                return R.drawable.filter_20;
            case 20 /*20*/:
                return R.drawable.filter_21;
            case 21 /*21*/:
                return R.drawable.filter_22;
            case 22 /*22*/:
                return R.drawable.filter_23;
            case 23 /*23*/:
                return R.drawable.filter_24;
            case 24 /*24*/:
                return R.drawable.filter_25;
            case 25 /*25*/:
                return R.drawable.filter_26;
            case 26/*26*/:
                return R.drawable.filter_27;
            default:
                return R.drawable.filter_1;
        }
    }

    public static int FilterType2Name(int filterType) {
        switch (filterType) {
            case 1 /*1*/:
                return R.string.filter_fairytale;
            case 2 /*2*/:
                return R.string.filter_sunrise;
            case 3 /*3*/:
                return R.string.filter_sunset;
            case 4 /*4*/:
                return R.string.filter_whitecat;
            case 5 /*5*/:
                return R.string.filter_blackcat;
            case 6 /*6*/:
                return R.string.filter_beauty;
            case 7 /*7*/:
                return R.string.filter_skinwhiten;
            case 8 /*8*/:
                return R.string.filter_healthy;
            case 9/*9*/:
                return R.string.filter_sweets;
            case 10 /*10*/:
                return R.string.filter_romance;
            case 11 /*11*/:
                return R.string.filter_sakura;
            case 12 /*12*/:
                return R.string.filter_warm;
            case 13 /*13*/:
                return R.string.filter_antique;
            case 14 /*14*/:
                return R.string.filter_nostalgia;
            case 15 /*15*/:
                return R.string.filter_calm;
            case 16 /*16*/:
                return R.string.filter_latte;
            case 17 /*17*/:
                return R.string.filter_tender;
            case 18 /*18*/:
                return R.string.filter_cool;
            case 19 /*19*/:
                return R.string.filter_emerald;
            case 20 /*20*/:
                return R.string.filter_evergreen;
            case 21 /*21*/:
                return R.string.filter_crayon;
            case 22 /*22*/:
                return R.string.filter_sketch;
            case 23 /*23*/:
                return R.string.filter_amaro;
            case 24 /*24*/:
                return R.string.filter_brannan;
            case 25 /*25*/:
                return R.string.filter_brooklyn;
            case 26 /*26*/:
                return R.string.filter_Earlybird;
            default:
                return R.string.filter_none;
        }
    }
}
