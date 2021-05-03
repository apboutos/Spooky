package com.apboutos.spooky.utilities;

import com.apboutos.spooky.units.Block;
import com.apboutos.spooky.units.Enemy;
import com.apboutos.spooky.units.Unit;
import com.badlogic.gdx.Gdx;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to initialize the level from the internal level files.
 */
public class LevelInitializer {

    private static final String TAG = "LevelInitializer";

    /**
     * Returns a list containing the units of the level. The units are created by parsing the XML file
     * of the specified level.
     */
    @SuppressWarnings("ConstantConditions")
    public static List<Unit> initializeUnits(int levelID)
    {
        List<Unit> units = new ArrayList<>();
        Document document = null;
        //TODO Filepath loading must be resolved to work for android and ios as well.
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("C:\\Users\\exoph\\Documents\\Java Projects\\Spooky\\android\\assets\\Levels/Level " + levelID + ".xml");
        } catch (ParserConfigurationException | IOException | SAXException e) {
            Gdx.app.error(TAG,e.getMessage(),e);
        }

        NodeList nodeList = document.getElementsByTagName("unit");
        for(int i=0;i<nodeList.getLength();i++){
            Node node = nodeList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;

                String x = element.getElementsByTagName("x").item(0).getTextContent();
                String y = element.getElementsByTagName("y").item(0).getTextContent();
                String type = element.getElementsByTagName("type").item(0).getTextContent();

                units.add(createNewUnit(x,y,type));
            }
        }
        return units;
    }

    /**
     * Creates a new Unit from the given parameters.
     */
    private static Unit createNewUnit(String x, String y, String type){

        switch (type){
            case "Standard":    return new Block(Integer.parseInt(x),Integer.parseInt(y),BlockType.Standard);
            case "Bouncing":    return new Block(Integer.parseInt(x),Integer.parseInt(y),BlockType.Bouncing);
            case "BigBouncing": return new Block(Integer.parseInt(x),Integer.parseInt(y),BlockType.BigBouncing);
            case "Dynamite":    return new Block(Integer.parseInt(x),Integer.parseInt(y),BlockType.Dynamite);
            case "BigDynamite": return new Block(Integer.parseInt(x),Integer.parseInt(y),BlockType.BigDynamite);
            case "Diamond":     return new Block(Integer.parseInt(x),Integer.parseInt(y),BlockType.Diamond);
            case "Fish":        return new Enemy(Float.parseFloat(x),Float.parseFloat(y),EnemyType.Fish);
            case "Shark":       return new Enemy(Float.parseFloat(x),Float.parseFloat(y),EnemyType.Shark);
            default:            throw new IllegalStateException("Type does not match with any known types");
        }
    }
}
