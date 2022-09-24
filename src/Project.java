import java.io.*;

public class Project {
    public static void main(String[] args)
    {
        try(FileInputStream fin=new FileInputStream("/Users/egorchik/Downloads/Project1/src/input.txt");
            FileOutputStream fos=new FileOutputStream("/Users/egorchik/Downloads/Project1/src/output.txt"))
        {
            byte[] buffer = new byte[fin.available()];

            fin.read(buffer, 0, buffer.length);

            fos.write(buffer, 0, buffer.length);
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }


    }
}