import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.nio.file.*;

public class FileList {

	public static void main(String[] args) throws IOException {

		//Files.list(Paths.get("C:\\01.workHome\\source")).filter(Files::isDirectory).forEach(System.out::println);
		//flatMap 사용법 : 기본적인 오퍼레이션을 사용한 후에 더 편리하다.
		//엘리먼트를 컬렉션에 매핑하는 것은 Map 메서드와 같으며, 람다 표현식에서 엘리먼트를 부분에서 스트림을 리턴한다.
		// 각 엘리먼트를 매핑해서 얻은 다중 스트림을 하나의 플랫 스트림으로 플랫하게 된다.
		List<File> files= Stream.of(new File("C:\\01.workHome\\source").listFiles())
						.flatMap(file->file.listFiles()==null ? Stream.of(file) : Stream.of(file.listFiles()))
						.filter(file->file.isFile() )
						.collect(Collectors.toList());
		
		files.forEach(System.out::println);

	}
	
	// 두개의 파라미터를 갖는다. 하난는 String의 리스트이고 다른 하나는 String을 고려해야할지를 판단하는 Predicate이다.
	 public static int totalAssetValue(final List<String> assets, final Predicate<String> assetSelector)
	 {
		 return assets.stream().filter(assetSelector).mapToInt(String :: length).sum();
	 }

}
