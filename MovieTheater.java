package com.example.week9;

import android.content.Context;
import android.os.StrictMode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

class MovieTheater {

    DocumentBuilder builder;
    private String tunnus;
    private String nimi;
    private String aika;
    private String title;

    public static ArrayList<Theatre> theatre_list = new ArrayList<Theatre>();
    public  ArrayList<Movie> movie_list = new ArrayList<Movie>();
    Context context = null;

    private static MovieTheater instance;

    public static MovieTheater getInstance() {
        if (instance == null) {
            instance = new MovieTheater();
        }
        return instance;
    }


    private MovieTheater() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String urlString = "https://www.finnkino.fi/xml/TheatreAreas/";
            Document doc = builder.parse(urlString);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getDocumentElement().getElementsByTagName("TheatreArea");

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    tunnus = element.getElementsByTagName("ID").item(0).getTextContent();
                    nimi = element.getElementsByTagName("Name").item(0).getTextContent();
                    System.out.println(tunnus + " " + nimi);
                    theatre_list.add(new Theatre(tunnus, nimi));
                }

            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<String> returnTheatreList() {
        ArrayList<String> tl = new ArrayList<>();
        for (int i = 0; i < theatre_list.size(); i++) {
            Theatre t = theatre_list.get(i);
            if (t.getName().contains(":")) {
                String s = (t.getName() + " " + t.getID());
                tl.add(s);
            }
        }
        return tl;
    }


    public void getSchedule(Theatre theatre) throws IOException, SAXException {
        movie_list.clear();
        builder.reset();
        Calendar c = Calendar.getInstance();
        String s = String.format(Locale.GERMANY, "%d.%02d.%d", c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH) + 1, c.get(Calendar.YEAR));
        System.out.println(s);
        String urlString = "https://www.finnkino.fi/xml/Schedule/?area=" + theatre.getID() + "&dt=" + s;
        Document doc = builder.parse(urlString);
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getDocumentElement().getElementsByTagName("Shows");
        NodeList showList = nList.item(0).getChildNodes();

        for (int i = 0; i < showList.getLength(); i++) {
            Node node = showList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                aika = element.getElementsByTagName("dttmShowStart").item(0).getTextContent();
                title = element.getElementsByTagName("Title").item(0).getTextContent();
                System.out.println(aika + " " + title);
                movie_list.add(new Movie(aika, title));
            }

        }
    }

    public ArrayList<String> returnMovieList(Theatre theatre, String date) throws IOException, SAXException {
        getSchedule(theatre);
        ArrayList<String> ml = new ArrayList<>();
        for (int i = 0; i < movie_list.size(); i++) {
            Movie m = movie_list.get(i);
            String l = (m.getName() + " " + m.getTime());
            ml.add(l);
        }
        return ml;

    }
}




