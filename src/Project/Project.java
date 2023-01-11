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
                text = text.replace(expression, Ideone.calc(ExpressionParser.parse(expression)));
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

}