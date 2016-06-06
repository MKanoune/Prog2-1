package de.hsa.games.fatsquirrel.Core.Board;

import de.hsa.games.fatsquirrel.Core.Entity.Entities.*;
import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.MasterSquirrel;
import de.hsa.games.fatsquirrel.Core.Entity.Entities.Squirrel.MasterSquirrelPlayer;
import de.hsa.games.fatsquirrel.Core.Entity.Entity;
import de.hsa.games.fatsquirrel.Core.Entity.EntityContainer;
import de.hsa.games.fatsquirrel.Core.Movement.XY;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Manager for the Board, its kind of a Interface between the FlattenedBoard and other Classes
 */
public class Board {
    private XY size;
    private final EntityContainer container;
    private int ID = 0;
    private final BoardConfig config;
    private Map<String, ArrayList<Integer>> Highscore = new HashMap<>();
    private MasterSquirrelPlayer master;
    private int rounds;
    private int duration;


    /**
     * Constructor for the Board
     * Generates all start Entities and insert them in the Container
     * At first a Big WALL around the field is generated
     * After that it gets the start Values, for how much Entities are needed from the BoardConfig.
     * Then it spawns them at a random Position on the field
     */
    public Board() {
        config = new BoardConfig();
        size = new XY(config.WIDTH, config.HEIGHT);
        container = new EntityContainer();
        rounds = config.MAX_ROUNDS;
        duration = config.DURATION;
        setStart();
        setPlayer();
        setBots();
        getHighscore();
    }

    private void setStart() {
        FileReader fr;
        try {
            fr = new FileReader("/home/max/Git/Prog2/src/Config.txt");
            BufferedReader br = new BufferedReader(fr);
            while (true) {
                String high = br.readLine();
                if (high.equals("Start:")) {
                    break;
                }
            }
            String configSize = br.readLine();
            String[] split = configSize.split("#");
            size = new XY(Integer.parseInt(split[1]), Integer.parseInt(split[2]));
            setWalls();
            for(int e = 0; e<4;e++){
                String count = br.readLine();
                String[] spl = count.split("#");
                for(int i =0;i<Integer.parseInt(spl[1]);i++){
                    Class<?> cl = Class.forName(spl[0]);
                    Constructor<?> constructor = cl.getConstructor(int.class,XY.class);
                    container.getCollection().add(constructor.newInstance(getNextID(),getRandomPos()));
                }
            }
            br.close();
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            container.getCollection().clear();
            defaultStart();
            System.err.println("Zomg!\nIrgendwas doofes ist in Config_Start: passiert :/");
            e.printStackTrace();
        }
    }

    private void setWalls() {
        for (int x = 0; x < size.getX(); x++) { //Build from left to Right
            container.insert(new Wall(getNextID(), new XY(x, 0)));
        }
        for (int y = 1; y < size.getY() - 1; y++) { //Build from the right Corner down
            container.insert(new Wall(getNextID(), new XY(size.getX() - 1, y)));
        }
        for (int x = size.getX() - 1; x >= 0; x--) { //Build from left to right
            container.insert(new Wall(getNextID(), new XY(x, size.getY() - 1)));
        }
        for (int y = size.getY() - 2; y >= 1; y--) { //build from left down corner up
            container.insert(new Wall(getNextID(), new XY(0, y)));
        }

        container.addToAdd();
    }

    private void setPlayer() {
        if(config.PLAYERMODE){
            master = new MasterSquirrelPlayer(getNextID(), getRandomPos());
            container.insert(master);
        }
    }

    public MasterSquirrelPlayer getPlayer() {
        return master;
    }

    private void setBots() {
        try {
            FileReader fr = new FileReader("/home/max/Git/Prog2/src/Config.txt");
            BufferedReader br = new BufferedReader(fr);
            try {
                br.readLine();
                String bCount = br.readLine();
                String[]s = bCount.split("#");
                int botCount = Integer.parseInt(s[1]);
                for(int i = 0; i < botCount;i++){
                    Class<?> cl = Class.forName(br.readLine());
                    Constructor<?> constructor = cl.getConstructor(int.class,XY.class);
                    container.getCollection().add(constructor.newInstance(getNextID(), getRandomPos()));
                }
                br.close();
            }catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | IOException e) {
                System.out.println("Class not found");
                e.printStackTrace();
            }

        }catch (FileNotFoundException g) {
            try {
                for(int i = 0; i < config.BOTCOUNT; i++){
                    Class<?> cl = Class.forName(config.BOTNAME[i]);
                    Constructor<?> constructor = cl.getConstructor(int.class,XY.class);
                    container.getCollection().add(constructor.newInstance(getNextID(), getRandomPos()));
                    g.printStackTrace();
                }
            }catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException d) {
                System.out.println("Class not found");
                d.printStackTrace();
            }

        }
    }

    private void defaultStart() {
        setWalls();

/************************************************BADBEASTS************************************************/
        for (int i = 0; i < config.BADBEAST; i++) {
            container.insert(new BadBeast(getNextID(), getRandomPos()));
        }

        container.addToAdd();

/************************************************GOODBEASTS************************************************/
        for (int i = 0; i < config.GOODBEAST; i++) {
            container.insert(new GoodBeast(getNextID(), getRandomPos()));
        }

        container.addToAdd();

/************************************************BADPLANTS************************************************/
        for (int i = 0; i < config.BADPLANT; i++) {
            container.insert(new BadPlant(getNextID(), getRandomPos()));
        }

        container.addToAdd();

/************************************************GOODPLANTS************************************************/
        for (int i = 0; i < config.GOODPLANT; i++) {
            container.insert(new GoodPlant(getNextID(), getRandomPos()));
        }
        container.addToAdd(); //TODO javadoc
    }

    /**
     * Generates a new ID.
     * Every ID is unique so every Object has its own
     *
     * @return The ID as Int
     */
    public int getNextID() {
        return ++ID;
    }


    /**
     * Generates a random Position on the Field.
     * If the generated Position is already occupied a new Position is generated
     *
     * @return The random Position as XY Object
     */
    public XY getRandomPos() {
        java.util.Random r = new java.util.Random();
        Entity[][] entities = flatten().getEntities();

        int xCord = r.nextInt(entities[0].length - 1);
        int yCord = r.nextInt(entities.length - 1);

        if (entities[yCord][xCord] != null) return getRandomPos();
        else {
            return new XY(xCord, yCord);
        }
    }


    /**
     * Generates the 2D Field, its a other form of an update because the old one isn't used after that anymore
     *
     * @return The new Field
     */
    public FlattenedBoard flatten() {
        return new FlattenedBoard(this);
    }


    /**
     * Start the next Round.
     * All Entity movements are executed
     */
    public void update() {
        FlattenedBoard FB = flatten();
        container.startNextStep(FB);

        duration--;

        if(duration <= 0) {
            try {
                rounds--;
                if(rounds == 0) {
                    setHighscore();
                    openHighscore();
                    System.exit(0);
                }
                Thread.sleep(10000);
                duration = config.DURATION;
                deleteStartEntities();
                setStart();
            }catch (InterruptedException e) {
                System.out.println("Zomg!\nIrgendwas doofes ist passiert :/");
                e.printStackTrace();
            }
        }
    }

    public void deleteStartEntities(){
        container.deleteAll();
    }


    /**
     * Get the Size of the Field
     *
     * @return The Size as XY Object, but in this Case they aren't Coordinates but Length and Width.
     */
    public XY getSize() {
        return size;
    }


    /**
     * Get the actual Container and its Entities
     * @return The Entity Array.
     */
    public EntityContainer getContainer() {
        return container;
    }


    /**
     * Get the Information if a Position is empty
     *
     * @param pos The Position which has to be checked as XY Object
     * @return A Boolean true or false
     */
    public boolean isPositionEmpty(XY pos) {
        return (flatten().getEntity(pos) == null);
    }


    public void getHighscore(){
        FileReader fr;
        try {
            fr = new FileReader("/home/max/Git/Prog2/src/Highscore.txt");
            BufferedReader br = new BufferedReader(fr);
            while(true){
                String high = br.readLine();
                if(high == null){
                    break;
                }
                String [] splitted = high.split("\t");
                ArrayList<Integer> list = new ArrayList<>();
                for(int i = 1;i<splitted.length;i++){
                    list.add(Integer.parseInt(splitted[i]));
                }
                Highscore.put(splitted[0],list);
            }
            System.out.println("Latest Highscore: "+Highscore.toString());
            br.close();

        } catch (IOException e) {
            System.err.println("No Highscore found");
            e.printStackTrace();
        }
    }

    public void setHighscore() {
        FileWriter fw;
        try {
            fw = new FileWriter("/home/max/Git/Prog2/src/Highscore.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for(int i = 0; i < container.getCollection().size();i++){
                if(container.getEntity(i) instanceof MasterSquirrel){
                    String key = container.getEntity(i).getClass().getName();
                    Integer highscore = container.getEntity(i).getEnergy();
                    ArrayList<Integer> l = new ArrayList<>();
                    if(Highscore.get(key)==null){
                        l.add(highscore);
                        Highscore.put(key, l);
                    }else{
                        l = Highscore.get(key);
                        l.add(highscore);
                        Collections.sort(l);
                        Collections.reverse(l);
                        Highscore.put(key, l);
                    }
                    String s = "";
                    for(int j=0;j<l.size();j++){
                        s += l.get(j);
                        s += "\t";
                    }
                    bw.write(key+"\t"+ s);
                    bw.newLine();

                }
            }
            bw.close();
        } catch (IOException e1) {
            System.err.println("Datei wurde nicht gefunden werden");
            for(int i = 0; i < container.getCollection().size(); i++){
                if(container.getEntity(i) instanceof MasterSquirrel){
                    String key = container.getEntity(i).getClass().getName();
                    ArrayList<Integer> l = new ArrayList<>();
                    for(int j = 0; j<Highscore.get(key).size(); j++){
                        l.add(Highscore.get(key).get(i));
                    }
                    Integer highscore = container.getEntity(i).getEnergy();
                    l.add(highscore);
                    Highscore.put(key,l);
                }
            }
            e1.printStackTrace();
        }
    }

    public void openHighscore(){
        File file = new File("/home/max/Git/Prog2/src/Highscore.txt");

        if(!Desktop.isDesktopSupported()){
            System.out.println("Desktop is not supported");
            return;
        }

        Desktop desktop = Desktop.getDesktop();
        if(file.exists())
            try {
                desktop.open(file);
            } catch (IOException e) {
                System.err.println("can not open Highscore.txt");
                e.printStackTrace();
            }
    }
}
