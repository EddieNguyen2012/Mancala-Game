package monymancala;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Path2D;

public class OceanStyle implements BoardStyle {
    private final Color seashellColor;
    private final Color coralColor;
    private final Color deepBlueColor;
    private final Color aquaColor;
    private final Color pearlColor;
    private final GradientPaint oceanGradient;
    
    public OceanStyle() {
        this.seashellColor = new Color(255, 245, 238); // Light shell color
        this.coralColor = new Color(255, 127, 80);     // Coral for accents
        this.deepBlueColor = new Color(0, 105, 148);   // Deep ocean blue
        this.aquaColor = new Color(127, 255, 212);     // Aqua for water effects
        this.pearlColor = new Color(240, 240, 240);    // Pearl-like stones
        this.oceanGradient = new GradientPaint(0, 0, new Color(0, 119, 190), 
                                               0, 100, new Color(0, 47, 108));
    }
    
    @Override
    public void drawPit(Graphics2D g2, int x, int y, int width, int height) {
        AffineTransform originalTransform = g2.getTransform();
        
        // seashell
        g2.setColor(seashellColor);
        g2.fillOval(x, y, width, height);
        // texture
        g2.setColor(new Color(0, 0, 0, 30)); // Transparent black for shadow
        g2.setStroke(new BasicStroke(1.5f));
       
        int centerX = x + width / 2;
        int centerY = y + height / 2;
        int maxRadius = Math.min(width, height) / 3;
        for (int i = 0; i < 3; i++) {
            Path2D spiral = new Path2D.Double();
            spiral.moveTo(centerX, centerY);
            
            for (double angle = 0; angle < Math.PI * 4; angle += 0.1) {
                double radius = (angle / (Math.PI * 2)) * maxRadius;
                double spiralX = centerX + radius * Math.cos(angle);
                double spiralY = centerY + radius * Math.sin(angle);
                spiral.lineTo(spiralX, spiralY);
            }
            
            g2.draw(spiral);
        }
        
        //seashell outline
        g2.setColor(coralColor);
        g2.setStroke(new BasicStroke(3));
        g2.drawOval(x, y, width, height);
        

        g2.setColor(new Color(aquaColor.getRed(), aquaColor.getGreen(), aquaColor.getBlue(), 50));
        for (int i = 0; i < 3; i++) {
            int rippleSize = (int)(width * (0.9 - i * 0.15));
            int rippleX = x + (width - rippleSize) / 2;
            int rippleY = y + (height - rippleSize) / 2;
            g2.drawOval(rippleX, rippleY, rippleSize, rippleSize);
        }
        
        g2.setTransform(originalTransform);
    }
    
    @Override
    public void drawMancala(Graphics2D g2, int x, int y, int width, int height) {
        Paint originalPaint = g2.getPaint();

        g2.setPaint(oceanGradient);
        RoundRectangle2D mancalaShape = new RoundRectangle2D.Double(x, y, width, height, width/4, height/4);
        g2.fill(mancalaShape);

        g2.setColor(coralColor);
        g2.setStroke(new BasicStroke(3));
        g2.draw(mancalaShape);
        
        g2.setColor(new Color(coralColor.getRed(), coralColor.getGreen(), coralColor.getBlue(), 120));
        g2.setStroke(new BasicStroke(2));
        
        for (int i = 0; i < 5; i++) {
            int lineY = y + (height * i / 5) + height/10;
            g2.drawLine(x + width/10, lineY, x + width - width/10, lineY);
        }
        
        g2.setColor(new Color(255, 255, 255, 100));
        for (int i = 0; i < 8; i++) {
            int bubbleSize = 5 + (int)(Math.random() * 8);
            int bubbleX = x + (int)(Math.random() * (width - bubbleSize));
            int bubbleY = y + (int)(Math.random() * (height - bubbleSize));
            g2.fillOval(bubbleX, bubbleY, bubbleSize, bubbleSize);
        }
        
        g2.setPaint(originalPaint);
    }
    
    @Override
    public void drawStone(Graphics2D g2, int x, int y, int size) {
        //pearls
    	g2.setColor(pearlColor);
        g2.fillOval(x, y, size, size);

        int highlightSize = size / 3;
        g2.setColor(Color.WHITE);
        g2.fillOval(x + size/4, y + size/4, highlightSize, highlightSize);
        
        g2.setColor(new Color(200, 200, 200));
        g2.setStroke(new BasicStroke(1));
        g2.drawOval(x, y, size, size);
    }
    
    @Override
    public Shape getPitBoundary(int width, int height) {
        return new Ellipse2D.Double(0, 0, width, height);
    }
    
    //wave effect - in progress
    private Path2D createWavePath(int x, int y, int width, int height, double amplitude) {
        Path2D path = new Path2D.Double();
        path.moveTo(x, y + height / 2);
        
        for (int i = 0; i <= width; i += 5) {
            double wave = Math.sin(i * 0.05) * amplitude;
            path.lineTo(x + i, y + height / 2 + wave);
        }
        
        return path;
    }
}