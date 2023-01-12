package Project;

import java.io.File;

class Main {
    public static void main(String[] args) {
        while(true) {
            Project projectget = new Project();
            projectget.initServer(9527);
            System.out.println("Please wait");
            String getf = projectget.getFile();
            File f1 = new File(getf);
            f1.deleteOnExit();
            projectget.RPN();

            String trFile = projectget.transformFile();
            System.out.println("Done!");
            projectget.serverStop();
            Project projectsend = new Project();
            projectsend.initServer(9528);
            projectsend.sendFile(trFile);
            File f2 = new File(trFile);
            f2.deleteOnExit();
            projectsend.serverStop();
        }
    }
}
