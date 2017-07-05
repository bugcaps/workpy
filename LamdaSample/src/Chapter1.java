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
		
		//���ٽ�
		BigDecimal totofDisconted = BigDecimal.ZERO;
		
		final BigDecimal totofDisconted2 = prices.stream().filter(price->price.compareTo(BigDecimal.valueOf(20)) >0 )
				.map(price->price.multiply(BigDecimal.valueOf(0.9)))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		System.out.println(totofDisconted2);
		
		// �ڹٽ�
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
		//java ���
		final List<String> uppercaseNames = new ArrayList<String>();
		for (String name : friends) { uppercaseNames.add(name.toUpperCase()); }
		//java ���
		friends.forEach(name->uppercaseNames.add(name.toUpperCase()));
		
		// ���ο� ��Ʈ�� �������̽��� map()�޼��带 ����ϸ� �������� �߻����� �ʵ��� �� �� ������ �ڵ带 �� �����ϰ� ������ִ�.
		// map()�޼���� �Է� ������ ��¼����� �����ϰų� �Է¼����� �ٸ� ������ ����� �����Ѵ�.
		friends.stream().map(name->name.toUpperCase()).forEach(name->System.out.print(name + " "));
		friends.stream().map(name->name.length()).forEach(count->System.out.print(count + " "));
		//filter�� return���� boolean����� �����ϴ� ����ǥ������ �ʿ��ϴ�.
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
		
		//�㸮�ེ �����̴�.���۷��̼��� �й��ϴ� map �հ��� ����� sum���� reduce�Ѵ�.
		friends.stream().mapToInt(name->name.length()).sum();
		//reduce �޼ҵ带 Ȱ���Ͽ�  �ΰ��� ������Ʈ�� ���� ���ϰ� �÷��ǿ� 
		//�����ִ� ������Ʈ�� �񱳸� ���� ����� ����.
		final Optional<String> aLongName = friends.stream().reduce(
				        (name1, name2) -> name1.length() >= name2.length() ? name1 :name2
				        		);
		aLongName.ifPresent(name -> System.out.println(String.format("%s", name)));
		
		System.out.println(
				friends.stream().map(String::toUpperCase).collect(Collectors.joining(", "))
				);
	}
	
	//Predicate�� �ߺ��� �����ϱ����ؼ� ���� �������� ���ڸ� ���߿� ����ϱ� ���� ĳ���صδ� ������ �ʿ� �� ���ִ�.
	// Predicate�� �����Ѵ�. (�����Լ�)		
	public static Predicate<String> checkifStartWin (final String letter) {
		return name -> name.startsWith(letter);
	}
	
public static void pickName(			final List<String> names,final String startingLetter) {
	final Optional<String> foundName =names.stream().filter(name-> name.startsWith(startingLetter)).findFirst();
	
	System.out.println(String.format("A name start with %s: %s", startingLetter, foundName.orElse("No name found")));
}

	
	
	
	
}
