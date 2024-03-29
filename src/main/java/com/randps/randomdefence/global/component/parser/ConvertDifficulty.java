package com.randps.randomdefence.global.component.parser;

public class ConvertDifficulty {

  public static String convertDifficulty(Integer difficulty) {
    if (difficulty == 0) {return "Unrated";}
    if (difficulty == 1) {return "브론즈 5";}
    if (difficulty == 2) {return "브론즈 4";}
    if (difficulty == 3) {return "브론즈 3";}
    if (difficulty == 4) {return "브론즈 2";}
    if (difficulty == 5) {return "브론즈 1";}
    if (difficulty == 6) {return "실버 5";}
    if (difficulty == 7) {return "실버 4";}
    if (difficulty == 8) {return "실버 3";}
    if (difficulty == 9) {return "실버 2";}
    if (difficulty == 10) {return "실버 1";}
    if (difficulty == 11) {return "골드 5";}
    if (difficulty == 12) {return "골드 4";}
    if (difficulty == 13) {return "골드 3";}
    if (difficulty == 14) {return "골드 2";}
    if (difficulty == 15) {return "골드 1";}
    if (difficulty == 16) {return "플레티넘 5";}
    if (difficulty == 17) {return "플레티넘 4";}
    if (difficulty == 18) {return "플레티넘 3";}
    if (difficulty == 19) {return "플레티넘 2";}
    if (difficulty == 20) {return "플레티넘 1";}
    if (difficulty == 21) {return "다이아몬드 5";}
    if (difficulty == 22) {return "다이아몬드 4";}
    if (difficulty == 23) {return "다이아몬드 3";}
    if (difficulty == 24) {return "다이아몬드 2";}
    if (difficulty == 25) {return "다이아몬드 1";}
    if (difficulty == 26) {return "루비 5";}
    if (difficulty == 27) {return "루비 4";}
    if (difficulty == 28) {return "루비 3";}
    if (difficulty == 29) {return "루비 2";}
    if (difficulty == 30) {return "루비 1";}

    return "Unknown";
  }

}
