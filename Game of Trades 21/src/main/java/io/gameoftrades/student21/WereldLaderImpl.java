package io.gameoftrades.student21;


import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.kaart.*;
import io.gameoftrades.model.lader.WereldLader;
import io.gameoftrades.model.markt.Handel;
import io.gameoftrades.model.markt.HandelType;
import io.gameoftrades.model.markt.Handelswaar;
import io.gameoftrades.model.markt.Markt;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class WereldLaderImpl implements WereldLader {
    int[] dimensions = new int[2];
    Handel testHandel = null;
    int coordinaatException;
    boolean doesStadExist = true;
    boolean isHandelPrijsWrong;
    boolean isHandelVraagWrong;
    Kaart kaart = null;

    @Override
    public Wereld laad(String resource) throws IllegalArgumentException {


        InputStream resourceStream = this.getClass().getResourceAsStream(resource);


        BufferedReader bufferedResourceReader = new BufferedReader(new InputStreamReader(resourceStream));
        dimensions = readDimensions(bufferedResourceReader);

        System.out.println("Dit zijn de dimensies van deze kaart: " + dimensions[0] + " " + dimensions[1]);


        HashMap<String, Stad> stedenMap = new HashMap<String, Stad>();
        ArrayList<Stad> steden = new ArrayList<Stad>();
        ArrayList<Handel> handels = new ArrayList<Handel>();
        Markt markt = null;
        Wereld wereld = null;
        if (dimensions[0] == 0 && dimensions[1] == 0) {
            System.out.println("Dit is een lege kaart");
            kaart = laadKaart(bufferedResourceReader, dimensions);
            markt = new Markt(handels);
            wereld = Wereld.van(kaart, steden, markt);
            return wereld;
        }

        kaart = laadKaart(bufferedResourceReader, dimensions);
        if (kaart == null) {
            throw new IllegalArgumentException("Er ging iets mis bij het laden van de kaart");
        }
        readTerreinFromResource(kaart, bufferedResourceReader);


        steden = laadSteden(bufferedResourceReader, dimensions);

        System.out.println("CoordinaatException is: " + coordinaatException);
        if (coordinaatException == -1) {
            throw new IllegalArgumentException("De stad heeft rare coordinaten");
        }

        for (int i = 0; i < steden.size(); i++) {
            stedenMap.put(steden.get(i).getNaam(), steden.get(i));
            System.out.println(steden.get(i));
        }
        handels = laadHandel(bufferedResourceReader, dimensions, stedenMap);
        if (!doesStadExist) {
            System.out.println("The handelException has been triggered");
            System.out.println("doesStadExist is :" + doesStadExist);
            throw new IllegalArgumentException("Er ging iets mis bij het laden van de handel");
        } else if (isHandelPrijsWrong) {
            System.out.println("The handelException has been triggered");
            System.out.println("isHandelPrijsWrong is :" + isHandelPrijsWrong);
            throw new IllegalArgumentException("Er ging iets mis bij het laden van de handel");
        } else if (isHandelVraagWrong) {
            System.out.println("The handelException has been triggered");
            System.out.println("isHandelVraagWrong is: " + isHandelVraagWrong);
            throw new IllegalArgumentException("Er ging iets mis bij het laden van de handel");
        }
        markt = new Markt(handels);
        wereld = Wereld.van(kaart, steden, markt);


        //
        // Gebruik this.getClass().getResourceAsStream(resource) om een resource van het classpath te lezen.
        //
        // Kijk in src/test/resources voor voorbeeld kaarten.
        //
        // TODO Laad de wereld!
        //

        return wereld;
    }


    public Kaart laadKaart(BufferedReader resourceBuf, int[] dimensions) throws IllegalArgumentException {
        Kaart kaart = null;
        int breedte = dimensions[0];
        int hoogte = dimensions[1];
        kaart = Kaart.metOmvang(breedte, hoogte);
        return kaart;
    }

    public int[] readDimensions(BufferedReader resourceBuf) {
        String[] parts = new String[2];
        int[] dimensions = new int[2];
        try {


            String dimensionString = resourceBuf.readLine();
            parts = dimensionString.split(",");
            parts[0] = parts[0].replaceAll("\\s+", "");
            parts[1] = parts[1].replaceAll("\\s+", "");
            dimensions[0] = Integer.parseInt(parts[0]);
            dimensions[1] = Integer.parseInt(parts[1]);


        } catch (Exception e) {
            e.printStackTrace();
        }


        return dimensions;
    }

    public void readTerreinFromResource(Kaart kaart, BufferedReader resourceBuf) throws IllegalArgumentException {
        int breedte = dimensions[0];
        int hoogte = dimensions[1];
        int terreinException = 0;
        String nthLine = "";


        //try {
        //while(resourceBuf.readLine().length() != breedte) {

        //}
        //}catch(Exception e) {
        //e.printStackTrace();}

        for (int i = 0; i < hoogte; i++) {
            try {
                nthLine = resourceBuf.readLine();
                nthLine = nthLine.replaceAll("\\s+", "");
                if (nthLine.length() != breedte && breedte > 0) {
                    System.out.println("de lengte van de terreinString: " + nthLine.length() + " De breedte van de kaart: " + breedte);
                    terreinException = -1;
                    //break;

                }

            } catch (Exception ex) {
                ex.toString();
            }
            for (int j = 0; j < breedte; j++) {
                if (terreinException == -1) {
                    nthLine = "";
                    throw new IllegalArgumentException("Fout! de breedte komt niet overeen met de werkelijke breedte");
                }
                Coordinaat c = Coordinaat.op(j, i);
                //System.out.println("De terreinException is: " + terreinException);
                System.out.println(nthLine.charAt(j));
                Terrein.op(kaart, c, TerreinType.fromLetter(nthLine.charAt(j)));
            }
        }

    }


    public ArrayList<Stad> laadSteden(BufferedReader resourceBuf, int[] dimensions) {
        int numberOfCities = 0;
        int stadBreedte = 0;
        int stadHoogte = 0;
        Stad stad;
        Coordinaat c = null;
        ArrayList<Stad> steden = new ArrayList<Stad>();
        String[] stedenData = new String[3];
        String numberOfCitiesString;

//    	try {
//			while(resourceBuf.readLine().length() == dimensions[0] || resourceBuf.readLine().contains(",")) {
//				
//			}
//			}catch(Exception e) {
//				e.printStackTrace();}

        try {
            numberOfCitiesString = resourceBuf.readLine();
            numberOfCitiesString = numberOfCitiesString.replaceAll("\\s+", "");
            numberOfCities = Integer.parseInt(numberOfCitiesString);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (numberOfCities > 0) {

            try {
                for (int i = 0; i < numberOfCities; i++) {
                    String stadString = resourceBuf.readLine();
                    stedenData = stadString.split(",");
                    stadBreedte = Integer.parseInt(stedenData[0]);
                    stadHoogte = Integer.parseInt(stedenData[1]);
                    if (stadBreedte == 0 || stadHoogte == 0) {
                        coordinaatException = -1;
                        throw new IllegalArgumentException("De stad heeft rare coordinaten");
                    }
                    c = Coordinaat.op(stadBreedte-1, stadHoogte-1);
                    stad = Stad.op(c, stedenData[2]);
                    steden.add(stad);
                }

            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }


        return steden;
    }

    public ArrayList<Handel> laadHandel(BufferedReader resourceBuf, int[] dimensions, HashMap<String, Stad> stedenMap) throws IllegalArgumentException {
        int aantalHandel = 0;
        ArrayList<Handel> handels = new ArrayList<Handel>();
        String[] handelData = new String[4];
        Stad stad;
        Handel handel = null;
        int prijs = 0;
        String prijsString = "";
        String aantalHandelString;
        String vraagString;
        int stadException = 0;
        int prijsException = 0;
        int vraagException = -1;

//        try{
//			while(resourceBuf.readLine().length() == dimensions[0] || resourceBuf.readLine().contains("BIEDT") || resourceBuf.readLine().contains("VRAAGT")) {
//				
//			}
//        }catch (Exception ex) {
//            ex.printStackTrace();
//        }
        try {
            aantalHandelString = resourceBuf.readLine();
            aantalHandelString = aantalHandelString.replaceAll("\\s+", "");
            aantalHandel = Integer.parseInt(aantalHandelString);
            System.out.println(aantalHandel);
            if (aantalHandel == 0) {
                return handels;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            for (int i = 0; i < aantalHandel; i++) {
                if (aantalHandel == 0) {
                    throw new IllegalArgumentException("Er is geen handel");
                }
                String handelString = resourceBuf.readLine();
                handelData = handelString.split(",");
                vraagString = handelData[1];
                vraagString = vraagString.replaceAll("\\s+", "");
                System.out.println("Dit zijn de handelData: " + handelData[0] + " " + handelData[1] + " " + handelData[2] + " " + handelData[3]);
                if (vraagString.equalsIgnoreCase("BIEDT") || vraagString.equalsIgnoreCase("VRAAGT")) {
                    System.out.println("VraagString is: " + vraagString);
                    vraagException = 0;
                }
                try {
                    if (handelData[3].contains(".")) {
                        System.out.println(handelData[3] + " Dit is niet van het type int");
                        throw new IllegalArgumentException("Die prijs klopt niet gozert");


                    }
                    prijsString = handelData[3];
                    prijsString = prijsString.replaceAll("\\s+", "");
                    prijs = Integer.parseInt(prijsString);
                } catch (IllegalArgumentException e) {
                    e.fillInStackTrace();
                }

                System.out.println(prijs);
                if (prijs <= 0) {
                    prijsException = -1;
                    throw new IllegalArgumentException("De prijs is kleiner of gelijk aan 0");
                }
                System.out.println(handelData[0]);
                stad = stedenMap.get(handelData[0]);
                if (stad == null) {
                    stadException = -1;
                    throw new IllegalArgumentException("Er is geen stad met die naam");
                }
                handel = new Handel(stad, HandelType.valueOf(handelData[1]), new Handelswaar(handelData[2]), prijs);
                handels.add(handel);
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }
        if (stadException == -1) {
            doesStadExist = false;
            System.out.println("stadException is: " + stadException);
            stadException = 0;

        } else if (prijsException == -1) {
            System.out.println("prijsException is: " + prijsException);
            isHandelPrijsWrong = true;
            prijsException = 0;
        } else if (vraagException == -1) {
            System.out.println("vraagException is: " + vraagException);
            isHandelVraagWrong = true;
            vraagException = 0;
        }

        return handels;
    }

    public Kaart getKaart() {
        return this.kaart;
    }

}
