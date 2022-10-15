import java.io.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.ArrayList;

public class Project {
    public static void main(String[] args)
    {
        try(FileInputStream fin=new FileInputStream("/Users/egorchik/Downloads/Project1/src/input.txt");
            FileOutputStream fos=new FileOutputStream("/Users/egorchik/Downloads/Project1/src/output.txt"))
        {
            byte[] buffer = new byte[fin.available()];
            fin.read(buffer, 0, buffer.length);
            fos.write(buffer, 0, buffer.length);

            String str=null;
            try {
                List<String> s = Files.readAllLines(Paths.get("/Users/egorchik/Downloads/Project1/src/input.txt"), StandardCharsets.UTF_8);
                str=String.join(System.lineSeparator(), s);
            } catch (IOException e) {
                e.printStackTrace();
            }

           // File file = new File("/Users/egorchik/Downloads/Project1/src/input.txt");
            //Scanner scan = new Scanner(file);
           // String str = scan.nextLine();
            Pattern pattern = Pattern.compile("(\\d+(\\.\\d+)?)|(\\+|\\-|\\*|\\/)");
            Matcher matcher = pattern.matcher(str);
            List<String> strings = new ArrayList<>();
            while (matcher.find()) {
                strings.add(matcher.group());
            }
            //Integer i1 = new Integer(str);
            //System.out.println(i1);
            System.out.print(strings);
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }


    }
}