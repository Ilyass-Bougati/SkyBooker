package skybooker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import skybooker.entity.Client;
import skybooker.entity.Reservation;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Configuration cfg = new Configuration().configure();
        SessionFactory sessionFactory;
        Session session;
        Transaction transaction;

        try {
            sessionFactory = cfg.buildSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try {
            Reservation res = new Reservation();
            Client ilyass = new Client();
            ilyass.setNom("Ilyass");
            ilyass.setPrenom("Bougati");
            ilyass.setCIN("Something");
            ilyass.setEmail("a@a.a");
            ilyass.setPassword("password");
            ilyass.setAdresse("Mok");
            ilyass.setTelephone("000");
            session.save(ilyass);

            res.setClient(ilyass);
            session.save(res);
            transaction.commit();
            session.close();
            launch();
        } catch (Exception e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }
    }
}