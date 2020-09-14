/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author ericp
 */
public final class GraphicTwoDimension extends JPanel{
    
    private int longueur;
    private int hauteur;
    
    private Object[] x;
    private Number[] y;
    
    private int echelleX;
    private int echelleY;
    
    private int pointPolicy;
 
    private String nomAxeX;
    private String nomAxeY;
    
    private List<Point2D> listPoint;
    
    
    public static final int EVERY_POINTS = 1;
    public static final int ONLY_NEEDED_POINTS = 0;
    
    
    public <T,E> GraphicTwoDimension(int longueur, int hauteur, Object[] x, Number[] y, int echelleX, int echelleY, String nomAxeX, String nomAxeY)
    {
        setLongueur(longueur);
        setHauteur(hauteur);
        setX(x);
        setY(y);
        setEchelleX(echelleX);
        setEchelleY(echelleY);
        setPointPolicy(ONLY_NEEDED_POINTS);
        this.setSize(longueur,hauteur);
        this.setPreferredSize(new Dimension(longueur,hauteur));
        this.setBackground(Color.WHITE);
        setNomAxeX(nomAxeX);
        setNomAxeY(nomAxeY);
        System.out.println("JUSQUE ICI");
    }
    public <T,E> GraphicTwoDimension(int longueur, int hauteur, Object[] x, Number[]y, int echelleX, int echelleY,String nomAxeX, String nomAxeY, int pointPolicy)
    {
        setLongueur(longueur);
        setHauteur(hauteur);
        setX(x);
        setY(y);
        setEchelleX(echelleX);
        setEchelleY(echelleY);
        setPointPolicy(pointPolicy);
        this.setSize(longueur,hauteur);
        this.setPreferredSize(new Dimension(longueur,hauteur));
        this.setBackground(Color.WHITE);
        setNomAxeX(nomAxeX);
        setNomAxeY(nomAxeY);
        System.out.println("JUSQUE ICI");
    }

    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D)g;
        Line2D axeX = new Line2D.Double(10,hauteur-10, longueur-10, hauteur-10);
        Line2D axeY = new Line2D.Double(10,10, 10, hauteur-10);
        graphics2D.draw(axeX);
        graphics2D.draw(axeY);
        
        setPoint();
        drawEchelleX(graphics2D);
        drawEchelleY(graphics2D);
        
        drawCourbe(graphics2D);
        
    }
    
    private void drawEchelleX(Graphics2D graphics2D)
    {
        if(pointPolicy == EVERY_POINTS)
        {
            Line2D mark;
            for(int i = echelleX; i<longueur; i+=echelleX)
            {
                mark = new Line2D.Double(10+i, hauteur-5,10+i, hauteur+5 );
                graphics2D.draw(mark);
            }
        }
        else
        {
            if(pointPolicy == ONLY_NEEDED_POINTS)
            {
                Line2D mark;
                for(int i = 0; i<listPoint.size(); i+=1)
                {
                    mark = new Line2D.Double(listPoint.get(i).getX(), hauteur-5,listPoint.get(i).getX(), hauteur+5 );
                    graphics2D.draw(mark);
                }
            }
        }
        int tmpSize = nomAxeX.length() *4;
        graphics2D.drawString(nomAxeX, longueur-10-tmpSize, hauteur);
    }
    private void drawEchelleY(Graphics2D graphics2D)
    {
        if(pointPolicy == EVERY_POINTS)
        {
            Line2D mark;
            for(int i = echelleY; i<hauteur; i+=echelleY)
            {
                mark = new Line2D.Double(5,10+i,15,10+i);
                graphics2D.draw(mark);
            }
        }
        else
        {
            if (pointPolicy == ONLY_NEEDED_POINTS)
            {
                
                Line2D mark;
                List<Number> uniqueYValues = getUniqueValueOfY();
                for(int i = 0; i<uniqueYValues.size(); i+=1)
                {
                    System.out.println("new mark y = "+uniqueYValues.get(i).doubleValue()*i);
                    mark = new Line2D.Double(5,hauteur-uniqueYValues.get(i).doubleValue()*echelleY+10,15,hauteur-uniqueYValues.get(i).doubleValue()*echelleY+10);
                    graphics2D.draw(mark);
                }
            }
        }
        graphics2D.drawString(nomAxeY, 10, 10);
    }    
    private void setPoint()
    {
        
        listPoint = new ArrayList<Point2D>();
        System.out.println("x Length = " + x.length);
        for(int i = 0; i<x.length;i++)
        {
            System.out.println("Y i = "+y[i].floatValue());
            int xPoint = (echelleX*i)+10 > longueur ? longueur : (echelleX*i)+10; 
            int yPoint = Math.round(hauteur-(y[i].floatValue() * echelleY))+10;
            listPoint.add(new Point(xPoint, yPoint));
            
        }
        
        System.out.println("TEST : ");
        for (Point2D p : listPoint)
        {
            System.out.println("Point : "+ p.toString());
        }
        
    }
    
    private void drawCourbe(Graphics2D graphics2D)
    {
        Line2D courbe;
        System.out.println("DRAW COURBE");
        for(int i = 0 ; i< listPoint.size()-1; i++)
        {
            System.out.println("I = " + i);
            System.out.println("Point : "+ listPoint.get(i).toString());
            System.out.println("Point +1: "+ listPoint.get(i+1).toString());     
            
            courbe = new Line2D.Double(listPoint.get(i), listPoint.get(i+1));
            graphics2D.draw(courbe);
        }
    }
    
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(WIDTH, HEIGHT);
    }

    

    
    
    public int getLongueur() {
        return longueur;
    }

    public void setLongueur(int longueur) {
        this.longueur = longueur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }



    public void setX(Object[] x) {
        this.x = x;
    }


    public void setY(Number[] y) {
        this.y = y;
    }

    public int getEchelleX() {
        return echelleX;
    }

    public void setEchelleX(int echelleX) {
        this.echelleX = echelleX;
    }

    public int getEchelleY() {
        return echelleY;
    }

    public void setEchelleY(int echelleY) {
        this.echelleY = echelleY;
    }

    public int getPointPolicy() {
        return pointPolicy;
    }

    public void setPointPolicy(int pointPolicy) {
        this.pointPolicy = pointPolicy;
    }

    public List<Point2D> getListPoint() {
        return listPoint;
    }

    public void setListPoint(List<Point2D> listPoint) {
        this.listPoint = listPoint;
    }

    public String getNomAxeX() {
        return nomAxeX;
    }

    public void setNomAxeX(String nomAxeX) {
        this.nomAxeX = nomAxeX;
    }

    public String getNomAxeY() {
        return nomAxeY;
    }

    public void setNomAxeY(String nomAxeY) {
        this.nomAxeY = nomAxeY;
    }
    
    
    
    
    private List<Number> getUniqueValueOfY()
    {
        List<Number> tmp = new ArrayList<Number>();
        
        for(Number t: y)
        {
            if(!tmp.contains(t))
            {
                tmp.add(t);
            }
        }
        
        return tmp;
    }
    
    
    
}
