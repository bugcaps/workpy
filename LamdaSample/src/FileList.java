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
		//flatMap ���� : �⺻���� ���۷��̼��� ����� �Ŀ� �� ���ϴ�.
		//������Ʈ�� �÷��ǿ� �����ϴ� ���� Map �޼���� ������, ���� ǥ���Ŀ��� ������Ʈ�� �κп��� ��Ʈ���� �����Ѵ�.
		// �� ������Ʈ�� �����ؼ� ���� ���� ��Ʈ���� �ϳ��� �÷� ��Ʈ������ �÷��ϰ� �ȴ�.
		List<File> files= Stream.of(new File("C:\\01.workHome\\source").listFiles())
						.flatMap(file->file.listFiles()==null ? Stream.of(file) : Stream.of(file.listFiles()))
						.filter(file->file.isFile() )
						.collect(Collectors.toList());
		
		files.forEach(System.out::println);

	}
	
	// �ΰ��� �Ķ���͸� ���´�. �ϳ��� String�� ����Ʈ�̰� �ٸ� �ϳ��� String�� ����ؾ������� �Ǵ��ϴ� Predicate�̴�.
	 public static int totalAssetValue(final List<String> assets, final Predicate<String> assetSelector)
	 {
		 return assets.stream().filter(assetSelector).mapToInt(String :: length).sum();
	 }

}
