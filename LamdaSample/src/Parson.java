import java.math.BigDecimal;
import java.util.Arrays;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.print.attribute.standard.JobOriginatingUserName;
public class Parson {

		private  final String name;
		private final int age;
		
		public Parson(final String name2, final int age2)
		{
			name=name2;
			age=age2;
		}
		public String getName() {
			return name;
		}
		public int getAge() {
			return age;
		}
		public int ageDiff(final Parson other)
		{
			return age-other.age;
		}
		public String toString()
		{
			return String.format("%s - %d",name,age);
		}
		
	public static void main(String[] args) {
		final List<Parson> people = Arrays.asList( new Parson("Jone",20)
												,new Parson("Sara",21),new Parson("Jane",21)
												,new Parson("Greg",35));
		List<Parson> ascending=people.stream().sorted((person1,person2)-> person1.ageDiff(person2)).collect(Collectors.toList());
		ascending.forEach(par ->System.out.println("test=>"+ par));
		
		people.stream().sorted(Parson::ageDiff   ).forEach(par ->System.out.println("test=>"+ par));
		
		//comparator 의 재사용
		Comparator<Parson> compareAcending = (parson1,parson2) ->parson1.ageDiff(parson2);
		Comparator<Parson> compareDecending = compareAcending.reversed();
		people.stream().sorted(compareAcending  ).forEach(par ->System.out.println("asc=>"+ par));
		people.stream().sorted(compareDecending  ).forEach(par ->System.out.println("desc=>"+ par));
		
		
		//나이가 가장 작은 사람을 출력
		people.stream().min(Parson :: ageDiff).ifPresent(young-> System.out.println(young));
		
		//복합 연산 정렬
		//나이순, 이름순 정렬
		final Function<Parson, Integer> byAge =person->person.getAge();
		final Function<Parson, String> byName =person->person.getName();
		List<Parson> list =people.stream().sorted(Comparator.comparing(byAge).thenComparing(byName))
						.collect(Collectors.toList());
		list.forEach(parson -> System.out.println(parson));
		
		///Collection groupby
		Map<Integer,List<Parson>> peopleByAge = people.stream().collect(Collectors.groupingBy(Parson::getAge));
		System.out.println(peopleByAge);
		//{35=[Greg - 35], 20=[Jone - 20], 21=[Sara - 21, Jane - 21]}
		
		Map<Integer,List<String>> peopleByAge2 = people.stream()
						.collect(Collectors.groupingBy(Parson::getAge,Collectors.mapping(Parson::getName,Collectors.toList() )));
		System.out.println(peopleByAge2);
		//{35=[Greg], 20=[Jone], 21=[Sara, Jane]}
	}
}
