package me.griphion.AntiNetherRoof.utils;

import java.util.Collection;

public class StringUtils {

  public static String separateWithCommas(final Collection<String> strings){
      String string = "";
      for(String elem : strings){
          string = string.concat(" ");
          string = string.concat(elem);
          string = string.concat(",");
      }
      if(!string.isEmpty() && string.charAt(string.length() - 1) == ',')
      {
          string = string.substring(0,string.length() - 1);
      }
      return string;
  }
}
