package Project;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Project {
    private Socket s;

    OutputStream os;
    InputStream is;

    private String flag;
    private String filename;

    void RPN() {
        Project readWrite = new Project();
        String text = readWrite.read(filename);

        Pattern MY_PATTERN = Pattern.compile("[()0-9]*( ){0,}([+-/*]( ){0,}[()0-9]{0,})*");
        Matcher m = MY_PATTERN.matcher(text);
        while (m.find()) {
            String expression = m.group();
            if(expression.equals("") || expression.equals(" ")) {
                continue;
            }
            try {
                text = text.replace(expression, Ideone.calc(Expression.parse(expression)));
            }
            catch(Exception e){
                ///
            }
        }
        readWrite.write(filename, text);
    }



    public void connectSocketServer(int port) {
        try {
            ServerSocket server = new ServerSocket(port);
            s = server.accept();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            is = s.getInputStream();
            os = s.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initServer(int port){
        while (s == null) {
            try {
                s = new Socket(InetAddress.getLocalHost(), port); // Подключиться к серверу
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }

        try {
            os = s.getOutputStream();
            is = s.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // чтение файла из SocketClient

    public void serverStop(){
        try {
            s.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getFile() {
        byte[] b=new byte[1024];
        try {// Определим входной поток,
            InputStream in = is;
            DataInputStream din = new DataInputStream (new BufferedInputStream(in)); // Создать файл для сохранения
            filename = din.readLine();
            flag = din.readLine();
            File f = new File(filename);
            RandomAccessFile fw = new RandomAccessFile(f, "rw");

            int num = din.read(b);
            while (num != -1) {// Записать 0 ~ num байтов в файл
                fw.write(b, 0, num); // Пропустить num байтов и снова записать в файл
                fw.skipBytes(num); // Чтение num байтов
                num = din.read(b);
            } // Закрыть входной и выходной потоки
            din.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

    public void sendFile(String filename) {

        try {
            PrintWriter printWriter = new PrintWriter(os, true);
            printWriter.println(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }

        byte[] b = new byte[1];
        File f = new File(filename);
        try {// Поток вывода данных
            OutputStream dout = new DataOutputStream(new BufferedOutputStream(s.getOutputStream ())); // Поток чтения файла
            InputStream ins = new FileInputStream(f);
            int n = ins.read(b);
            while (n != -1) {// Запись данных в сеть
                dout.write (b); // Отправить содержимое файла
                dout.flush (); // снова прочитать n байтов
                n = ins.read(b);
            } // Закрыть поток
            ins.close();
            dout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void create(String filename){
        try {
            File myObj = new File(filename);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void delete(String filename){
        File file = new File(filename);
        if(file.delete())
        {
            System.out.println("File deleted successfully");
        }
        else
        {
            System.out.println("Failed to delete the file");
        }
    }
}