import java.math.BigDecimal;
import java.util.Arrays;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.print.attribute.standard.JobOriginatingUserName;
public class Chapter1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final List<BigDecimal> prices = Arrays.asList(new BigDecimal("10" )
				              ,new BigDecimal("30" ),new BigDecimal("17" ),
				              new BigDecimal("20" ),new BigDecimal("15" ),new BigDecimal("18" )
				              ,new BigDecimal("45" ),new BigDecimal("12" ));
		
		//람다식
		BigDecimal totofDisconted = BigDecimal.ZERO;
		
		final BigDecimal totofDisconted2 = prices.stream().filter(price->price.compareTo(BigDecimal.valueOf(20)) >0 )
				.map(price->price.multiply(BigDecimal.valueOf(0.9)))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		System.out.println(totofDisconted2);
		
		// 자바식
		for(BigDecimal price :prices) {
			if (price.compareTo(BigDecimal.valueOf(20))>0 )
			{
				totofDisconted =totofDisconted.add(price.multiply(BigDecimal.valueOf(0.9)));
		
			}
		}
		System.out.println(totofDisconted);
		
		final List<String> friends = Arrays.asList("Brian","Nate","Raju","Sara","Scott");
		friends.forEach(new Consumer<String>() {
			public void accept(final String name) { System.out.println(name);};
		}) ;
		
		friends.forEach((final String name)->System.out.println(name));
		//java 방법
		final List<String> uppercaseNames = new ArrayList<String>();
		for (String name : friends) { uppercaseNames.add(name.toUpperCase()); }
		//java 방법
		friends.forEach(name->uppercaseNames.add(name.toUpperCase()));
		
		// 새로운 스트림 인터페이스의 map()메서드를 사용하면 가변성이 발생하지 않도록 할 수 있으며 코드를 더 간결하게 만들수있다.
		// map()메서드는 입력 순서를 출력순서로 매핑하거나 입력순서를 다른 순서의 출력을 변형한다.
		friends.stream().map(name->name.toUpperCase()).forEach(name->System.out.print(name + " "));
		friends.stream().map(name->name.length()).forEach(count->System.out.print(count + " "));
		//filter는 return값이 boolean결과를 리턴하는 람다표현식이 필요하다.
		friends.stream().filter(name->name.startsWith("S")).forEach(count->System.out.print(count + " "));
		
		
		final Predicate<String> startWin =name->name.startsWith("N");
		final long countFriendStartN = friends.stream().filter(startWin).count();
		System.out.println("countFriendStartN " + countFriendStartN );
		
		final long countFriendStartN2 = friends.stream().filter(checkifStartWin("N")).count();
		final long countFriendStartB = friends.stream().filter(checkifStartWin("B")).count();
		
		final Function<String, Predicate<String>> startWithLetter = 
				(String letter) -> { 
					Predicate<String> checkstart =(String name) ->name.startsWith(letter);
					return checkstart;
				};
		long countFriendStartN3 = friends.stream().filter(startWithLetter.apply("N")).count();
		long countFriendStartN4 = friends.stream().filter(startWithLetter.apply("B")).count();
		System.out.println("countFriendStartB " + countFriendStartN4 );
		
		friends.stream().filter(checkifStartWin("N")).findFirst();
		
		pickName(friends,"Z");
		pickName(friends,"N");
		
		//멥리듀스 패턴이다.오퍼레이션을 분배하는 map 합계의 결과를 sum으로 reduce한다.
		friends.stream().mapToInt(name->name.length()).sum();
		//reduce 메소드를 활용하여  두개의 엘리먼트를 서로 비교하고 컬렉션에 
		//남아있는 엘리먼트를 비교를 통해 결과를 얻어내다.
		final Optional<String> aLongName = friends.stream().reduce(
				        (name1, name2) -> name1.length() >= name2.length() ? name1 :name2
				        		);
		aLongName.ifPresent(name -> System.out.println(String.format("%s", name)));
		
		System.out.println(
				friends.stream().map(String::toUpperCase).collect(Collectors.joining(", "))
				);
	}
	
	//Predicate의 중복을 제거하기위해서 비교할 목적으로 문자를 나중에 사용하기 위해 캐시해두는 변수가 필요 할 수있다.
	// Predicate를 리턴한다. (고차함수)		
	public static Predicate<String> checkifStartWin (final String letter) {
		return name -> name.startsWith(letter);
	}
	
public static void pickName(			final List<String> names,final String startingLetter) {
	final Optional<String> foundName =names.stream().filter(name-> name.startsWith(startingLetter)).findFirst();
	
	System.out.println(String.format("A name start with %s: %s", startingLetter, foundName.orElse("No name found")));
}

	
	
	
	
}
