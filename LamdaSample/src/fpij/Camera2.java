/***
 * Excerpted from "Functional Programming in Java",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/vsjava8 for more book information.
***/
package fpij;

import java.util.stream.Stream;
import java.util.function.Function;
import java.awt.Color;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class Camera2 {  
  private Function<Color, Color> filter;
  
  public Color capture(final Color inputColor) {
      final Color processedColor = filter.apply(inputColor);
      //... more processing of color...
      return processedColor;
  }

  //... other functions that use the filter ...

  public void setFilters(final Function<Color, Color>... filters) {
	  // 각 필터를 이터레이션 하고 comose()메서드를 사용 각 필터를 하나로 연결 조합한다. 
	  // 필터가 없으면 reduce()메서드가 Optional empty를 리턴한다.
    filter = 
      Stream.of(filters)
            .reduce((filter, next) -> filter.compose(next))
            .orElseGet(Function::identity);
  }

  public Camera2() { setFilters(); }
  
  public static void main(final String[] args) {
    final Camera2 camera = new Camera2();
    final Consumer<String> printCaptured = (filterInfo) ->
      System.out.println(String.format("with %s: %s", filterInfo,   
        camera.capture(new Color(200, 100, 200))));
        
    System.out.println("//" + "START:NOFILTER_OUTPUT");
    printCaptured.accept("no filter");
    System.out.println("//" + "END:NOFILTER_OUTPUT");

    System.out.println("//" + "START:BRIGHT_OUTPUT");
    camera.setFilters(Color::brighter);
    printCaptured.accept("brighter filter");
    System.out.println("//" + "END:BRIGHT_OUTPUT");

    System.out.println("//" + "START:DARK_OUTPUT");
    camera.setFilters(Color::darker);
    printCaptured.accept("darker filter");
    System.out.println("//" + "END:DARK_OUTPUT");
    
    System.out.println("//" + "START:BOTH_OUTPUT");
    camera.setFilters(Color::brighter, Color::darker);
    printCaptured.accept("brighter & darker filter");
    System.out.println("//" + "END:BOTH_OUTPUT");
  }
  
}
