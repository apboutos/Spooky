package com.apboutos.spooky.utilities;

import com.apboutos.spooky.boot.Spooky;
import com.apboutos.spooky.units.Unit;
import com.apboutos.spooky.units.block.Bouncing;
import com.apboutos.spooky.units.block.Diamond;
import com.apboutos.spooky.units.block.Dynamite;
import com.apboutos.spooky.units.block.Standard;
import com.apboutos.spooky.units.enemy.Fish;
import com.apboutos.spooky.units.enemy.Shark;
import com.badlogic.gdx.Gdx;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class LevelInitializer {

    private static final String TAG = "LevelInitializer";



    @SuppressWarnings("ConstantConditions")
    public static void initialize(int level , List<Unit> units , Spooky spooky)
    {
        Document document = null;
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(Gdx.files.internal("Levels/Level " + level + ".xlm").path());
        } catch (ParserConfigurationException | IOException | SAXException e) {
            Gdx.app.error(TAG,e.getMessage(),e);
        }

        NodeList nodeList = document.getElementsByTagName("unit");
        for(int i=0;i<nodeList.getLength();i++){
            Node node = nodeList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;

                String x = element.getElementsByTagName("x").item(0).getTextContent();
                String y = element.getElementsByTagName("y").item(1).getTextContent();
                String type = element.getElementsByTagName("type").item(2).getTextContent();

                units.add(createNewUnit(x,y,type, spooky));
            }
        }
    }

    private static Unit createNewUnit(String x, String y, String type, Spooky spooky){

        switch (type){
            case "Standard":    return new Standard(Integer.parseInt(x),Integer.parseInt(y), spooky.batch, BlockType.Standard);
            case "Bouncing":    return new Bouncing(Integer.parseInt(x),Integer.parseInt(y), spooky.batch, BlockType.Bouncing);
            case "BigBouncing": return new Bouncing(Integer.parseInt(x),Integer.parseInt(y), spooky.batch, BlockType.BigBouncing);
            case "Dynamite":    return new Dynamite(Integer.parseInt(x),Integer.parseInt(y), spooky.batch, BlockType.Dynamite);
            case "BigDynamite": return new Dynamite(Integer.parseInt(x),Integer.parseInt(y), spooky.batch, BlockType.BigDynamite);
            case "Diamond":     return new Diamond(Integer.parseInt(x),Integer.parseInt(y), spooky.batch, BlockType.Diamond);
            case "Fish":        return new Fish(Float.parseFloat(x),Float.parseFloat(y), spooky.batch,EnemyType.Fish);
            case "Shark":       return new Shark(Float.parseFloat(x),Float.parseFloat(y), spooky.batch,EnemyType.Shark);
            default:            throw new IllegalStateException("Type does not match with any known types");
        }
    }
}
