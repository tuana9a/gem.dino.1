// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.utils;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.awt.Color;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

public class Utils {
    public static int[] integerToArray(int num) {
        int length = String.valueOf(num).length();
        final int[] result = new int[length];
        while (num > 0) {
            result[--length] = num % 10;
            num /= 10;
        }
        return result;
    }

    public static BufferedImage reverseImg(final BufferedImage input) {
        if (input == null) {
            return null;
        }
        final int width = input.getWidth();
        final int height = input.getHeight();
        final BufferedImage output = new BufferedImage(width, height, 2);
        for (int y = 0; y < height; ++y) {
            for (int lx = 0, rx = width - 1; lx < width; ++lx, --rx) {
                final int p = input.getRGB(lx, y);
                output.setRGB(rx, y, p);
            }
        }
        return output;
    }

    public static BufferedImage rotateImg(final BufferedImage image, final double radian) {
        if (image == null) {
            return null;
        }
        final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        final GraphicsDevice gd = ge.getDefaultScreenDevice();
        final GraphicsConfiguration gc = gd.getDefaultConfiguration();
        final double sin = Math.abs(Math.sin(radian));
        final double cos = Math.abs(Math.cos(radian));
        final int w = image.getWidth();
        final int h = image.getHeight();
        final int newW = (int) Math.floor(w * cos + h * sin);
        final int newH = (int) Math.floor(h * cos + w * sin);
        final int transparency = image.getColorModel().getTransparency();
        final BufferedImage result = gc.createCompatibleImage(newW, newH, transparency);
        final Graphics2D g = result.createGraphics();
        g.translate((newW - w) / 2, (newH - h) / 2);
        g.rotate(radian, w / 2.0f, h / 2.0f);
        g.drawRenderedImage(image, null);
        return result;
    }

    public static String loadTextFile(final String filePath) {
        InputStream is = null;
        String stringData = null;
        try {
            is = new FileInputStream(filePath);
            final byte[] byteData = new byte[is.available()];
            final int byteAmountRead = is.read(byteData);
            stringData = new String(byteData);
        } catch (Exception e) {
            System.out.println("Fail to load: \"" + filePath + "\"");
            e.printStackTrace();
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return stringData;
    }

    public static BufferedImage loadImg(final String filePath) {
        try {
            return ImageIO.read(new File(filePath));
        } catch (IOException e) {
            System.out.println("Fail to load: \"" + filePath + "\"");
            e.printStackTrace();
            return null;
        }
    }

    public static Color getColor(final BufferedImage image, final int x, final int y) {
        final int color = image.getRGB(x, y);
        final int red = (color & 0xFF0000) >> 16;
        final int green = (color & 0xFF00) >> 8;
        final int blue = color & 0xFF;
        return new Color(red, green, blue);
    }

    public static Color getColor(final BufferedImage image) {
        final int width = image.getWidth();
        final int height = image.getHeight();
        return getColor(image, width / 2, height / 2);
    }

    public static void writeTextFile(final String filePath, final String data) {
        if (data == null || data.equals("")) {
            System.out.println("not thing to write");
            return;
        }
        FileOutputStream fos = null;
        BufferedOutputStream bis = null;
        try {
            final File f = new File(filePath);
            fos = new FileOutputStream(f);
            bis = new BufferedOutputStream(fos);
            bis.write(data.getBytes());
        } catch (Exception e) {
            System.out.println("Fail to write: \"" + filePath + "\"");
            e.printStackTrace();
            try {
                if (bis != null) {
                    bis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static String generateWeaponXml(final int id, final String name, final int w, final int h, final int bX, final int bY, final int bW, final int bH, final double[][] rotateRel, final double rotateSpeed, final double accuracy, final double attackSpeed, final int dmg, final double recoilAmt, final double recoilRegen, final double recoilRAmt, final double recoilRRegen) {
        String data = "";
        data = data + "<weapon id = \"" + id + "\" name = \"" + name + "\">\n";
        data = data + "\t<width>" + w + "</width>\n";
        data = data + "\t<height>" + h + "</height>\n";
        data = data + "\t<boundX>" + bX + "</boundX>\n";
        data = data + "\t<boundY>" + bY + "</boundY>\n";
        data = data + "\t<boundW>" + bW + "</boundW>\n";
        data = data + "\t<boundH>" + bH + "</boundH>\n";
        data = data + "\t<rotateRel><left x=\"" + rotateRel[0][0] + "\" y = \"" + rotateRel[0][1] + "\"></left><right x=\"" + rotateRel[1][0] + "\" y = \"" + rotateRel[1][1] + "\"></right></rotateRel>\n";
        data = data + "\t<rotateSpeed>" + rotateSpeed + "</rotateSpeed>\n";
        data = data + "\t<accuracy>" + accuracy + "</accuracy>\n";
        data = data + "\t<atkSpeed>" + attackSpeed + "</atkSpeed>\n";
        data = data + "\t<dmg>" + dmg + "</dmg>\n";
        data = data + "\t<recoilAmt>" + recoilAmt + "</recoilAmt>\n";
        data = data + "\t<recoilRegen>" + recoilRegen + "</recoilRegen>\n";
        data = data + "\t<recoilRotateAmt>" + recoilRAmt + "</recoilRotateAmt>\n";
        data = data + "\t<recoilRotateRegen>" + recoilRRegen + "</recoilRotateRegen>\n";
        data += "</weapon>";
        return data;
    }

    public static void parseWeaponXml() {
        try {
            final File inputFile = new File("input.txt");
            final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            final Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            final NodeList nodeList = doc.getElementsByTagName("weapon");
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            System.out.println("----------------------------");
            for (int i = 0; i < nodeList.getLength(); ++i) {
                final Node node = nodeList.item(i);
                System.out.println("\nCurrent Element :" + node.getNodeName());
                if (node.getNodeType() == 1) {
                    final Element element = (Element) node;
                    System.out.println("Weapon ID  : " + element.getAttribute("id"));
                    System.out.println("Width      : " + element.getElementsByTagName("width").item(0).getTextContent());
                    System.out.println("Height     : " + element.getElementsByTagName("height").item(0).getTextContent());
                    final NodeList rel = element.getElementsByTagName("rotateRel").item(0).getChildNodes();
                    final NamedNodeMap relLeft = rel.item(0).getAttributes();
                    final NamedNodeMap relRight = rel.item(1).getAttributes();
                    System.out.printf("RotateRel : left (x %s,y %s) right (%s, %s)\n", relLeft.getNamedItem("x").getTextContent(), relLeft.getNamedItem("y").getTextContent(), relRight.getNamedItem("x").getTextContent(), relRight.getNamedItem("y").getTextContent());
                    System.out.println("rotateSpeed: " + element.getElementsByTagName("rotateSpeed").item(0).getTextContent());
                    System.out.println("accuracy: " + element.getElementsByTagName("accuracy").item(0).getTextContent());
                    System.out.println("atkSpeed: " + element.getElementsByTagName("rotateSpeed").item(0).getTextContent());
                    System.out.println("damage: " + element.getElementsByTagName("dmg").item(0).getTextContent());
                    System.out.println("recoilAmt: " + element.getElementsByTagName("recoilAmt").item(0).getTextContent());
                    System.out.println("recoilRegen: " + element.getElementsByTagName("recoilRegen").item(0).getTextContent());
                    System.out.println("recoilRAmt: " + element.getElementsByTagName("recoilRotateAmt").item(0).getTextContent());
                    System.out.println("recoilRRegen: " + element.getElementsByTagName("recoilRotateRegen").item(0).getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(final String[] args) {
        final String s = "4 4 4 6 6 6 4 4 6 6 6 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 3 3 3 3 3 3 3 3 3 3 3 3 3 9 9 9 9 9\n4 4 6 0 0 0 6 6 0 0 0 6 4 4 4 4 4 4 4 4 6 6 6 6 6 6 6 6 6 6 4 4 4 3 3 3 3 3 3 3 3 3 3 3 7 9 9 9 9 9\n4 6 0 0 0 0 0 0 0 0 0 0 6 4 4 4 4 4 4 6 6 0 0 0 0 0 0 0 0 0 4 4 4 4 3 3 3 3 3 5 3 5 3 3 7 9 9 9 9 9\n4 6 0 0 0 0 0 0 0 0 0 0 6 4 4 4 4 4 6 0 0 0 0 0 0 0 0 0 0 0 0 0 8 8 8 3 3 3 3 3 3 3 5 3 7 9 9 9 9 9\n4 4 6 0 0 0 0 0 0 0 0 6 4 4 4 4 4 4 0 0 0 7 7 7 7 7 7 7 7 9 0 0 0 0 0 0 8 8 3 5 5 3 3 5 3 7 9 9 9 9\n4 4 4 6 0 0 0 0 0 0 6 4 4 4 4 4 4 4 0 0 7 7 9 9 9 9 9 4 4 9 9 0 0 0 0 0 0 8 3 3 5 5 3 3 5 3 7 9 9 9\n4 4 4 4 6 0 0 0 0 6 4 4 4 4 4 4 4 6 0 0 7 9 9 9 9 9 4 6 4 9 0 0 0 0 0 0 0 0 3 3 3 3 3 3 3 5 7 7 7 9\n4 4 4 4 4 6 0 0 6 4 4 4 6 4 4 4 6 6 0 0 7 4 4 4 6 6 4 4 4 0 0 0 7 7 7 7 0 0 0 3 3 3 3 3 3 3 3 3 3 7\n4 4 4 4 4 4 0 0 0 4 4 6 6 4 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 7 9 9 9 9 0 0 0 0 3 5 5 5 5 3 5 5 5 3\n3 3 4 4 4 4 4 0 0 0 6 6 4 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 7 9 9 9 9 9 9 0 0 0 0 0 0 0 5 3 3 3 5 0\n3 5 3 3 4 4 4 4 0 0 0 4 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 7 9 9 9 9 9 9 9 0 0 0 0 0 0 0 0 0 0 3 5 0\n3 3 5 5 3 4 4 4 0 0 0 0 0 0 7 7 7 7 7 7 7 7 7 7 7 7 0 0 7 9 9 9 7 7 9 9 9 9 9 9 0 0 0 0 0 0 0 0 3 0\n7 7 7 3 5 5 5 4 4 4 0 0 0 7 9 9 9 9 9 9 9 9 9 9 9 9 0 0 9 9 9 7 8 7 9 9 9 9 9 0 0 5 5 5 0 0 0 0 0 0\n0 0 0 7 3 3 3 3 3 0 0 0 7 9 9 9 9 9 9 9 9 9 9 9 9 9 0 0 9 9 7 8 7 7 9 9 9 0 0 0 5 5 3 3 5 5 5 0 0 0\n0 0 0 0 7 3 3 3 0 0 0 7 9 9 9 7 7 7 9 9 9 9 9 9 9 9 0 0 9 9 7 8 7 9 9 9 0 0 0 0 5 3 3 3 3 3 5 5 5 0\n0 0 0 0 0 7 3 0 0 0 7 9 9 9 9 7 8 7 9 9 9 9 9 9 9 9 0 0 9 9 7 7 7 9 9 9 0 3 0 0 5 3 3 3 3 3 3 5 5 0\n7 7 7 0 0 0 7 0 0 8 9 9 9 9 9 7 7 7 9 9 9 9 9 9 9 8 0 0 8 8 9 9 9 9 9 9 0 0 0 0 5 3 3 3 3 3 3 5 0 0\n1 1 1 7 0 0 0 0 8 8 9 9 9 9 9 9 9 9 9 9 9 9 9 9 8 8 0 0 0 8 8 9 9 9 9 9 9 0 0 8 5 5 3 3 3 3 5 5 0 0\n1 1 4 1 7 0 0 0 8 9 9 9 9 9 9 9 9 9 9 9 9 7 7 7 0 0 0 0 0 0 0 8 8 9 8 0 0 0 0 0 0 5 5 5 5 5 5 0 0 0\n6 6 4 1 1 7 0 0 7 7 9 9 9 9 9 9 9 9 9 9 7 0 0 0 0 0 0 0 0 0 0 0 0 8 0 0 0 0 0 0 0 0 0 0 5 5 0 0 0 0\n6 6 6 4 1 1 7 0 0 7 7 7 7 7 7 9 9 9 7 7 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 7 7 0 0 0 0 0 0 0 1 0\n4 4 4 4 4 1 7 0 0 0 0 0 0 0 7 7 7 8 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 7 7 4 4 4 0 0 0 0 0 1 1 1\n4 5 5 5 4 4 1 7 0 0 0 0 0 0 0 0 7 7 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 7 7 7 6 4 6 6 4 0 0 0 1 1 4 1\n5 5 5 5 5 4 1 1 7 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 7 1 4 4 4 4 4 6 6 1 1 1 1 6 4 1\n3 5 5 5 5 4 4 1 1 1 6 4 4 6 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 7 7 7 1 1 3 3 4 4 4 4 6 5 5 5 6 6 4 1\n3 3 8 8 8 8 4 4 6 6 4 4 4 4 6 0 0 0 1 1 1 1 1 1 1 0 0 0 0 7 7 1 1 1 1 1 3 3 3 3 4 4 4 5 5 5 6 4 4 1\n3 8 8 8 8 8 8 4 4 4 4 4 4 6 6 0 0 1 5 5 5 1 1 1 1 1 1 1 1 1 1 1 1 1 3 3 3 3 3 5 5 5 5 5 5 5 5 6 6 1\n3 8 8 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 5 3 3 3 1 1 1 1 1 1 1 1 1 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 1\n3 8 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3\n3 3 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 3 5 5 5 5 3 3 3 3 3 3 5 5 5 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3";
        System.out.println(s.replaceAll("1", "2"));
    }
}
