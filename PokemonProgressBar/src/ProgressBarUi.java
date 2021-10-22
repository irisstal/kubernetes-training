import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class ProgressBarUi extends BasicProgressBarUI {
    BufferedImage bimage = null;

    private volatile int offset;

    private volatile int offset2;

    private volatile int velocity;

    public static ComponentUI createUI(JComponent c) {
        c.setBorder((Border)JBUI.Borders.empty().asUIResource());
        return new ProgressBarUi();
    }

    public Dimension getPreferredSize(JComponent c) {
        return new Dimension((super.getPreferredSize(c)).width, JBUI.scale(20));
    }

    protected void installListeners() {
        super.installListeners();
        this.progressBar.addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
            }

            public void componentHidden(ComponentEvent e) {
                super.componentHidden(e);
            }
        });
    }

    public ProgressBarUi() {
        this.offset = 0;
        this.offset2 = 0;
        this.velocity = 1;
        try {
            this.bimage = ImageIO.read(getClass().getResource("/grass.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void paintIndeterminate(Graphics g2d, JComponent c) {
        if (!(g2d instanceof Graphics2D))
            return;
        Graphics2D g = (Graphics2D)g2d;
        Insets b = this.progressBar.getInsets();
        int barRectWidth = this.progressBar.getWidth() - b.right + b.left;
        int barRectHeight = this.progressBar.getHeight() - b.top + b.bottom;
        if (barRectWidth <= 0 || barRectHeight <= 0)
            return;
        g.setColor((Color)new JBColor(Gray._240.withAlpha(50), Gray._128.withAlpha(50)));
        int w = c.getWidth();
        int h = (c.getPreferredSize()).height;
        if (!isEven(c.getHeight() - h))
            h++;
        if (c.isOpaque())
            g.fillRect(0, (c.getHeight() - h) / 2, w, h);
        g.setColor((Color)new JBColor(Gray._165.withAlpha(50), Gray._88.withAlpha(50)));
        GraphicsConfig config = GraphicsUtil.setupAAPainting(g);
        g.translate(0, (c.getHeight() - h) / 2);
        int x = -this.offset;
        float R = JBUI.scale(8.0F);
        float R2 = JBUI.scale(9.0F);
        Area containingRoundRect = new Area(new RoundRectangle2D.Float(1.0F, 1.0F, w - 2.0F, h - 2.0F, R, R));
        g.fill(containingRoundRect);
        this.offset = (this.offset + 1) % getPeriodLength();
        this.offset2 += this.velocity;
        if (this.offset2 <= 2) {
            this.offset2 = 2;
            this.velocity = 1;
        } else if (this.offset2 >= w - JBUI.scale(15)) {
            this.offset2 = w - JBUI.scale(15);
            this.velocity = -1;
        }
        Area area = new Area(new Rectangle2D.Float(0.0F, 0.0F, w, h));
        area.subtract(new Area(new RoundRectangle2D.Float(1.0F, 1.0F, w - 2.0F, h - 2.0F, R, R)));
        if (c.isOpaque())
            g.fill(area);
        area.subtract(new Area(new RoundRectangle2D.Float(0.0F, 0.0F, w, h, R2, R2)));
        Container parent = c.getParent();
        if (c.isOpaque())
            g.fill(area);
        PokemonIcons.POKEBALL.paintIcon(this.progressBar, g, this.offset2 - JBUI.scale(3), -JBUI.scale(-2));
        g.draw(new RoundRectangle2D.Float(1.0F, 1.0F, w - 2.0F - 1.0F, h - 2.0F - 1.0F, R, R));
        g.translate(0, -(c.getHeight() - h) / 2);
        if (this.progressBar.isStringPainted())
            if (this.progressBar.getOrientation() == 0) {
                paintString(g, b.left, b.top, barRectWidth, barRectHeight, this.boxRect.x, this.boxRect.width);
            } else {
                paintString(g, b.left, b.top, barRectWidth, barRectHeight, this.boxRect.y, this.boxRect.height);
            }
        config.restore();
    }

    protected void paintDeterminate(Graphics g, JComponent c) {
        if (!(g instanceof Graphics2D))
            return;
        if (this.progressBar.getOrientation() != 0 || !c.getComponentOrientation().isLeftToRight()) {
            super.paintDeterminate(g, c);
            return;
        }
        GraphicsConfig config = GraphicsUtil.setupAAPainting(g);
        Insets b = this.progressBar.getInsets();
        int w = this.progressBar.getWidth();
        int h = (this.progressBar.getPreferredSize()).height;
        if (!isEven(c.getHeight() - h))
            h++;
        int barRectWidth = w - b.right + b.left;
        int barRectHeight = h - b.top + b.bottom;
        if (barRectWidth <= 0 || barRectHeight <= 0)
            return;
        int amountFull = getAmountFull(b, barRectWidth, barRectHeight);
        Container parent = c.getParent();
        Color background = (parent != null) ? parent.getBackground() : UIUtil.getPanelBackground();
        g.setColor(background);
        Graphics2D g2 = (Graphics2D)g;
        if (c.isOpaque())
            g.fillRect(0, 0, w, h);
        float R = JBUI.scale(8.0F);
        float R2 = JBUI.scale(9.0F);
        float off = JBUI.scale(1.0F);
        g2.translate(0, (c.getHeight() - h) / 2);
        g2.setColor(this.progressBar.getForeground());
        g2.fill(new RoundRectangle2D.Float(0.0F, 0.0F, w - off, h - off, R2, R2));
        g2.setColor(background);
        g2.fill(new RoundRectangle2D.Float(off, off, w - 2.0F * off - off, h - 2.0F * off - off, R, R));
        if (this.bimage != null) {
            TexturePaint tp = new TexturePaint(this.bimage, new Rectangle2D.Double(0.0D, 1.0D, (h - 2.0F * off - off), (h - 2.0F * off - off)));
            g2.setPaint(tp);
        }
        g2.fill(new RoundRectangle2D.Float(2.0F * off, 2.0F * off, amountFull - JBUI.scale(5.0F), h - JBUI.scale(5.0F), JBUI.scale(7.0F), JBUI.scale(7.0F)));
        PokemonIcons.POKEBALL.paintIcon(this.progressBar, g2, amountFull - JBUI.scale(5), -JBUI.scale(1));
        g2.translate(0, -(c.getHeight() - h) / 2);
        if (this.progressBar.isStringPainted())
            paintString(g, b.left, b.top, barRectWidth, barRectHeight, amountFull, b);
        config.restore();
    }

    private void paintString(Graphics g, int x, int y, int w, int h, int fillStart, int amountFull) {
        if (!(g instanceof Graphics2D))
            return;
        Graphics2D g2 = (Graphics2D)g;
        String progressString = this.progressBar.getString();
        g2.setFont(this.progressBar.getFont());
        Point renderLocation = getStringPlacement(g2, progressString, x, y, w, h);
        Rectangle oldClip = g2.getClipBounds();
        if (this.progressBar.getOrientation() == 0) {
            g2.setColor(getSelectionBackground());
            BasicGraphicsUtils.drawString(this.progressBar, g2, progressString, renderLocation.x, renderLocation.y);
            g2.setColor(getSelectionForeground());
            g2.clipRect(fillStart, y, amountFull, h);
            BasicGraphicsUtils.drawString(this.progressBar, g2, progressString, renderLocation.x, renderLocation.y);
        } else {
            g2.setColor(getSelectionBackground());
            AffineTransform rotate = AffineTransform.getRotateInstance(1.5707963267948966D);
            g2.setFont(this.progressBar.getFont().deriveFont(rotate));
            renderLocation = getStringPlacement(g2, progressString, x, y, w, h);
            BasicGraphicsUtils.drawString(this.progressBar, g2, progressString, renderLocation.x, renderLocation.y);
            g2.setColor(getSelectionForeground());
            g2.clipRect(x, fillStart, w, amountFull);
            BasicGraphicsUtils.drawString(this.progressBar, g2, progressString, renderLocation.x, renderLocation.y);
        }
        g2.setClip(oldClip);
    }

    protected int getBoxLength(int availableLength, int otherDimension) {
        return availableLength;
    }

    private int getPeriodLength() {
        return JBUI.scale(16);
    }

    private static boolean isEven(int value) {
        return (value % 2 == 0);
    }
}